package be.bendem.bot.storage.models;

import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.inventories.items.Resource;
import be.bendem.bot.storage.Database;
import be.bendem.bot.storage.GameRegistry;
import be.bendem.bot.storage.Model;
import be.bendem.bot.storage.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceModel implements Model<Resource> {

    private static final String DEFAULT_QUERY = "select * from resource left join item using(IdItem)";
    private static final String[] ITEM_FIELDS = { "IdItem", "Name", "Description", "Value", "Rank",
        "LevelRequired", "DropProbability", "DropClimate" };
    private static final String[] RESOURCE_FIELDS = { "IdItem" };

    private final Database db;

    public ResourceModel(Database db) {
        this.db = db;
    }

    private List<Resource> exec(SqlQuery stmt) throws SQLException{
        ResultSet result = stmt.query();

        ArrayList<Resource> list = new ArrayList<>();
        while(result.next()) {
            list.add(read(result));
        }

        return list;
    }

    private Resource read(ResultSet result) {
        try {
            int id = result.getInt("IdItem");
            String name = result.getString("Name");
            String description = result.getString("Description");
            int value = result.getInt("Value");
            Item.Rank rank = Item.Rank.values()[result.getInt("Rank")];
            int levelRequired = result.getInt("LevelRequired");
            int dropProbability = result.getInt("DropProbability");
            Climate dropClimate = GameRegistry.getInstance().getClimate(result.getInt("DropClimate"));

            return new Resource(id, name, description, value, rank, levelRequired, dropProbability, dropClimate);
        } catch(SQLException e) {
            // TODO Handle exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Resource> getAll() {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY);
        try {
            return exec(stmt);
        } catch(SQLException e) {
            // TODO Proper logging?
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not retrieve stuff", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Resource> get(int id) {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?");
        List<Resource> exec;
        try {
            stmt.set(0, id);
            exec = exec(stmt);
        } catch(SQLException e) {
            // TODO
            return Optional.empty();
        }
        if(exec.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(exec.get(0));
    }

    @Override
    public Optional<Resource> get(String name) {
        SqlQuery stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?");
        List<Resource> exec;
        try {
            stmt.set(0, name);
            exec = exec(stmt);
        } catch(SQLException e) {
            // TODO
            return Optional.empty();
        }
        if(exec.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(exec.get(0));
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
            .set(0, item.id)
            .set(1, item.name)
            .set(2, item.description)
            .set(3, item.value)
            .set(4, item.rank.ordinal())
            .set(5, item.levelRequired)
            .set(6, item.dropProbability)
            .set(7, item.dropClimate.id)

            .add(
                db.prepare(
                    "insert into resource ("
                        + String.join(", ", RESOURCE_FIELDS)
                    + ") values ("
                        + Stream.of(RESOURCE_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
                    + ")"
                ).set(0, item.id)
            );
        // TODO Validate count?
        int count = stmt.exec();

        db.commit();
    }

    @Override
    public void update(Resource item) {
        throw new UnsupportedOperationException();
    }

}
