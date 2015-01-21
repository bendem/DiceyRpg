package be.bendem.bot.dices;

import be.bendem.bot.entities.Entity;

/**
 * @author bendem
 */
public class ShieldDice extends Dice {

    public ShieldDice(int min, int max) {
        super(min, max, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyTo(Entity target, int result) {
        target.shield(result);
    }
}
