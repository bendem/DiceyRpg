package be.bendem.bot.game;

import be.bendem.bot.utils.Identifiable;

public class Climate implements Identifiable {

    public final int id;
    public final String name;
    public final String description;

    public Climate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

}
