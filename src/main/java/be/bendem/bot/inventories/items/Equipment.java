package be.bendem.bot.inventories.items;

import be.bendem.bot.inventories.EquipmentSlot;

public class Equipment extends Item {

    public final EquipmentSlot equipmentSlot;

    public Equipment(int id, String name, String description, int value, Rank rank, EquipmentSlot equipmentSlot) {
        super(id, name, description, value, rank, ItemType.Equipment);
        this.equipmentSlot = equipmentSlot;
    }

}
