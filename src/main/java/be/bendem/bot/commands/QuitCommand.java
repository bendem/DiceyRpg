package be.bendem.bot.commands;

import be.bendem.bot.commands.handling.BaseCommand;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.List;

public class QuitCommand extends BaseCommand {

    public QuitCommand() {
        super("quit", "admin.quit", "Disconnects the bot - Usage: ##");
    }

    @Override
    public void perform(Client client, Channel channel, User user, List<String> arguments) {
        client.shutdown(user.getName() + " told me to quit.");
    }

}
