import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
/**
 * @author Alex Bocharov skype bam271074
 *         <p>
 *         Project under hackaton rzdhack.ru 27-29.11.2020
 *         <p>
 *         telegram bot
 */

public class Searcher {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");
        //we use openweathermap.org to get the weather in city
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        //model.setTemp(main.getDouble("temp"));
        //model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        return "City: " + model.getName() + "\n" +
                //"Temperature: " + model.getTemp() + "C" + "\n" +
                //"Humidity:" + model.getHumidity() + "%" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }

    public static String getInfo(String message, Model model) throws IOException {
        //String s = "Инновационная антенная мачта";
        Document doc = Jsoup.connect("http://google.com/search?q=" + message).userAgent("Mozilla").get();
        Elements titles = doc.select("title");

        //print all titles in main page
        for (Element e : titles) {
            //System.out.println("text: " + e.text());
            //System.out.println("html: " + e.html());
            //model.setMain(e.text());
            model.setName(e.text());
        }
        //model.setName(message);
        String buff="";
        //print all available links on page
        Elements links = doc.select("a[href]");
        Integer i=0;
        for (Element l : links) {
            //System.out.println("link: " + l.attr("abs:href"));
            i+=1;
            buff="link: " + l.attr("abs:href")+"\n";
            if (i ==3) {break; }
        }
        model.setMain(buff);
        return model.getName()+"\n"+
                model.getMain();
    }

}


