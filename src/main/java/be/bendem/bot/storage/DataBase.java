package be.bendem.bot.storage;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataBase {

    private final JdbcConnectionSource connectionSource;
    private final Map<Class<?>, Dao<?, ?>> daos;

    public DataBase(Path dbFile) throws SQLException {
        if(Files.notExists(dbFile)) {
            try {
                Files.createDirectories(dbFile.getParent());
            } catch(IOException e) {
                throw new RuntimeException("Could not create data folder for " + dbFile, e);
            }
        }
        connectionSource = new JdbcConnectionSource("jdbc:h2:" + dbFile);

        daos = new HashMap<>();
        //daos.put(Server.class, DaoManager.createDao(connectionSource, Server.class));
        //daos.put(Identity.class, DaoManager.createDao(connectionSource, Identity.class));

        for(Class<?> clazz : daos.keySet()) {
            TableUtils.createTableIfNotExists(connectionSource, clazz);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Dao<T, ?> getDao(Class<T> clazz) {
        Dao<T, ?> dao = (Dao<T, ?>) daos.get(clazz);
        if(dao == null) {
            throw new IllegalArgumentException("No dao loaded for " + clazz.getName());
        }
        return dao;
    }

    public void close() {
        try {
            connectionSource.close();
        } catch(IOException e) {
            throw new RuntimeException("Could not close connection source", e);
        }
    }

}
