package be.bendem.bot.inventories.items;

public class Die extends Item {

    private final Type type;
    public final int min;
    public final int max;
    public final boolean canFail;

    public Die(int id, String name, String description, int value, Rank rank, Type type, int min, int max, boolean canFail) {
        super(id, name, description, value, rank, ItemType.Die);
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
