package be.bendem.bot;

import be.bendem.bot.commands.LoadCommand;
import be.bendem.bot.commands.SaveCommand;
import be.bendem.bot.commands.StartCommand;
import be.bendem.bot.commands.handling.Command;
import be.bendem.bot.commands.handling.CommandHandler;
import org.kitteh.irc.client.library.AuthType;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.ClientBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author bendem
 */
public class RpgBot {

    private final CommandHandler commandHandler;
    public final Client client;

    public RpgBot() {
        commandHandler = new CommandHandler(this, ".");
        load();

        client = new ClientBuilder()

            .auth(AuthType.NICKSERV, "bendem", "<redacted>")
            .realName("TODO")
            .nick("TODO")

            .secure(true)
            .server(6697)
            .server("ipv6.irc.esper.net")

            .build();
    }

    private void load() {
        register(new StartCommand());
        register(new SaveCommand());
        register(new LoadCommand());
    }

    private void register(Command command) {
        commandHandler.register(command);
    }

    public static void main(final String args[]) {
        RpgBot bot = new RpgBot();

        while(!System.console().readLine().equalsIgnoreCase("stop"));
        bot.client.shutdown("Bye peps...");
    }

    private static final Logger logger = Logger.getLogger("Bot");
    static {
        logger.setLevel(Level.ALL);
    }

    public static Logger getLogger() {
        return logger;
    }

}
