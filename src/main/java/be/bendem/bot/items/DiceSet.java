package be.bendem.bot.items;

import be.bendem.bot.dices.Dice;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author bendem
 */
public class DiceSet implements Item, Iterable<Dice> {

    protected List<Dice> dices;

    @Override
    public ItemType getType() {
        return ItemType.DiceSet;
    }

    @Override
    public Iterator<Dice> iterator() {
        return dices.iterator();
    }

    @Override
    public void forEach(Consumer<? super Dice> consumer) {
        dices.forEach(consumer);
    }

    @Override
    public Spliterator<Dice> spliterator() {
        return dices.spliterator();
    }
}
