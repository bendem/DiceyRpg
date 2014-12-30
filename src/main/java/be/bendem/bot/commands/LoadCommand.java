package be.bendem.bot.commands;

import be.bendem.bot.commands.handling.BaseCommand;
import be.bendem.bot.commands.handling.Command;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.List;

/**
 * @author bendem
 */
public class LoadCommand extends BaseCommand {

    public LoadCommand() {
        super("load", "game.load", "Loads a saved game - Usage ## <save-id>");
    }

    @Override
    public void perform(Channel channel, User user, List<String> arguments) {
    }

}
