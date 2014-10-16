package be.bendem.bot.commands;

import fr.ribesg.alix.api.Channel;
import fr.ribesg.alix.api.Server;
import fr.ribesg.alix.api.Source;
import fr.ribesg.alix.api.bot.command.Command;

/**
 * @author bendem
 */
public class SaveCommand extends Command {

    public SaveCommand() {
        super("save", new String[] {
            "Saves the current state of the game and gives the save-id needed to restart it"
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exec(Server server, Channel channel, Source user, String primaryArgument, String[] args) {
        return false;
    }
}
