package be.bendem.bot.commands.handling;

import be.bendem.bot.RpgBot;
import org.kitteh.irc.client.library.EventHandler;
import org.kitteh.irc.client.library.IRCFormat;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent;
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final RpgBot rpgBot;
    private final Map<String, Command> commands;
    private final String commandPrefix;

    public CommandHandler(RpgBot rpgBot, String prefix) {
        this.rpgBot = rpgBot;
        this.commandPrefix = prefix;
        this.commands = new HashMap<>();

        rpgBot.client.getEventManager().registerEventListener(this);
    }

    public void register(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    public void handle(String message, Channel channel, User user) {
        String[] args = message.split("\\s+");
        if(args.length == 0) {
            return;
        }
        String commandName = args[0].toLowerCase();
        Command command = commands.get(commandName);
        if(command == null) {
            return;
        }

        if(!command.hasPermission(channel, user)) {
            // Send no permission message
            rpgBot.client.sendMessage(channel.getName(), user.getNick() + IRCFormat.RED + ", You don't have the permission to do that");
            return;
        }

        command.perform(rpgBot.client, channel, user, new ArrayList<>(Arrays.asList(args).subList(1, args.length)));
    }

    @EventHandler
    public void onChannelMessage(ChannelMessageEvent event) {
        if(event.getMessage().startsWith(commandPrefix)) {
            handle(event.getMessage().substring(commandPrefix.length()), event.getChannel(), ((User) event.getActor()));
        }
    }

    @EventHandler
    public void onPrivateMessage(PrivateMessageEvent event) {
        handle(event.getMessage(), null, ((User) event.getActor()));
    }

}
