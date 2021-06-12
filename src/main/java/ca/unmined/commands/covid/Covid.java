package ca.unmined.commands.covid;

import ca.unmined.util.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.Instant;
import java.util.Arrays;

public class Covid extends Command {

    public Covid() {
        this.name = "covid";
        this.aliases = new String[] {
                "c",
                "i"
        };
        this.permissions = new Permission[0];
        this.description = "Get COVID-19 data from different countries";
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        if(args.length == 2 && args[0].equalsIgnoreCase("list")) {

            if(args[1].equalsIgnoreCase("top") || args[1].equalsIgnoreCase("t")){
                getTopCases(event);
            }
            if(args[1].equalsIgnoreCase("low") || args[1].equalsIgnoreCase("l")){
                getLowCases(event);
            }
        }

        return true;
    }


    private void getTopCases(MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("COVID-19 Cases Leaderboard");

        embed.setDescription("Leaderboards for the countries with the most covid cases");

        embed.addField("1. " + "Country1", "", false);
        embed.addField("2. " + "Country2", "", false);
        embed.addField("3. " + "Country3", "", false);
        embed.setTimestamp(Instant.now());
        embed.setFooter("Bot made by Justin and Christian");

        event.getChannel().sendMessage(embed.build()).queue();

    }

    private void getLowCases(MessageReceivedEvent event) {
    }



}
