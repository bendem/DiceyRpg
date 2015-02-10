package be.bendem.bot.entities;

import be.bendem.bot.entities.attributes.Attribute;
import be.bendem.bot.entities.attributes.Attributes;
import be.bendem.bot.utils.RandomUtils;

public class Monster extends Character {

    // TODO Monster dice and drops (based on their levels)
    //private final List<Die> dice;
    //private final Map<Item, Byte> drops;

    /**
     * Creates a base level 1 monster to put in the game registry
     *
     * @param id         the monster id
     * @param name       the monster's name
     * @param attributes the attributes of the monster
     */
    public Monster(int id, String name, Attributes attributes) {
        super(id, name, attributes);
    }

    /**
     * Creates a monster with randomized attributes based on his level
     *
     * @param monster the monster to get basic informations from
     * @param level   the level of the monster
     */
    public Monster(Monster monster, int level) {
        super(monster.id, monster.name, randomAttributes(monster.attributes, level), level);
    }

    private static Attributes randomAttributes(Attributes attributes, int level) {
        for(int i = 0; i < level; ++i) {
            attributes.add(RandomUtils.nextEnum(Attribute.class), 1);
        }
        return attributes;
    }

}
