package ca.unmined.commands.covid;

import ca.unmined.Plugin;
import ca.unmined.util.Command;
import ca.unmined.util.Util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        switch (graphState) {
            case 0:
                mode = "Graph";
                break;
            case 1:
                mode = "List";
                break;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("top")
                    || args[0].equalsIgnoreCase("t")){
                getTopCases(event);
            } else if(args[0].equalsIgnoreCase("low")
                    || args[0].equalsIgnoreCase("l")) {
                getLowCases(event);
            } else if (getCountryCases(event)) {

            }
        }

        return true;
    }

    private void getTopCases(MessageReceivedEvent event) {
        JSONArray a = Util.getTopCasesByCountry(Plugin.countryStats, 3);
        sendCases(event, "Leaderboards for the countries with the top covid cases", (JSONObject) a.get(0), (JSONObject) a.get(1), (JSONObject) a.get(2));
    }

    private void getLowCases(MessageReceivedEvent event) {
        JSONArray a = Util.getLowCasesByCountry(Plugin.countryStats, 3);
        sendCases(event, "Leaderboards for the countries with the lowest covid cases", (JSONObject) a.get(0), (JSONObject) a.get(1), (JSONObject) a.get(2));
    }

    private boolean getCountryCases(MessageReceivedEvent event) {
        return false;
    }

    private void sendCases(MessageReceivedEvent event, String description, JSONObject... countries) {
        EmbedBuilder embedHighCases = new EmbedBuilder();

        embedHighCases.setColor(Color.RED);
        embedHighCases.setTitle("COVID-19 Cases Leaderboard");
        embedHighCases.setDescription(description);
        for (int i = 0; i < countries.length; i++) {
            embedHighCases.addField((i + 1) + ". " + countries[i].get("location"), "cases: " + countries[i].get("confirmed"), false);
        }

        embedHighCases.setTimestamp(Instant.now());
        embedHighCases.setFooter("Bot made by Justin and Christian");

        Message e = event.getChannel().sendMessage(embedHighCases.build()).setActionRow(
                Button.primary("7Day", "7 Day View"), Button.primary("30Day", "30 Day View"), Button.primary("1Year", "Year View"), Switch
                ).complete();
    }
}
