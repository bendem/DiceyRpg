package be.bendem.bot.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private final Connection connection;
    private final Map<Class<?>, Model<?>> adaptaters;

    public Database(Path dbFile) throws SQLException {
        if(Files.notExists(dbFile)) {
            try {
                Files.createDirectories(dbFile.getParent());
            } catch(IOException e) {
                throw new RuntimeException("Could not create data folder for " + dbFile, e);
            }
        }
        connection = DriverManager.getConnection("jdbc:h2:" + dbFile);

        Map<Class<?>, Model<?>> adaptaterMap = new HashMap<>();
        // TODO register adaptaters here

        this.adaptaters = Collections.unmodifiableMap(adaptaterMap);

        // TODO Create tables if they don't exist
    }

    @SuppressWarnings("unchecked")
    public <T> Model<T> getModel(Class<T> clazz) {
        return (Model<T>) adaptaters.get(clazz);
    }

    PreparedStatement prepare(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch(SQLException e) {
            throw new RuntimeException("Couldn't prepare statement", e);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            throw new RuntimeException("Could not close connection source", e);
        }
    }

}