package ca.unmined.commands;

import ca.unmined.Plugin;
import ca.unmined.util.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class Help extends Command {

    public Help() {
        this.name = "help";
        this.aliases = new String[] {"h"};
        this.permissions = new Permission[0];
        this.description = "Help about commands";
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        EmbedBuilder b = new EmbedBuilder();
        b.setColor(new Color(0, 0, 0xff).getRGB());
        b.setTitle("Help");
        for (Command c : Plugin.COMMANDS.values()) {
            b.addField(c.name, c.description, false);
            if (c.children != null && c.children.length != 0) {
                for (String[] child : c.children) {
                    b.addField(c.name + " " + child[0], child[1], false);
                }
            }
        }
        event.getChannel().sendMessage(b.build()).queue();


        return true;
    }
}
