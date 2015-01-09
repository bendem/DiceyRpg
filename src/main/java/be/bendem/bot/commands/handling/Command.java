package be.bendem.bot.commands.handling;

import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.List;

public interface Command {

    public void perform(Client client, Channel channel, User user, List<String> arguments);
    public boolean hasPermission(Channel channel, User user);
    public String getName();
    public String[] getUsage();

}
