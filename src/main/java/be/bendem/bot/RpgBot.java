package be.bendem.bot;

import be.bendem.bot.commands.LoadCommand;
import be.bendem.bot.commands.SaveCommand;
import be.bendem.bot.commands.StartCommand;
import fr.ribesg.alix.api.Client;
import fr.ribesg.alix.api.Log;
import fr.ribesg.alix.api.Server;
import fr.ribesg.alix.api.bot.command.Command;
import fr.ribesg.alix.api.bot.command.CommandManager;
import fr.ribesg.alix.api.bot.config.AlixConfiguration;
import fr.ribesg.alix.api.bot.util.PasteUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;

/**
 * @author bendem
 */
public class RpgBot extends Client {

    private final AlixConfiguration config;
    private final CommandManager commandManager;

    public RpgBot() {
        super("RpgBot");
        logger.info("Starting up...");

        PasteUtil.setMode(null);

        config = new AlixConfiguration("");
        if(!config.exists()) {
            try {
                config.save();
            } catch(IOException e) {
                getLogger().error("Error while loading config", e);
            }
        }

        commandManager = createCommandManager("!", Collections.EMPTY_SET);
        loadItMyself();
        getServers().forEach(Server::connect);
    }

    private void loadItMyself() {
        getServers().addAll(config.getServers());

        register(new StartCommand());
        register(new SaveCommand());
        register(new LoadCommand());
    }

    private void register(Command command) {
        commandManager.registerCommand(command);
    }

    @Override
    protected boolean load() {
        return true;
    }

    public static void main(final String args[]) {
        Log.get().setLevel(Level.INFO);
        RpgBot bot = new RpgBot();

        while(!System.console().readLine().equalsIgnoreCase("stop"));
        bot.kill();
    }

    private static final Logger logger = Logger.getLogger("Georges");
    static {
        logger.setLevel(Level.ALL);
    }

    public static Logger getLogger() {
        return logger;
    }

}
