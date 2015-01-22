package be.bendem.bot.inventories.items;

/**
 * An item represents something a player can store in its inventories.
 *
 * @author bendem
 */
public interface Item {

    public long getId();
    public String getName();
    public String getDescription();
    public ItemType getType();

}
