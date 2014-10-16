package be.bendem.bot.game;

import be.bendem.bot.entities.Entity;

import java.util.List;

/**
 * @author bendem
 */
public class CampaignPart {

    private final List<Entity> allies;
    private final List<Entity> enemies;

    public CampaignPart(List<Entity> allies, List<Entity> enemies) {
        this.allies = allies;
        this.enemies = enemies;
    }
}
