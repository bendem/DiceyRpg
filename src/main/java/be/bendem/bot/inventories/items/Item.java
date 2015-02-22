package be.bendem.bot.inventories.items;

import be.bendem.bot.game.Climate;
import be.bendem.bot.utils.Identifiable;

public abstract class Item implements Identifiable {

    public final int id;
    public final String name;
    public final String description;
    public final int value;
    public final Rank rank;
    public final int levelRequired;
    public final int dropProbability;
    public final Climate dropClimate;
    public final Type type;

    public Item(int id, String name, String description, int value, Rank rank, int levelRequired, int dropProbability, Climate dropClimate, Type type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
        this.rank = rank;
        this.levelRequired = levelRequired;
        this.dropProbability = dropProbability;
        this.dropClimate = dropClimate;
        this.type = type;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
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
