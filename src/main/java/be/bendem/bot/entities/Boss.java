package be.bendem.bot.entities;

import be.bendem.bot.dices.Dice;

import java.util.List;

/**
 * @author bendem
 */
public class Boss extends Monster {

    protected Boss(List<Dice> dices, String name, int maxHealth) {
        super(dices, name, maxHealth);
    }
}
