package ca.unmined.util;


import ca.unmined.Plugin;
import com.google.common.reflect.ClassPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

public class Util {
    public static void RegisterAllCommands() {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getCountryFromJSON(JSONArray countries, String name) {
        for (Object o : countries) {
            JSONObject country = (JSONObject) o;
            if (country.get("location").equals(name) || country.get("country_code").equals(name)) {
                return country;
            }
        }

        return new JSONObject();
    }

    public static JSONArray getTopCasesByCountry(JSONArray countries, int amount) {
        return getTopJsonArray(countries, amount);
    }
    public static JSONArray getLowCasesByCountry(JSONArray countries, int amount) {
        return getLowJsonArray(countries, amount);
    }

    public static JSONArray getTopCasesByState(JSONArray states, String countryCode, int amount) {
        JSONArray p = new JSONArray();
        for (Object state : states) {
            JSONObject o = (JSONObject) state;
            if (o.get("country_code").equals(countryCode)) {
                p.add(o);
            }
        }
        return getTopJsonArray(p, amount);
    }
    public static JSONArray getLowCasesByState(JSONArray states, String countryCode, int amount) {
        JSONArray p = new JSONArray();
        for (Object state : states) {
            JSONObject o = (JSONObject) state;
            if (o.get("country_code").equals(countryCode)) {
                p.add(o);
            }
        }
        return getLowJsonArray(states, amount);
    }

    private static JSONArray getTopJsonArray(JSONArray states, int amount) {
        List<JSONObject> jsonValues = new ArrayList<>();
        for (Object state : states) {
            jsonValues.add((JSONObject) state);
        }
        jsonValues.sort(new Comparator<JSONObject>() {
            private static final String KEY_NAME = "confirmed";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                Long va;
                Long vb;

                va = (Long) a.get(KEY_NAME);
                vb = (Long) b.get(KEY_NAME);

                return -va.compareTo(vb);
            }
        });

        JSONArray out = new JSONArray();
        if (amount == -1) {
            for (int i = 0; i < states.size(); i++) {
                out.add(jsonValues.get(i));
            }
        } else if (amount > 0) {
            for (int i = 0; i < states.size() && i <= amount; i++) {
                out.add(jsonValues.get(i));
            }
        }

        return out;
    }
    private static JSONArray getLowJsonArray(JSONArray states, int amount) {
        List<JSONObject> jsonValues = new ArrayList<>();
        for (Object state : states) {
            jsonValues.add((JSONObject) state);
        }

        jsonValues.sort(new Comparator<JSONObject>() {
            private static final String KEY_NAME = "confirmed";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                Long va;
                Long vb;

                va = (Long) a.get(KEY_NAME);
                vb = (Long) b.get(KEY_NAME);

                return va.compareTo(vb);
            }
        });

        JSONArray out = new JSONArray();
        for (int i = 0; i < states.size() && i <= amount; i++) {
            out.add(jsonValues.get(i));
        }

        return out;
    }

    public static String graph(TreeMap<Long, Long> in) {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<Long, Long> entry : in.entrySet()) {
            b.append("%7b")
                    .append(entry.getKey())
                    .append(",%20")
                    .append(entry.getValue())
                    .append("%7d");
            if (!entry.getKey().equals(in.lastEntry().getKey())) b.append(',').append("%20");
        }

        return b.toString();
    }
}
