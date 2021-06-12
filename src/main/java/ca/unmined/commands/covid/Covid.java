package ca.unmined.commands.covid;

import ca.unmined.util.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.interactions.components.Component;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.time.Instant;

public class Covid extends Command implements Component {


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

            if(args[1].equalsIgnoreCase("top")
                    || args[1].equalsIgnoreCase("t")){
                getTopCases(event);
            }
            if(args[1].equalsIgnoreCase("low")
                    || args[1].equalsIgnoreCase("l")) {
                getLowCases(event);
            }
        }

        return true;
    }


    private void getTopCases(MessageReceivedEvent event) {
        EmbedBuilder embedHighCases = new EmbedBuilder();

        embedHighCases.setTitle("COVID-19 Cases Leaderboard");
        embedHighCases.setDescription("Leaderboards for the countries with the most covid cases");
        embedHighCases.addField("1. " + "Country1", "", false);
        embedHighCases.addField("2. " + "Country2", "", false);
        embedHighCases.addField("3. " + "Country3", "", false);
        embedHighCases.setTimestamp(Instant.now());
        embedHighCases.setFooter("Bot made by Justin and Christian");

        Message e = event.getChannel().sendMessage(embedHighCases.build()).setActionRow(Button.primary("GraphHighCase", "Graph")).complete();
    }

    private void getLowCases(MessageReceivedEvent event) {
        EmbedBuilder embedLowCases = new EmbedBuilder();

        embedLowCases.setTitle("COVID-19 Cases Leaderboard");
        embedLowCases.setDescription("Leaderboards for the countries with the least covid cases");
        embedLowCases.addField("1. " + "Country1", "", false);
        embedLowCases.addField("2. " + "Country2", "", false);
        embedLowCases.addField("3. " + "Country3", "", false);
        embedLowCases.setTimestamp(Instant.now());
        embedLowCases.setFooter("Bot made by Justin and Christian");

        event.getChannel().sendMessage(embedLowCases.build()).queue();
    }


    @NotNull
    @Override
    public Type getType() {
        return null;
    }

    @Nullable
    @Override
    public String getId() {
        return null;
    }

    @NotNull
    @Override
    public DataObject toData() {
        return null;
    }
}
