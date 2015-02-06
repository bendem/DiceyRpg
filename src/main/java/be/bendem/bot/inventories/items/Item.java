package be.bendem.bot.inventories.items;

/**
 * An item represents something a player can store in its inventories.
 *
 * @author bendem
 */
public class Item {

    public final int id;
    public final String name;
    public final String description;
    public final int value;
    public final Rank rank;
    public final ItemType type;

    public Item(int id, String name, String description, int value, Rank rank, ItemType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.rank = rank;
        this.type = type;
    }

}
