package be.bendem.bot.entities;

import be.bendem.bot.dice.Die;

import java.util.List;

/**
 * @author bendem
 */
public class Boss extends Monster {

    protected Boss(List<Die> dice, String name, int maxHealth) {
        super(dice, name, maxHealth);
    }
}
