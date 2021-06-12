package ca.unmined.commands;

import ca.unmined.util.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class testcommand extends Command {

    public testcommand() {
        this.name = "test";
        this.aliases = new String[] {"t"};
        this.permissions = new Permission[0];
        this.description = "Embed Test Command";

    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Embed Test");
        embed.setAuthor("Justin");
        event.getChannel().sendMessage(embed.build()).queue();
        return true;
    }

}
