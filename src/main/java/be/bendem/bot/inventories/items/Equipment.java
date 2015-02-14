package be.bendem.bot.inventories.items;

import be.bendem.bot.game.Climate;

public class Equipment extends Item {

    public final Slot equipmentSlot;
    // TODO Attributes
    //public final Attributes attributes;

    public Equipment(int id, String name, String description, int value, Rank rank, int levelRequired, int dropProbability, Climate dropClimate, Slot equipmentSlot) {
        super(id, name, description, value, rank, levelRequired, dropProbability, dropClimate, Type.Equipment);
        this.equipmentSlot = equipmentSlot;
    }

    public static enum Slot {

        Head,
        Chest,
        Belt,
        Legs,
        Feet,
        Gloves,
        Cape,
        Necklace,
        Ring1,
        Ring2,
        ;

    }

}
