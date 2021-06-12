package ca.unmined.util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

    public String name;
    public String[] aliases;
    public Permission[] permissions;
    public String description;

    public Command() { }

    public abstract boolean execute(MessageReceivedEvent event);

}
