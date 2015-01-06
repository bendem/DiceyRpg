package be.bendem.bot.items;

import be.bendem.bot.dice.Die;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author bendem
 */
public class DiceSet implements Item, Iterable<Die> {

    protected List<Die> dice;

    @Override
    public ItemType getType() {
        return ItemType.DieSet;
    }

    @Override
    public Iterator<Die> iterator() {
        return dice.iterator();
    }

    @Override
    public void forEach(Consumer<? super Die> consumer) {
        dice.forEach(consumer);
    }

    @Override
    public Spliterator<Die> spliterator() {
        return dice.spliterator();
    }
}
