package ca.unmined;

import ca.unmined.util.Command;
import ca.unmined.util.Util;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Plugin {

    public static final HashMap<String, String> properties = new HashMap<>();

    public static HashMap<String, Command> COMMANDS = new HashMap<>();
    public static HashMap<String, Command> ALIASES = new HashMap<>();

    public static String b_Prefix = "!";
    public static JDABuilder builder;

    public static void main(String[] args) {
        try {
            builder = JDABuilder.createDefault(getProperty("token", "C:\\Users\\chris\\Documents\\Bananahack\\src\\main\\java\\ca\\unmined\\bot.properties"));
            builder.addEventListeners(new Listener());

            // Register Commands
            Util.RegisterAllCommands();

            for (Command command : COMMANDS.values()) {
                if (command.aliases.length > 0) {
                    for (String a : command.aliases) {
                        ALIASES.put(a, command);
                    }
                }
            }

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
        String commandString = event.getMessage().getContentRaw().toLowerCase().replace("!", "");
        Command command = COMMANDS.get(commandString);

        if (command != null) {
            if (!command.execute(event)) {
                event.getChannel().sendMessage("This command is disabled").queue();
            }
        }
    }

    // endregion

}
