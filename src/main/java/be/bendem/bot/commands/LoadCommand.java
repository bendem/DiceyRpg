package be.bendem.bot.commands;

import fr.ribesg.alix.api.Channel;
import fr.ribesg.alix.api.Server;
import fr.ribesg.alix.api.Source;
import fr.ribesg.alix.api.bot.command.Command;

/**
 * @author bendem
 */
public class LoadCommand extends Command {

    public LoadCommand() {
        super("load", new String[] { "Loads a saved game - Usage ## <save-id>" });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exec(Server server, Channel channel, Source user, String primaryArgument, String[] args) {
        return false;
    }
}
