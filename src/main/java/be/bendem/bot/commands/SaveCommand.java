package be.bendem.bot.commands;

import be.bendem.bot.commands.handling.BaseCommand;
import org.kitteh.irc.client.library.element.Channel;
import org.kitteh.irc.client.library.element.User;

import java.util.List;

/**
 * @author bendem
 */
public class SaveCommand extends BaseCommand {

    public SaveCommand() {
        super("save", "game.save", "Saves the current state of the game and gives the save-id needed to restart it");
    }

    @Override
    public void perform(Channel channel, User user, List<String> args) {
    }

}
