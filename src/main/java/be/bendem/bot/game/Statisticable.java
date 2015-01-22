package be.bendem.bot.game;

import java.util.Map;

public interface Statisticable {

    public Map<Statistic, Long> getStatistics();
    public long getStatistic(Statistic statistic);

    public enum Statistic {
        Agility, Strength
    }

}
