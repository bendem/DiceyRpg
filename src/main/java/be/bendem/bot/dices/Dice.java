package be.bendem.bot.dices;

import be.bendem.bot.entities.Entity;

/**
 * @author bendem
 */
public abstract class Dice {

    protected final int faces;
    protected final boolean fails;

    protected Dice(int faces, boolean fails) {
        this.faces = faces;
        this.fails = fails;
    }

    public abstract void applyToAlly(Entity ally);
    public abstract void applyToEnemy(Entity enemy);

    public int getFaces() {
        return faces;
    }

    public boolean isFails() {
        return fails;
    }

}
