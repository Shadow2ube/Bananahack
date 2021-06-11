package ca.unmined;

import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Plugin {

    public static final HashMap<String, String> properties = new HashMap<>();
    public static JDABuilder builder;

    public static void main(String[] args) {
        try {
            builder = JDABuilder.createDefault(getProperty("yeet", "C:\\Users\\chris\\Documents\\Bananahack\\src\\main\\java\\ca\\unmined\\bot.properties"));
            builder.addEventListeners(new Listener());
            
            
            builder.build();

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private static void populateProperties(final String file) {
        // TODO: 2021-06-11  
    }
    
    private static String getProperty(final String name, final String file) {
        try {
            if (!properties.isEmpty() && properties.containsKey(name))
                return properties.get(name);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] p = line.split(": ");
                properties.put(p[0], p[1]);
            }

            return properties.get(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
