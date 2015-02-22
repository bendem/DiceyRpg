package be.bendem.bot.utils;

public interface Identifiable {

    public int getId();
    public default String getName() {
        throw new UnsupportedOperationException(getClass().getName() + " is not identifiable by name");
    }

}
