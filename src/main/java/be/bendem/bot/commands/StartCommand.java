package be.bendem.bot.commands;

import be.bendem.bot.commands.handling.BaseCommand;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.List;

/**
 * @author bendem
 */
public class StartCommand extends BaseCommand {

    /**
     * {@inheritDoc}
     */
    public StartCommand() {
        super("start", "game.start", "Starts a new game.");
    }

    @Override
    public void perform(Client client, Channel channel, User user, List<String> args) {
    }

}
