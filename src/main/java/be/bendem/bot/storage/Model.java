package be.bendem.bot.storage;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface Model<T> {

    public String getTable();
    public Collection<T> getAll();
    public Collection<T> getAll(Predicate<T> predicate);
    public Optional<T> getFirst(Predicate<T> predicate);
    public boolean add(T item);

}
