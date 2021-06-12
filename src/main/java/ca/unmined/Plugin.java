package ca.unmined;

import ca.unmined.util.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.JSONArray;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Plugin {

    public static final HashMap<String, String> properties = new HashMap<>();

    public static HashMap<String, Command> COMMANDS = new HashMap<>();
    public static HashMap<String, Command> ALIASES = new HashMap<>();

    public static String wolfram;
    public static Timer timer = new Timer();
    public static String b_Prefix = "!";
    public static JDABuilder builder;

    public static JSONArray countryStats = new JSONArray();
    public static JSONArray stateStats = new JSONArray();
    public static String allTimeGraph;
    public static TreeMap<Long, Long> allTimeCases;

    public static void main(String[] args) {
        try {
            builder = JDABuilder.createDefault(getProperty("token", "bot.properties"));
            builder.addEventListeners(new Listener());

            Util.RegisterAllCommands();
            wolfram = "http://api.wolframalpha.com/v2/query?appid=" + properties.get("appId");

            // Get command aliases
            for (Command command : COMMANDS.values()) {
                if (command.aliases.length > 0) {
                    for (String a : command.aliases) {
                        ALIASES.put(a, command);
                    }
                }
            }

            TimerTask updateStats = new Task(() -> {
                countryStats = (JSONArray) Rest.GET("https://www.trackcorona.live/api/countries").get("data");
                stateStats = (JSONArray) Rest.GET("https://www.trackcorona.live/api/provinces").get("data");
                allTimeCases = Rest.CSV("https://raw.githubusercontent.com/datasets/covid-19/main/data/worldwide-aggregate.csv");
                allTimeGraph = Xml.GET((wolfram + "&input=quadratic%20fit%20" + Util.graph(allTimeCases)));
            });
            timer.schedule(updateStats, 0, 7200000); // Index once every 2 hours

            builder.build();

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    // region util

    private static void populateProperties(final String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("= ");
                properties.put(p[0], p[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static String getProperty(final String name, final String file) {
        if (!properties.containsKey(name))
            populateProperties(file);
        return properties.get(name);
    }

    public static void execute(MessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw().toLowerCase().replace("!", "").split(" ");
        String commandString = split[0];
        String[] args = Arrays.copyOfRange(split, 1, split.length);
        Command command = COMMANDS.get(commandString);

        if (command != null) {
            if (!command.execute(event, args)) {
                event.getChannel().sendMessage("This command is disabled").queue();
            }
        } else if ((command = ALIASES.get(commandString)) != null) {
            if (!command.execute(event, args)) {
                event.getChannel().sendMessage("This command is disabled").queue();
            }
        }
    }

    // endregion

}
