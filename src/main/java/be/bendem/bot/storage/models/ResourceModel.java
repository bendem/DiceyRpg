package be.bendem.bot.storage.models;

import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.inventories.items.Resource;
import be.bendem.bot.storage.Database;
import be.bendem.bot.storage.GameRegistry;
import be.bendem.bot.storage.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceModel extends BaseModel<Resource> {

    private static final String DEFAULT_QUERY = "select * from resource left join item on(resource.IdItem = item.IdItem)";
    private static final String[] ITEM_FIELDS = { "IdItem", "Name", "Description", "Value", "Rank",
        "LevelRequired", "DropProbability", "DropClimate" };
    private static final String[] RESOURCE_FIELDS = { "IdItem" };

    private final Database db;

    public ResourceModel(Database db) {
        this.db = db;
    }

    protected Resource read(ResultSet result) throws SQLException {
        return new Resource(
            result.getInt("IdItem"),
            result.getString("Name"),
            result.getString("Description"),
            result.getInt("Value"),
            Item.Rank.values()[result.getInt("Rank")],
            result.getInt("LevelRequired"),
            result.getInt("DropProbability"),
            GameRegistry.getInstance().getClimate(result.getInt("DropClimate"))
        );
    }

    @Override
    public List<Resource> getAll() {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY);
        try {
            return query(stmt);
        } catch(SQLException e) {
            // TODO Proper logging?
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not retrieve stuff", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Resource> get(int id) {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?").set(1, id);
        List<Resource> exec;
        try {
            exec = query(stmt);
        } catch(SQLException e) {
            // TODO Throw?
            return Optional.empty();
        }
        if(exec.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(exec.get(0));
    }

    @Override
    public Optional<Resource> get(String name) {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?").set(1, name);
        List<Resource> resources;
        try {
            resources = query(stmt);
        } catch(SQLException e) {
            // TODO Throw?
            return Optional.empty();
        }
        if(resources.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(resources.get(0));
    }

    @Override
    public void add(Resource item) {
        db.setAutoCommit(false);
        SqlQuery stmt = db.prepare(
            "insert into item ("
                + String.join(", ", ITEM_FIELDS)
            + ") values ("
                + Stream.of(ITEM_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
            + ")"
        );

        stmt
            .set(1, null)
            .set(2, item.name)
            .set(3, item.description)
            .set(4, item.value)
            .set(5, item.rank.ordinal())
            .set(6, item.levelRequired)
            .set(7, item.dropProbability)
            .set(8, item.dropClimate.id)

            .add(
                db.prepare(
                    "insert into resource ("
                        + String.join(", ", RESOURCE_FIELDS)
                    + ") values ("
                        + Stream.of(RESOURCE_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
                    + ")"
                ).set(1, item.id)
            );

        if(stmt.exec() != 2) {
            db.rollback();
            // TODO Throw!
        }

        db.commit();
    }

    @Override
    public void update(Resource item) {
        // TODO
        throw new UnsupportedOperationException();
    }

}
