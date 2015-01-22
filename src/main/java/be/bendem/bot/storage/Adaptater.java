package be.bendem.bot.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Adaptater<T> {

    public T toJava(ResultSet row) throws SQLException;
    public int fieldCount();
    public PreparedStatement bind(T obj, PreparedStatement stmt) throws SQLException;

}
