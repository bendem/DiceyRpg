package be.bendem.bot.inventories.items;

import be.bendem.bot.game.Climate;

import java.util.Iterator;
import java.util.List;

public class DiceSet extends Item implements Iterable<Die> {

    private int setSize;
    private final List<Die> content;

    public DiceSet(int id, String name, String description, int value, Rank rank, int levelRequired, int dropProbability, Climate dropClimate, int setSize, List<Die> content) {
        super(id, name, description, value, rank, levelRequired, dropProbability, dropClimate, Type.DiceSet);
        this.setSize = setSize;
        this.content = content;
    }

    public int getSetSize() {
        return setSize;
    }

    public void upgrade() {
        ++setSize;
    }

    public void add(Die die) {
        if(content.size() == setSize) {
            return; // TODO Throw exception
        }
        content.add(die);
    }

    public void remove(Die die) {
        content.remove(die);
    }

    public Iterator<Die> iterator() {
        return content.iterator();
    }

}
