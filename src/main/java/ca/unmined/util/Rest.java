package ca.unmined.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

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
