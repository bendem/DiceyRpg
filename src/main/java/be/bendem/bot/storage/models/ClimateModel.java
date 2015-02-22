package be.bendem.bot.storage.models;

import be.bendem.bot.game.Climate;
import be.bendem.bot.storage.Database;
import be.bendem.bot.storage.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClimateModel extends BaseModel<Climate> {

    private final Database db;

    public ClimateModel(Database db) {
        this.db = db;
    }

    @Override
    protected Climate read(ResultSet resultSet) throws SQLException {
        return new Climate(
            resultSet.getInt("IdClimate"),
            resultSet.getString("Name"),
            resultSet.getString("Description")
        );
    }

    @Override
    public List<Climate> getAll() {
        try {
            return query(db.prepare("select * from climate"));
        } catch(SQLException e) {
            // TODO Proper logging?
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not retrieve stuff", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Climate> get(int id) {
        SqlQuery query = db.prepare("select * from climate where IdClimate = ?").set(1, id);
        List<Climate> climates;
        try {
            climates = query(query);
        } catch(SQLException e) {
            // TODO Throw?
            return Optional.empty();
        }
        if(climates.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(climates.get(0));
    }

    @Override
    public Optional<Climate> get(String name) {
        SqlQuery query = db.prepare("select * from climate where Name = ?").set(1, name);
        List<Climate> climates;
        try {
            climates = query(query);
        } catch(SQLException e) {
            // TODO Throw?
            return Optional.empty();
        }
        if(climates.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(climates.get(0));
    }

    @Override
    public void add(Climate item) {
        db.setAutoCommit(false);
        SqlQuery stmt = db.prepare(
            "insert into climate (IdClimate, Name, Descritpion) values (?, ?, ?)"
        );

        stmt
            .set(1, null)
            .set(2, item.name)
            .set(3, item.description);

        if(stmt.exec() != 1) {
            db.rollback();
            // TODO Throw!
        }
        db.commit();
    }

    @Override
    public void update(Climate item) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
