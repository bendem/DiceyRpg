package be.bendem.bot.storage.models;

import be.bendem.bot.inventories.items.Equipment;
import be.bendem.bot.inventories.items.Item;
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

public class EquipmentModel extends BaseModel<Equipment> {

    private static final String DEFAULT_QUERY = "select * from equipment left join item on (equipment.IdItem = item.IdItem)";
    private static final String[] ITEM_FIELDS = { "IdItem", "Name", "Description", "Value", "Rank",
        "LevelRequired", "DropProbability", "DropClimate" };
    private static final String[] EQUIPMENT_FIELDS = { "IdItem", "EquipableSlot" };

    private final Database db;

    public EquipmentModel(Database db) {
        this.db = db;
    }

    @Override
    protected Equipment read(ResultSet resultSet) throws SQLException {
        return new Equipment(
            resultSet.getInt("IdItem"),
            resultSet.getString("Name"),
            resultSet.getString("Description"),
            resultSet.getInt("Value"),
            Item.Rank.values()[resultSet.getInt("Rank")],
            resultSet.getInt("LevelRequired"),
            resultSet.getInt("DropProbability"),
            GameRegistry.getInstance().getClimate(resultSet.getInt("Climate")),
            Equipment.Slot.values()[resultSet.getInt("EquipableSlot")]
        );
    }

    @Override
    public List<Equipment> getAll() {
        try {
            return query(db.prepare(DEFAULT_QUERY));
        } catch(SQLException e) {
            // TODO Proper logging?
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Could not retrieve stuff", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Equipment> get(int id) {
        return get(db.prepare(DEFAULT_QUERY + " where IdItem = ?").set(1, id));
    }

    @Override
    public Optional<Equipment> get(String name) {
        return get(db.prepare(DEFAULT_QUERY + " where Name = ?").set(1, name));
    }

    @Override
    public void add(Equipment item) {
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
                        + String.join(", ", EQUIPMENT_FIELDS)
                        + ") values ("
                        + Stream.of(EQUIPMENT_FIELDS).map(e -> "?").collect(Collectors.joining(", "))
                        + ")"
                )
                    .set(1, item.id) // FIXME This is not right, need to insert both at once or get the id of the inserted item
                    .set(2, item.equipmentSlot.ordinal())
            );

        if(stmt.exec() != 2) {
            db.rollback();
            // TODO Throw!
        }

        db.commit();
    }

    @Override
    public void update(Equipment item) {
        // TODO
        throw new UnsupportedOperationException();
    }
}
