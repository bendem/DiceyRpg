package be.bendem.bot.storage;

import be.bendem.bot.entities.Monster;
import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Item;

import java.util.*;

public class GameRegistry {

    private static GameRegistry instance;
    public static GameRegistry getInstance() {
        return instance;
    }

    private final Database database;
    private final Set<Climate> climates;
    private final Map<Integer, Climate> climatesById;
    private final Map<String, Climate> climatesByName;
    private final Set<Item> items;
    private final Map<Integer, Item> itemsById;
    private final Map<String, Item> itemsByName;
    private final Set<Monster> monsters;
    private final Map<Integer, Monster> monstersById;
    private final Map<String, Monster> monstersByName;

    public GameRegistry(Database db) {
        database = db;
        climates = new HashSet<>();
        climatesById = new HashMap<>();
        climatesByName = new HashMap<>();
        items = new HashSet<>();
        itemsById = new HashMap<>();
        itemsByName = new HashMap<>();
        monsters = new HashSet<>();
        monstersById = new HashMap<>();
        monstersByName = new HashMap<>();

        load();

        instance = this;
    }

    private void load() {
        climates.addAll(database.getModel(Climate.class).getAll());
        climates.forEach(climate -> {
            climatesById.put(climate.id, climate);
            climatesByName.put(climate.name, climate);
        });

        items.addAll(database.getModel(Item.class).getAll());
        items.forEach(item -> {
            itemsById.put(item.id, item);
            itemsByName.put(item.name, item);
        });

        monsters.addAll(database.getModel(Monster.class).getAll());
        monsters.forEach(monster -> {
            monstersById.put(monster.id, monster);
            monstersByName.put(monster.name, monster);
        });
    }

    public Climate getClimate(int id) {
        return climatesById.get(id);
    }

    public Climate getClimate(String name) {
        return climatesByName.get(name);
    }

    public Item getItem(int id) {
        return itemsById.get(id);
    }

    public Item getItem(String name) {
        return itemsByName.get(name);
    }

    public Monster getMonster(int id) {
        return monstersById.get(id);
    }

    public Monster getMonster(String name) {
        return monstersByName.get(name);
    }

}
