package be.bendem.bot.entities.attributes;

public enum Attribute {

    Strength("Increases your damages"),
    Agility("Increases your chances to dodge an attack"),
    Intelligence("Increases your chances to make a critical strike"),
    Luck("Reduces your chances to fail a crafting recipe"),
    Defense("Reduces the damage you take"),
    Constitution("Increases your maximum health"),
    ;

    public final String description;

    private Attribute(String description) {
        this.description = description;
    }

}
