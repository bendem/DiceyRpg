package be.bendem.bot.entities;

public abstract class Character {

    public final int id;
    public final String name;
    public final int maxHealth;
    private int currentHealth;

    protected Character(int id, String name, int maxHealth) {
        this.id = id;
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

}
