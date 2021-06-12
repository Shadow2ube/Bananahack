package ca.unmined.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class Rest {

    public static JSONObject GET(String location) {
        try {
            URL url = new URL(location);
            return request(url, "GET");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    static HashSet<Integer> count = new HashSet<>();
    public static TreeMap<Long, Long> CSV(String link) {
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            TreeMap<Long, Long> out = new TreeMap<>();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Date")) continue;
                String[] split = line.split(",");
                int p = Integer.parseInt(split[0].split("-")[1]);
                if (!count.contains(p) && !count.contains(p + 1) && !count.contains(p + 2)) {
                    out.put(new SimpleDateFormat("yyyy-MM-dd").parse(split[0]).getTime(), Long.parseLong(split[1]));
                    count.add(p);
                    count.add(p + 1);
                    count.add(p + 2);
                }
            }
            return out;
        } catch (IOException | java.text.ParseException e) {
            e.printStackTrace();
        }

        return new TreeMap<>();
    }

    public static JSONObject request(URL url, String method) throws IOException, ParseException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder b = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            b.append(line);
        }
        return (JSONObject) new JSONParser().parse(b.toString());
    }

}
