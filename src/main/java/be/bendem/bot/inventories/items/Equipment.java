package be.bendem.bot.inventories.items;

public class Equipment extends Item {

    public final Slot equipmentSlot;

    public Equipment(int id, String name, String description, int value, Rank rank, Slot equipmentSlot) {
        super(id, name, description, value, rank, Type.Equipment);
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
