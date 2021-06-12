package ca.unmined;

import ca.unmined.util.Command;
import ca.unmined.util.Task;
import ca.unmined.util.Util;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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

            Util.RegisterAllCommands();

            // Get command aliases
            for (Command command : COMMANDS.values()) {
                if (command.aliases.length > 0) {
                    for (String a : command.aliases) {
                        ALIASES.put(a, command);
                    }
                }
            }

            builder.build();

            Timer timer = new Timer();
            TimerTask sleep = new Task(() -> {
                System.out.println("U - U -zzzzz");
                System.out.println("U _ U -zzzzz");
                System.out.println("U - U -zzzzz");
                System.out.println("U _ U -zzzzz");
                System.out.println("O o O -oh");
                System.out.println("- _ - -no");
            });
//            timer.schedule(sleep,);

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
