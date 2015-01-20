package be.bendem.bot.commands.handling;

import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

public abstract class BaseCommand implements Command {

    protected final String name;
    protected final String permission;
    protected final String[] usage;

    public BaseCommand(String name, String permission, String... usage) {
        this.name = name;
        this.permission = permission;
        this.usage = usage;
    }

    @Override
    public boolean hasPermission(Channel channel, User user) {
        return user.getNick().equals("bendem"); // TODO Implement this with nick registration check and stuff
    }

    @Override
    public String getName() {
        return name;
    }

    public String[] getUsage() {
        return usage;
    }

}
