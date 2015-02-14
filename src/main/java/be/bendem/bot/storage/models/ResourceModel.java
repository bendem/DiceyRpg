package be.bendem.bot.storage.models;

import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.inventories.items.Resource;
import be.bendem.bot.storage.Database;
import be.bendem.bot.storage.GameRegistry;
import be.bendem.bot.storage.Model;

import java.sql.PreparedStatement;
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

    private List<Resource> exec(PreparedStatement stmt) throws SQLException{
        ResultSet result = stmt.executeQuery();

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
        PreparedStatement stmt = db.prepare(DEFAULT_QUERY);
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
        PreparedStatement stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?");
        List<Resource> exec;
        try {
            stmt.setInt(0, id);
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
        PreparedStatement stmt = db.prepare(DEFAULT_QUERY + " where IdItem = ?");
        List<Resource> exec;
        try {
            stmt.setString(0, name);
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
        PreparedStatement stmt = db.prepare(
            "insert into item ("
                + String.join(", ", ITEM_FIELDS)
            + ") values ("
                + Stream.of(ITEM_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
            + ")"
        );
        try {
            stmt.setInt(0, item.id);
            stmt.setString(1, item.name);
            stmt.setString(2, item.description);
            stmt.setInt(3, item.value);
            stmt.setInt(4, item.rank.ordinal());
            stmt.setInt(5, item.levelRequired);
            stmt.setInt(6, item.dropProbability);
            stmt.setInt(7, item.dropClimate.id);
            stmt.addBatch(
                "insert into resource ("
                    + String.join(", ", RESOURCE_FIELDS)
                + ") values ("
                    + Stream.of(RESOURCE_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
                + ")"
            );
            stmt.setInt(0, item.id);
            int count = stmt.executeUpdate();
            // TODO Validate count?
        } catch(SQLException e) {
            // TODO Handle error
            e.printStackTrace();
        }
        db.commit();
    }

    @Override
    public void update(Resource item) {
        throw new UnsupportedOperationException();
    }

}
