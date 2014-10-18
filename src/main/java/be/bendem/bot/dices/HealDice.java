package be.bendem.bot.dices;

import be.bendem.bot.entities.Entity;

/**
 * @author bendem
 */
public class HealDice extends Dice {

    public HealDice() {
        this(1, 6);
    }

    public HealDice(int min, int max) {
        super(min, max, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyTo(Entity target, int result) {
        target.heal(result);
    }

}
