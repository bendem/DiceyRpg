package be.bendem.bot.entities;

import be.bendem.bot.dices.Dice;
import be.bendem.bot.game.CampaignPart;
import be.bendem.bot.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public class Player extends Entity {

    private static final int BASE_HEALTH = 20;
    private static final int HEALTH_PER_LEVEL = 10;

    private final List<Item> inventory;
    private int level;

    public Player(List<Dice> dices, String name, int maxHealth) {
        super(dices, name, maxHealth);
        inventory = new ArrayList<>();
        level = 1;
    }

    @Override
    public boolean takeTurn(CampaignPart campaignPart, Random random) {
        // TODO
        return false;
    }

    @Override
    public int getMaxHealth() {
        return BASE_HEALTH + level * HEALTH_PER_LEVEL;
    }

    @Override
    public void shield(int shield) {
        // TODO
    }

    @Override
    public void poison(int damage, int turns) {
        // TODO
    }

    public int getLevel() {
        return level;
    }

    public void incrementLevel() {
        level++;
        // TODO Increment stats, give custom stat point(s), etc.
    }

}
