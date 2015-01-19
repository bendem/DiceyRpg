package be.bendem.bot;

import be.bendem.bot.commands.LoadCommand;
import be.bendem.bot.commands.QuitCommand;
import be.bendem.bot.commands.SaveCommand;
import be.bendem.bot.commands.StartCommand;
import be.bendem.bot.commands.handling.Command;
import be.bendem.bot.commands.handling.CommandHandler;
import be.bendem.bot.storage.DataBase;
import org.kitteh.irc.client.library.AuthType;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.ClientBuilder;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author bendem
 */
public class RpgBot {

    private final CommandHandler commandHandler;
    public final Client client;

    public RpgBot() {
        DataBase dataBase;
        try {
            dataBase = new DataBase(new File("./data"));
        } catch (SQLException e) {
            throw new RuntimeException("Could not access setup db", e);
        }

        client = new ClientBuilder()

            .auth(AuthType.NICKSERV, "DiceyGameMaster", "<redacted>")
            .realName("DiceyRpg Game Mater (at your service)")
            .nick("DiceyGameMaster")

            .secure(true)
            .server(6697)
            //.server("ipv6.irc.esper.net")
            .server("irc.esper.net")
            .listenException(Throwable::printStackTrace)
            .listenInput(str  -> System.out.println("< " + str))
            .listenOutput(str -> System.out.println("> " + str))

            .build();

        client.addChannel("#DiceyRpg");

        commandHandler = new CommandHandler(this, ".");
        load();
    }

    private void load() {
        register(new QuitCommand());
        register(new StartCommand());
        register(new SaveCommand());
        register(new LoadCommand());
    }

    private void register(Command command) {
        commandHandler.register(command);
    }

    public static void main(final String args[]) {
        RpgBot bot = new RpgBot();
    }

    private static final Logger logger = Logger.getLogger("Bot");
    static {
        logger.setLevel(Level.ALL);
    }

    public static Logger getLogger() {
        return logger;
    }

}
