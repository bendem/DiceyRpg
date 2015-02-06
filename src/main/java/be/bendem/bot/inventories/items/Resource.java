package be.bendem.bot.inventories.items;

public class Resource extends Item {

    public Resource(int id, String name, String description, int value, Rank rank) {
        super(id, name, description, value, rank, ItemType.Resource);
    }

}
