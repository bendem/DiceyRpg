package be.bendem.bot.entities;

import be.bendem.bot.dices.Dice;
import be.bendem.bot.game.CampaignPart;

import java.util.List;
import java.util.Random;

/**
 * @author bendem
 */
public class Monster extends Entity {

    protected Monster(List<Dice> dices, String name, int maxHealth) {
        super(dices, name, maxHealth);
    }

    @Override
    public boolean takeTurn(CampaignPart campaignPart, Random random) {
        // TODO
        return false;
    }
}
