package ca.unmined.util;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Xml {
    public static String GET(String url) {
        try {
            return request(url);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String request(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).data("query", "Java").userAgent("Mozilla").get();
        Element e = doc.getElementById("PlotOfTheLeastSquaresFit").selectFirst("subpod img");
        return e.attr("src");
    }
}
