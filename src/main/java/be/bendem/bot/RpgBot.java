package be.bendem.bot;

import be.bendem.bot.commands.LoadCommand;
import be.bendem.bot.commands.QuitCommand;
import be.bendem.bot.commands.SaveCommand;
import be.bendem.bot.commands.StartCommand;
import be.bendem.bot.commands.handling.Command;
import be.bendem.bot.commands.handling.CommandHandler;
import be.bendem.bot.config.Config;
import be.bendem.bot.hander.CommonEventHandler;
import be.bendem.bot.storage.DataBase;
import org.kitteh.irc.client.library.Client;
import org.kitteh.irc.client.library.ClientBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author bendem
 */
public class RpgBot {

    private final CommandHandler commandHandler;
    public final Config config;
    public final Client client;
    public final DataBase dataBase;

    public RpgBot() {
        Path dataFolder = Paths.get(".", "data");
        config = new Config(dataFolder.resolve("bot.cfg"));

        if(!config.load()) {
            throw new RuntimeException("New config file created, fill it before restarting the application");
        }

        try {
            dataBase = new DataBase(dataFolder.resolve("db.h2"));
        } catch (SQLException e) {
            throw new RuntimeException("Could not access setup db", e);
        }
        client = new ClientBuilder()

            .realName(config.get(Config.Key.Realname))
            .nick(config.get(Config.Key.Nickname))

            .user(config.get(Config.Key.ServerUsername))
            .secure(Boolean.valueOf(config.get(Config.Key.ServerSsl)))
            .server(Integer.valueOf(config.get(Config.Key.ServerPort)))
            .server(config.get(Config.Key.ServerAddress))
            .serverPassword(config.get(Config.Key.ServerPassword)) // ZNC

            .listenException(Throwable::printStackTrace)
            .listenInput(str  -> System.out.println("< " + str))
            .listenOutput(str -> System.out.println("> " + str))

            .build();

        Arrays.stream(config.get(Config.Key.Channels).split(","))
            .map(c -> c.startsWith("#") ? c : '#' + c)
            .forEach(client::addChannel);

        new CommonEventHandler(this);
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
