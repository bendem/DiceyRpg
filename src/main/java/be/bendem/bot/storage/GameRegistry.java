package be.bendem.bot.storage;

import be.bendem.bot.entities.Monster;
import be.bendem.bot.game.Climate;
import be.bendem.bot.inventories.items.Die;
import be.bendem.bot.inventories.items.Equipment;
import be.bendem.bot.inventories.items.Resource;
import be.bendem.bot.utils.Identifiable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameRegistry {

    private static GameRegistry instance;
    public static GameRegistry getInstance() {
        return instance;
    }

    private final Database database;
    private final Set<Climate> climates;
    private final Map<Integer, Climate> climatesById;
    private final Map<String, Climate> climatesByName;
    private final Set<Resource> resources;
    private final Map<Integer, Resource> resourcesById;
    private final Map<String, Resource> resourcesByName;
    private final Set<Die> dice;
    private final Map<Integer, Die> diceById;
    private final Map<String, Die> diceByName;
    private final Set<Equipment> equipments;
    private final Map<Integer, Equipment> equipmentsById;
    private final Map<String, Equipment> equipmentsByName;
    private final Set<Monster> monsters;
    private final Map<Integer, Monster> monstersById;
    private final Map<String, Monster> monstersByName;

    public GameRegistry(Database db) {
        database = db;

        climates = new HashSet<>();
        climatesById = new HashMap<>();
        climatesByName = new HashMap<>();

        resources = new HashSet<>();
        resourcesById = new HashMap<>();
        resourcesByName = new HashMap<>();

        dice = new HashSet<>();
        diceById = new HashMap<>();
        diceByName = new HashMap<>();

        equipments = new HashSet<>();
        equipmentsById = new HashMap<>();
        equipmentsByName = new HashMap<>();

        monsters = new HashSet<>();
        monstersById = new HashMap<>();
        monstersByName = new HashMap<>();

        instance = this;

        load(Climate.class, climates, climatesById, climatesByName);

        load(Resource.class, resources, resourcesById, resourcesByName);
        load(Die.class, dice, diceById, diceByName);
        load(Equipment.class, equipments, equipmentsById, equipmentsByName);

        load(Monster.class, monsters, monstersById, monstersByName);
    }

    private <T extends Identifiable> void load(Class<T> type, Set<T> all, Map<Integer, T> byId, Map<String, T> byName) {
        all.addAll(database.getModel(type).getAll());
        all.forEach(element -> {
            byId.put(element.getId(), element);
            byName.put(element.getName(), element);
        });
    }

    public Climate getClimate(int id) {
        return climatesById.get(id);
    }

    public Climate getClimate(String name) {
        return climatesByName.get(name);
    }

    public Resource getResource(int id) {
        return resourcesById.get(id);
    }

    public Resource getResource(String name) {
        return resourcesByName.get(name);
    }

    public Monster getMonster(int id) {
        return monstersById.get(id);
    }

    public Monster getMonster(String name) {
        return monstersByName.get(name);
    }

}
