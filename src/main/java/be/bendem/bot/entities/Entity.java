package be.bendem.bot.entities;

import be.bendem.bot.dice.Die;
import be.bendem.bot.game.CampaignPart;

import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public abstract class Entity {

    protected List<Die> dice;
    protected final String name;
    private int currentHealth;

    protected Entity(List<Die> dice, String name, int currentHealth) {
        this.dice = dice;
        this.name = name;
        this.currentHealth = currentHealth;
    }

    public abstract boolean takeTurn(CampaignPart campaignPart, Random random);

    public List<Die> getDice() {
        return dice;
    }

    public void setDice(List<Die> dice) {
        this.dice = dice;
    }

    public String getName() {
        return name;
    }

    public abstract int getMaxHealth();

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void damage(int damage) {
        currentHealth -= damage > currentHealth ? currentHealth : damage;
    }

    public void heal(int heal) {
        currentHealth += heal;
    }

    public abstract void shield(int shield);
    public abstract void poison(int damage, int turns);

    public boolean isDead() {
        return currentHealth == 0;
    }

}
