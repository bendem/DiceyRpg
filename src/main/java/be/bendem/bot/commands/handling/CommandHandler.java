package be.bendem.bot.commands.handling;

import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    public void handle(String message, Channel channel, User user) {
        String[] args = message.split("\\s+");
        if(args.length == 0) {
            return;
        }
        String commandName = args[0];
        Command command = commands.get(commandName);
        if(command == null) {
            return;
        }

        if(!command.hasPermission(channel, user)) {
            // Send no permission message
            return;
        }

        command.perform(channel, user, new ArrayList<>(Arrays.asList(args).subList(1, args.length)));
    }

}
