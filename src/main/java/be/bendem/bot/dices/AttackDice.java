package be.bendem.bot.dices;

import be.bendem.bot.entities.Entity;

/**
 * @author bendem
 */
public class AttackDice extends Dice {

    public AttackDice() {
        this(1, 6);
    }

    public AttackDice(int min, int max) {
        super(min, max, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyTo(Entity target, int result) {
        target.damage(result);
    }

}
