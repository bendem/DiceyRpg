package be.bendem.bot.storage;

import be.bendem.bot.RpgBot;
import be.bendem.bot.entities.Monster;
import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Die;
import be.bendem.bot.inventories.items.Equipment;
import be.bendem.bot.inventories.items.Resource;
import be.bendem.bot.storage.models.*;
import org.h2.tools.RunScript;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Connection connection;
    private final Map<Class<?>, Model<?>> models;

    public Database(Path dbFile) throws SQLException, IOException {
        boolean needsCreation = false;
        if(Files.notExists(dbFile)) {
            Files.createDirectories(dbFile.getParent());
            needsCreation = true;
        }
        // TODO Check what are the implications
        // ;MV_STORE=FALSE Prevents it from writing in weird places not available from outside the application
        connection = DriverManager.getConnection("jdbc:h2:" + dbFile + ";MV_STORE=FALSE");

        if(needsCreation) {
            RpgBot.getLogger().fine("Database not found, creating it");
            RunScript.execute(
                connection,
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("database.sql"),
                    StandardCharsets.UTF_8
                )
            );
        }

        migrate();

        Map<Class<?>, Model<?>> modelMap = new HashMap<>();
        modelMap.put(Climate.class, new ClimateModel(this));
        modelMap.put(Resource.class, new ResourceModel(this));
        modelMap.put(Die.class, new DieModel(this));
        modelMap.put(Equipment.class, new EquipmentModel(this));
        modelMap.put(Monster.class, new MonsterModel(this));

        models = Collections.unmodifiableMap(modelMap);
    }

    private void migrate() throws IOException, SQLException {
        Path migrationsDir = Paths.get("migrations");
        Path migrationFile = Paths.get("migrations.txt");

        Path lastMigration = null;
        if(Files.exists(migrationFile)) {
            lastMigration = migrationsDir.resolve(Files.readAllLines(migrationFile, StandardCharsets.UTF_8).get(0));
        }
        if(!Files.isDirectory(migrationsDir)) {
            Files.createDirectory(migrationsDir);
        }

        boolean foundLast = lastMigration == null; // If no last, migrate everything
        Path newLastMigration = null;
        for(Path migration : Files.newDirectoryStream(migrationsDir)) {
            if(!foundLast) {
                if(Files.isSameFile(lastMigration, migration)) {
                    foundLast = true;
                }
                continue;
            }
            RpgBot.getLogger().fine("Running migration '" + migration + "'");
            RunScript.execute(
                connection,
                Files.newBufferedReader(migration, StandardCharsets.UTF_8)
            );
            newLastMigration = migration;
        }
        if(newLastMigration != null) {
            BufferedWriter bufferedWriter = Files.newBufferedWriter(migrationFile, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
            bufferedWriter.write(newLastMigration.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Model<T> getModel(Class<T> clazz) {
        Model<?> model = models.get(clazz);
        if(model == null) {
            throw new IllegalArgumentException("No model found for '" + clazz.getName() + "'");
        }
        return (Model<T>) model;
    }

    /**
     * Create a prepared statement from the connection handled by this database object.
     * <p>
     * This should only be used by models, not by the application
     *
     * @param sql SQL request
     * @return the prepared statement
     */
    public SqlQuery prepare(String sql) {
        return new SqlQuery(connection, sql);
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch(SQLException e) {
            throw new RuntimeException("Couldn't change autocommit", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch(SQLException e) {
            throw new RuntimeException("Couldn't commit", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch(SQLException e) {
            throw new RuntimeException("Couldn't rollback", e);
        }
    }

    public void close() {
        try {
            if(!connection.isClosed()) {
                connection.close();
            }
        } catch(SQLException e) {
            throw new RuntimeException("Could not close connection source", e);
        }
    }

}
