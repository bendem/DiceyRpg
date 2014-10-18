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
    private int currentHealth;

    protected Entity(List<Dice> dices, String name, int currentHealth) {
        this.dices = dices;
        this.name = name;
        this.currentHealth = currentHealth;
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

    public boolean isDead() {
        return currentHealth == 0;
    }

}
