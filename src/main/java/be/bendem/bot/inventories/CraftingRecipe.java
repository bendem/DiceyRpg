package be.bendem.bot.inventories;

import be.bendem.bot.inventories.items.Item;
import be.bendem.bot.inventories.items.Resource;

import java.util.Collections;
import java.util.Map;

public class CraftingRecipe {

    public final Map<Resource, Integer> ingredients;
    public final Item result;
    public final int levelRequired;

    public CraftingRecipe(Map<Resource, Integer> ingredients, Item result, int levelRequired) {
        this.ingredients = Collections.unmodifiableMap(ingredients);
        this.result = result;
        this.levelRequired = levelRequired;
    }

}
