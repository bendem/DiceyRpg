package be.bendem.bot.dice;

import be.bendem.bot.entities.Entity;
import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.inventories.items.ItemType;
import be.bendem.bot.utils.Sanity;

import java.util.Random;

/**
 * Base class of the dices used to fight.
 *
 * @author bendem
 */
public abstract class Die implements Item {

    protected final int min;
    protected final int max;
    protected final boolean fails;

    /**
     * Creates a Die with a minimum and a maximum of faces and wether or not
     * the entity should receive a turn penalty when 1 is rolled.
     *
     * @param min the minimum number a roll can be
     * @param max the maximum number a roll can be
     * @param fails wether rolling 1 causes a turn penalty
     */
    protected Die(int min, int max, boolean fails) {
        Sanity.truthness(max >= min, "The max result of a dice should be greater than the min");
        this.min = min;
        this.max = max;
        this.fails = fails;
    }

    /**
     * Applies the result of a roll to the target.
     *
     * @param target the target to apply the result to
     */
    public abstract void applyTo(Entity target, int result);

    /**
     * Rolls the dice using the given random generator.
     *
     * @param random the random generator to use
     * @return the roll
     */
    protected int roll(Random random) {
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * Returns wether the dice can fail or not. If it returns true and 1 is
     * rolled, the entity lost is turn.
     *
     * @return wether the dice can fail or not
     */
    public boolean canFail() {
        return fails;
    }

    @Override
    public ItemType getType() {
        return ItemType.Die;
    }

}
