package be.bendem.bot.dices;

import be.bendem.bot.entities.Entity;

/**
 * @author bendem
 */
public class PoisonDice extends Dice {

    private final int damage;

    public PoisonDice(int damage) {
        this(damage, 1, 6);
    }

    protected PoisonDice(int damage, int min, int max) {
        super(min, max, true);
        this.damage = damage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyTo(Entity target, int result) {
        target.poison(damage, result);
    }

}
