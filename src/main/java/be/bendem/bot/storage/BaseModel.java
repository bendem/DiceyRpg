package be.bendem.bot.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BaseModel<T> implements Model<T> {

    private final String table;
    private final Adaptater<T> adaptater;
    private final Database database;

    public BaseModel(String tableName, Adaptater<T> objectAdaptater, Database db) {
        table = tableName;
        adaptater = objectAdaptater;
        database = db;
    }

    public String getTable() {
        return table;
    }

    public Collection<T> getAllThrowing() throws SQLException {
        PreparedStatement ps = database.prepare("SELECT * FROM ?");
        ps.setString(1, table);
        ResultSet rs = ps.executeQuery();

        ArrayList<T> list = new ArrayList<>();
        while(rs.next()) {
            list.add(adaptater.toJava(rs));
        }
        return list;
    }

    @Override
    public Collection<T> getAll() {
        try {
            return getAllThrowing();
        } catch(SQLException e) {
            throw new RuntimeException("Could not get all elements", e);
        }
    }

    @Override
    public Collection<T> getAll(Predicate<T> predicate) {
        // TODO That's horrible, 'where' exists for something
        return getAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<T> getFirst(Predicate<T> predicate) {
        // TODO That's horrible, 'where' exists for something
        return getAll().stream().filter(predicate).findAny();
    }

    @Override
    public boolean add(T item) {
        StringJoiner joiner = new StringJoiner(", ");
        for(int i = 0; i < adaptater.fieldCount(); i++) {
            joiner.add("?");
        }
        int count;
        try {
            count = adaptater.bind(
                item,
                database.prepare("insert into " + table + " values (" + joiner + ')')
            ).executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException("Could not add " + item + " to the db", e);
        }
        return count != 1;
    }

}
