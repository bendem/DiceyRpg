package be.bendem.bot.storage;

import be.bendem.bot.inventories.items.Item;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GameRegistry {

    private final Database database;
    private final Set<Item> items;

    public GameRegistry(Database db) {
        database = db;
        items = new CopyOnWriteArraySet<>(); // TODO Check for better collection
    }

    public Item getItem(String name) {
        Optional<Item> item = items.stream()
            .filter(i -> i.name.equals(name))
            .findAny();

        if(!item.isPresent()) {
            item = database.getModel(Item.class).getFirst(i -> i.name.equals(name));
        }

        return item.get();
    }

    public Item getItem(int id) {
        Optional<Item> item = items.stream()
            .filter(i -> i.id == id)
            .findAny();

        if(!item.isPresent()) {
            item = database.getModel(Item.class).getFirst(i -> i.id == id);
        }

        return item.get();
    }

}
