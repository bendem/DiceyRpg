package be.bendem.bot.inventories.items;

public abstract class Item {

    public final int id;
    public final String name;
    public final String description;
    public final int value;
    public final Rank rank;
    public final Type type;

    public Item(int id, String name, String description, int value, Rank rank, Type type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.rank = rank;
        this.type = type;
    }

    public enum Rank {

        Junk,
        Normal,
        Good,
        Epic,
        Legendary,
        Unique,
        ;

    }

    public enum Type {

        Die,
        DiceSet,
        Resource,
        Equipment,
        ;

    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Item)) return false;
        return id == ((Item) o).id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
