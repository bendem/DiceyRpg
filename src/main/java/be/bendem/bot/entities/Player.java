package be.bendem.bot.entities;

import be.bendem.bot.dices.Dice;
import be.bendem.bot.game.CampaignPart;

import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public class Player extends Entity {

    protected int level;

    protected Player(List<Dice> dices, String name, int maxHealth) {
        super(dices, name, maxHealth);
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
