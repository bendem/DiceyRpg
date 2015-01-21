package be.bendem.bot.entities;

import be.bendem.bot.dice.Die;
import be.bendem.bot.game.CampaignPart;
import be.bendem.bot.items.Item;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public class Monster extends Entity {

    private final int maxHealth;

    protected Monster(List<Die> dice, String name, int health) {
        super(dice, name, health);
        maxHealth = health;
    }

    @Override
    public boolean takeTurn(CampaignPart campaignPart, Random random) {
        // TODO
        return false;
    }

    public Collection<Item> loot(Random random) {
        // TODO
        return Collections.emptySet();
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void shield(int shield) {
        // TODO
    }

    @Override
    public void poison(int damage, int turns) {
        // TODO
    }

}
