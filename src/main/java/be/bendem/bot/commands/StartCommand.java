package be.bendem.bot.commands;

import fr.ribesg.alix.api.Channel;
import fr.ribesg.alix.api.Server;
import fr.ribesg.alix.api.Source;
import fr.ribesg.alix.api.bot.command.Command;

/**
 * @author bendem
 */
public class StartCommand extends Command {

    /**
     * {@inheritDoc}
     */
    public StartCommand() {
        super("start", new String[] { "Starts a new game." });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exec(Server server, Channel channel, Source user, String primaryArgument, String[] args) {
        // TODO
        return false;
    }
}
