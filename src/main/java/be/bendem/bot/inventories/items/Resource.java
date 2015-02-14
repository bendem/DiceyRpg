package be.bendem.bot.inventories.items;

import be.bendem.bot.game.Climate;

public class Resource extends Item {

    public Resource(int id, String name, String description, int value, Rank rank, int levelRequired, int dropProbability, Climate dropClimate) {
        super(id, name, description, value, rank, levelRequired, dropProbability, dropClimate, Type.Resource);
    }

}
