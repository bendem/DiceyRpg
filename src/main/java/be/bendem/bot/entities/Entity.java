package be.bendem.bot.entities;

import be.bendem.bot.dices.Dice;
import be.bendem.bot.game.CampaignPart;

import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public abstract class Entity {

    protected List<Dice> dices;
    protected final String name;
    protected int health;
    protected int maxHealth;

    protected Entity(List<Dice> dices, String name, int maxHealth) {
        this(dices, name, maxHealth, maxHealth);
    }

    protected Entity(List<Dice> dices, String name, int health, int maxHealth) {
        this.dices = dices;
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    public abstract boolean takeTurn(CampaignPart campaignPart, Random random);

    public List<Dice> getDices() {
        return dices;
    }

    public void setDices(List<Dice> dices) {
        this.dices = dices;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

}
