package ca.unmined.util;


import ca.unmined.Plugin;
import com.google.common.reflect.ClassPath;

import java.io.IOException;

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
}
