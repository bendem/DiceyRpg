package be.bendem.bot.storage;

import be.bendem.bot.storage.irc.Identity;
import be.bendem.bot.storage.irc.Server;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DataBase {

    private final JdbcConnectionSource connectionSource;
    private final Map<Class<?>, Dao<?, ?>> daos;

    public DataBase(File folder) throws SQLException {
        if(!folder.exists()) {
            if(!folder.mkdirs()) {
                throw new RuntimeException("Could not create data folder " + folder.getPath());
            }
        }
        File dbFile = new File(folder, "database.h2");
        connectionSource = new JdbcConnectionSource("jdbc:h2:" + dbFile.getAbsolutePath());

        daos = new HashMap<>();
        daos.put(Server.class, DaoManager.createDao(connectionSource, Server.class));
        daos.put(Identity.class, DaoManager.createDao(connectionSource, Identity.class));

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
