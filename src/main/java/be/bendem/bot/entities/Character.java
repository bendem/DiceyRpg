package be.bendem.bot.entities;

import be.bendem.bot.entities.attributes.Attribute;
import be.bendem.bot.entities.attributes.Attributes;

public abstract class Character {

    private static final int BASE_HEALTH = 20;
    private static final int BASE_HEALTH_PER_LEVEL = 10;
    private static final float HEALTH_LEVEL_MODIFIER = 0.2f;

    public final int id;
    public final String name;
    public final Attributes attributes;
    protected int level;
    protected int maxHealth;
    protected int currentHealth;

    protected Character(int id, String name, Attributes attributes) {
        this(id, name, attributes, 1);
    }

    protected Character(int id, String name, Attributes attributes, int level) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
        this.level = level;
        this.maxHealth = computeHealth(level, attributes.get(Attribute.Constitution));
        this.currentHealth = maxHealth;
    }

    public int getLevel() {
        return level;
    }

    private static int computeHealth(int level, int constitution) {
        // TODO That's really basic, maybe it needs rethinking
        int health = BASE_HEALTH;
        for(int i = 1; i <= level; ++i) {
            health += BASE_HEALTH_PER_LEVEL * (i + HEALTH_LEVEL_MODIFIER);
        }
        return health + constitution;
    }

}
