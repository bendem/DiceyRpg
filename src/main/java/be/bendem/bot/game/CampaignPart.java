package be.bendem.bot.game;

import be.bendem.bot.entities.Character;

import java.util.List;

/**
 * @author bendem
 */
public class CampaignPart {

    private final List<Character> allies;
    private final List<Character> enemies;

    public CampaignPart(List<Character> allies, List<Character> enemies) {
        this.allies = allies;
        this.enemies = enemies;
    }
}
