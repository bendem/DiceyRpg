package be.bendem.bot.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private static final Random RANDOM = ThreadLocalRandom.current();

    public static <T extends Enum<T>> T nextEnum(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[RANDOM.nextInt(enumConstants.length)];
    }

}
