package ca.unmined.commands;

import ca.unmined.util.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping extends Command {

    public Ping() {
        this.name = "Ping";
        this.aliases = new String[0];
        this.permissions = new Permission[0];
        this.description = "This says pong - duh";

    }

    @Override
    public boolean execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong").queue();
        return true;
    }
}
