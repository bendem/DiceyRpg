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
            .filter(i -> i.getName().equals(name))
            .findAny();

        if(!item.isPresent()) {
            item = database.getModel(Item.class).getFirst(i -> i.getName().equals(name));
        }

        return item.get();
    }

    public Item getItem(long id) {
        Optional<Item> item = items.stream()
            .filter(i -> i.getId() == id)
            .findAny();

        if(!item.isPresent()) {
            item = database.getModel(Item.class).getFirst(i -> i.getId() == id);
        }

        return item.get();
    }

}
