package ca.unmined.util;


import ca.unmined.Plugin;
import com.google.common.reflect.ClassPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Util {
    public static void RegisterAllCommands() throws IOException {
        ClassPath cp = ClassPath.from(Util.class.getClassLoader());
        cp.getTopLevelClassesRecursive("ca.unmined.commands").forEach(info -> {
            try {
                Class<?> c = Class.forName(info.getName());

                Object o = c.newInstance();
                if (o instanceof Command) {
                    Command command = (Command) o;
                    Plugin.COMMANDS.put(command.name.toLowerCase(), command);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }

    public static JSONObject getCountryFromJSON(JSONArray countries, String name) {
        System.out.println("A");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println("B");
            JSONObject country = (JSONObject) countries.get(i);
            System.out.println("C");
            if (country.get("location") == name || country.get("country_code") == name) {
                System.out.println("D");
                return country;
            }
        }

        return new JSONObject();
    }
}
