package be.bendem.bot.entities;

import be.bendem.bot.entities.attributes.Attribute;
import be.bendem.bot.entities.attributes.Attributes;
import be.bendem.bot.inventories.items.Die;
import be.bendem.bot.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monster extends Character {

    public final List<Die> dice;

    /**
     * Creates a base level 1 monster to put in the game registry
     *
     * @param id         the monster id
     * @param name       the monster's name
     * @param attributes the attributes of the monster
     */
    public Monster(int id, String name, List<Die> dice, Attributes attributes) {
        super(id, name, attributes);
        // Copy the list and make it unmodifiable, we don't want a view of somewhere else
        this.dice = Collections.unmodifiableList(new ArrayList<>(dice));
    }

    /**
     * Creates a monster with randomized attributes based on his level
     *
     * @param monster the monster to get basic informations from
     * @param level   the level of the monster
     */
    public Monster(Monster monster, int level) {
        super(monster.id, monster.name, randomAttributes(monster.attributes, level), level);
        dice = Collections.unmodifiableList(monster.dice);
    }

    private static Attributes randomAttributes(Attributes attributes, int level) {
        for(int i = 0; i < level; ++i) {
            attributes.add(RandomUtils.nextEnum(Attribute.class), 1);
        }
        return attributes;
    }

}
