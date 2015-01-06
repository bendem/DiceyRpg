package be.bendem.bot.utils;

/**
 * @author bendem
 */
public class Sanity {

    public static void truthness(boolean condition, String message) {
        if(!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void nullness(Object object, String message) {
        if(object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void nullness(String message, Object ... objects) {
        for(Object object : objects) {
            if(object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

}
