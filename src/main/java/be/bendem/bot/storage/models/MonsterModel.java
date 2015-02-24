package be.bendem.bot.storage.models;

import be.bendem.bot.entities.Monster;
import be.bendem.bot.entities.attributes.Attributes;
import be.bendem.bot.storage.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MonsterModel extends BaseModel<Monster> {

    private static final String DEFAULT_QUERY = "select * from monster"
        + " inner join character on (monster.IdCharacter = character.IdCharacter)"
        + " inner join character_has_attribute on (monster.IdCharacter = character.IdCharacter)"
        + " inner join monster_has_die on (monster.IdCharacter = monster_has_die.IdCharacter)";

    private final Database db;

    public MonsterModel(Database db) {
        this.db = db;
    }

    @Override
    protected Monster read(ResultSet resultSet) throws SQLException {
        return new Monster(
            resultSet.getInt("IdCharacter"),
            resultSet.getString("Name"),
            new ArrayList<>(),
            new Attributes() // TODO
        );
    }

    @Override
    public List<Monster> getAll() {
        return null;
    }

    @Override
    public Optional<Monster> get(int id) {
        return null;
    }

    @Override
    public Optional<Monster> get(String name) {
        return null;
    }

    @Override
    public void add(Monster item) {

    }

    @Override
    public void update(Monster item) {

    }
}
