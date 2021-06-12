package ca.unmined.commands;

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
import java.util.HashMap;

public class Covid extends Command {

    public static HashMap<Long, Long> sentByWho = new HashMap<>();


    public static String mode = "Graph";
    public static int graphState = 0;

    public Covid() {
        this.name = "covid";
        this.aliases = new String[] {
                "c",
                "i"
        };
        this.children = new String[][] {
                {"top/t", "COVID Countries with highest cases"},
                {"low/l", "COVID Countries with lowest cases"},
                {"top/t {country_code}", "COVID States/Provinces with highest cases"},
//                {"low/l {country_code}", "COVID States/Provinces with lowest cases"}
        };
        this.permissions = new Permission[0];
        this.description = "Get COVID-19 data from different countries";
    }

    @Override
    public boolean execute(MessageReceivedEvent event, String[] args) {
        switch (graphState) {
            case 0:
                mode = "International Graph";
                break;
            case 1:
                mode = "List";
                break;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("top")
                    || args[0].equalsIgnoreCase("t")) {
                getTopCases(event);
            } else if (args[0].equalsIgnoreCase("low")
                    || args[0].equalsIgnoreCase("l")) {
                getLowCases(event);
            }
        } else if (args.length == 2) {
            if ((args[0].equalsIgnoreCase("top") || args[0].equalsIgnoreCase("t"))
                    && !getCountryTopCases(event, args[1])) {
                event.getChannel().sendMessage("Invalid country").queue();
            } else if ((args[0].equalsIgnoreCase("low") || args[0].equalsIgnoreCase("l"))
                    && !getCountryTopCases(event, args[1])) {
                event.getChannel().sendMessage("Invalid country").queue();
            }
        }

        return true;
    }

    private void getTopCases(MessageReceivedEvent event) {
        JSONArray a = Util.getTopCasesByCountry(Plugin.countryStats, 3);
        sendCases(event, "Leaderboard for the countries with the top covid cases", (JSONObject) a.get(0), (JSONObject) a.get(1), (JSONObject) a.get(2));
    }

    private void getLowCases(MessageReceivedEvent event) {
        JSONArray a = Util.getLowCasesByCountry(Plugin.countryStats, 3);
        sendCases(event, "Leaderboard for the countries with the lowest covid cases", (JSONObject) a.get(0), (JSONObject) a.get(1), (JSONObject) a.get(2));
    }

    private boolean getCountryTopCases(MessageReceivedEvent event, String countryCode) {
        JSONArray a = Util.getTopCasesByState(Plugin.stateStats, countryCode, -1);
        JSONObject c = Util.getCountryFromJSON(Plugin.countryStats, countryCode);
        System.out.println(a);
        if (a.size() == 0)
            return false;
        sendCases(event, c, "Leaderboard for the top Provinces/States with the top covid cases", a.toArray(new Object[0]));
        return true;
    }

    private void sendCases(MessageReceivedEvent event, String description, JSONObject... countries) {

        EmbedBuilder embedHighCases = new EmbedBuilder();

        embedHighCases.setColor(Color.RED);
        embedHighCases.setTitle("COVID-19 Cases Leaderboard");
        embedHighCases.setDescription(description);
        for (int i = 0; i < countries.length; i++) {
            embedHighCases.addField((i + 1) + ". " + countries[i].get("location"), "Cases: " + countries[i].get("confirmed"), false);
        }

        embedHighCases.setTimestamp(Instant.now());
        embedHighCases.setFooter("Command Executed By: " + event.getAuthor().getIdLong());

        Message e = event.getChannel().sendMessage(embedHighCases.build()).setActionRow(
                Button.primary("Switch", mode)
                ).complete();

        sentByWho.put(event.getAuthor().getIdLong(), e.getIdLong());
    }
    private void sendCases(MessageReceivedEvent event, JSONObject country, String description, Object... countries) {
        EmbedBuilder embedHighCases = new EmbedBuilder();

        embedHighCases.setColor(Color.RED);
        embedHighCases.setTitle(country.get("location") + "'s COVID-19 Case Leaderboard");
        embedHighCases.setDescription(description + "\n" + country.get("location") + "'s Total Case Count is: " + country.get("confirmed"));
        for (int i = 0; i < countries.length; i++) {
            embedHighCases.addField((i + 1) + ". " + ((JSONObject) countries[i]).get("location"), "Cases: " + ((JSONObject) countries[i]).get("confirmed"), false);
        }

        embedHighCases.setTimestamp(Instant.now());
        embedHighCases.setFooter("Command Executed By: " + event.getAuthor().getIdLong());

        Message e = event.getChannel().sendMessage(embedHighCases.build()).complete();

        sentByWho.put(event.getAuthor().getIdLong(), e.getIdLong());
    }
}
