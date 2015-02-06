package be.bendem.bot.entities;

import be.bendem.bot.inventories.items.Die;
import be.bendem.bot.inventories.items.Item;

import java.util.List;
import java.util.Map;

public class Monster extends Character implements Cloneable {

    private List<Die> dice;
    private Map<Item, Byte> drops;

    protected Monster(int id, String name, int maxHealth) {
        super(id, name, maxHealth);
    }

}
