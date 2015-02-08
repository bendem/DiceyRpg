package be.bendem.bot.entities.attributes;

import java.util.EnumMap;
import java.util.Map;

public class Attributes {

    private Map<Attribute, Integer> attributes;

    public Attributes() {
        attributes = new EnumMap<>(Attribute.class);
    }

    public Attributes(Map<Attribute, Integer> attributes) {
        this.attributes = new EnumMap<>(attributes);
    }

    public int get(Attribute attribute) {
        return attributes.getOrDefault(attribute, 0);
    }

    public int set(Attribute attribute, int value) {
        if(value < 0) {
            value = 0;
        }
        attributes.put(attribute, value);
        return value;
    }

    public int add(Attribute attribute, int value) {
        int newValue = get(attribute) + value;
        return set(attribute, newValue);
    }

    public int remove(Attribute attribute, int value) {
        int newValue = get(attribute) - value;
        return set(attribute, newValue);
    }

}
