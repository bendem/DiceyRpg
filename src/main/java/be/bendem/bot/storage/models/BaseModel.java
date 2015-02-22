package be.bendem.bot.storage.models;

import be.bendem.bot.storage.Model;
import be.bendem.bot.storage.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseModel<T> implements Model<T> {

    protected abstract T read(ResultSet resultSet) throws SQLException;

    protected List<T> query(SqlQuery stmt) throws SQLException {
        ResultSet result = stmt.query();

        ArrayList<T> list = new ArrayList<>();
        while(result.next()) {
            list.add(read(result));
        }

        return list;
    }

}
