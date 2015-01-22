package be.bendem.bot.handler;

import be.bendem.bot.RpgBot;
import org.kitteh.irc.client.library.EventHandler;
import org.kitteh.irc.client.library.event.client.ClientConnectionClosedEvent;

public class CommonEventHandler {

    private RpgBot rpgBot;

    public CommonEventHandler(RpgBot rpgBot) {
        this.rpgBot = rpgBot;
    }

    @EventHandler
    public void handleQuit(ClientConnectionClosedEvent e) {
        if(!e.isReconnecting()) {
            rpgBot.database.close();
        }
    }
}
