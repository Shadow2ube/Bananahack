package ca.unmined.commands;

import ca.unmined.Plugin;
import ca.unmined.util.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Ping extends Command {

    public Ping() {
        this.name = "Ping";
        this.aliases = new String[] {
                "p",
                "pi"
        };
        this.permissions = new Permission[0];
        this.description = "This says pong - duh";

    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage("Pong.").queue();

        System.out.println(Plugin.countryStats);
        return true;
    }
}
