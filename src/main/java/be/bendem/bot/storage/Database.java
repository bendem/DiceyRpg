package be.bendem.bot.storage;

import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.storage.models.ClimateModel;
import be.bendem.bot.storage.models.ResourceModel;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Connection connection;
    private final Map<Class<?>, Model<?>> models;

    public Database(Path dbFile) throws SQLException {
        boolean needsCreation = false;
        if(Files.notExists(dbFile)) {
            try {
                Files.createDirectories(dbFile.getParent());
            } catch(IOException e) {
                throw new RuntimeException("Could not create data folder for " + dbFile, e);
            }
            needsCreation = true;
        }
        connection = DriverManager.getConnection("jdbc:h2:" + dbFile);

        if(needsCreation) {
            RunScript.execute(
                connection,
                new InputStreamReader(
                    getClass().getClassLoader().getResourceAsStream("database.sql"), StandardCharsets.UTF_8
                )
            );
        }

        Map<Class<?>, Model<?>> modelMap = new HashMap<>();
        modelMap.put(Climate.class, new ClimateModel(this));
        modelMap.put(Item.class, new ResourceModel(this));

        this.models = Collections.unmodifiableMap(modelMap);
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
        return new SqlQuery(connection, sql);/*
        try {
            return connection.prepareStatement(sql);
        } catch(SQLException e) {
            throw new RuntimeException("Couldn't prepare statement", e);
        }*/
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
