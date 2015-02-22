package be.bendem.bot.storage.models;

import be.bendem.bot.storage.Model;
import be.bendem.bot.storage.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    protected Optional<T> get(SqlQuery query) {
        List<T> resources;
        try {
            resources = query(query);
        } catch(SQLException e) {
            // TODO Throw?
            return Optional.empty();
        }
        if(resources.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(resources.get(0));
    }

}
