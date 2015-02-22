package be.bendem.bot.storage;

import java.util.List;
import java.util.Optional;

public interface Model<T> {

    /**
     * Gets all the items handled by this model.
     *
     * @return a collection containing all the item handled by this model
     */
    public List<T> getAll();

    /**
     * Gets the item corresponding to the provided id.
     *
     * @param id the id of the item
     * @return the optional item
     */
    public Optional<T> get(int id);

    /**
     * Gets the item corresponding to the provided name.
     *
     * @param name the name of the item
     * @return the optional item
     * @throws UnsupportedOperationException if the model doesn't handle name lookups
     */
    default public Optional<T> get(String name) {
        throw new UnsupportedOperationException(getClass().getName() + " does not suport name predicates");
    }

    /**
     * Adds an item to the database.
     * <p>
     * The id of the item to insert in the database will be ignored.
     *
     * @param item the item to add
     */
    public void add(T item);

    /**
     * Updates an item of the database
     *
     * @param item the item to update
     * @throws IllegalArgumentException if the id and the item do not correspond to a row in the db
     */
    public void update(T item);

}
