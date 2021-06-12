package ca.unmined;

import ca.unmined.util.Command;
import ca.unmined.util.Rest;
import ca.unmined.util.Task;
import ca.unmined.util.Util;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Plugin {

    public static final HashMap<String, String> properties = new HashMap<>();

    public static HashMap<String, Command> COMMANDS = new HashMap<>();
    public static HashMap<String, Command> ALIASES = new HashMap<>();

    public static Timer timer = new Timer();
    public static JSONObject countryStats;
    public static JSONObject provinceStats;
    public static String b_Prefix = "!";
    public static JDABuilder builder;

    public static void main(String[] args) {
        try {
            builder = JDABuilder.createDefault(getProperty("token", "bot.properties"));
            builder.addEventListeners(new Listener());

            Util.RegisterAllCommands();

            // Get command aliases
            for (Command command : COMMANDS.values()) {
                if (command.aliases.length > 0) {
                    for (String a : command.aliases) {
                        ALIASES.put(a, command);
                    }
                }
            }

            TimerTask updateStats = new Task(() -> countryStats = Rest.GET("https://www.trackcorona.live/api/countries"));
            timer.schedule(updateStats, 0, 3600000);

            builder.build();

        } catch (LoginException | IOException e) {
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
