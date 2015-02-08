package be.bendem.bot.entities;

import be.bendem.bot.entities.attributes.Attributes;

/**
 * @author bendem
 */
public class Player extends Character {

    public Player(int id, String name, Attributes attributes, int level) {
        super(id, name, attributes, level);
    }

    public void incrementLevel() {
        // TODO That should be done with attribute changes and health recomputation
        ++level;
    }

}