package be.bendem.bot.inventories.items;

import be.bendem.bot.game.Climate;

public class Die extends Item {

    public final Type type;
    public final int min;
    public final int max;
    public final boolean canFail;

    public Die(int id, String name, String description, int value, Rank rank, int levelRequired, int dropProbability, Climate dropClimate, Die.Type type, int min, int max, boolean canFail) {
        super(id, name, description, value, rank, levelRequired, dropProbability, dropClimate, Item.Type.Die);
        this.type = type;
        this.min = min;
        this.max = max;
        this.canFail = canFail;
    }

    public enum Type {
        Heal,
        Attack,
        Shield,
        Poison,
        ;
    }

}
