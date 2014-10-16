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

    public int getLevel() {
        return level;
    }

    public void incrementLevel() {
        level++;
    }

}
