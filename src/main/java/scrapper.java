import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class scrapper {
    public static void main(String[] args) throws Exception {
        scrapper s = new scrapper();
        s.initialize();
    }

    public void initialize() throws Exception {
        final Document doc = Jsoup.connect("https://www.airport-jfk.com/arrivals.php").get();
        Elements part1 = doc.select("#flight_detail_junt");
        Elements part2 = doc.select("#flight_detail_junt2");
        int elementsCount = part1.size();
        Flight[] resultArray = new Flight[elementsCount - 1];
        int index = 0;
        for (Element e: part1) {
            if(e == part1.get(0)) continue;
            resultArray[index] = new Flight();
            resultArray[index].setOrigin(e.child(0).text());
            resultArray[index].setAirline(e.child(1).text());
            resultArray[index].setFlight(e.child(2).text());
            index++;
        }

        index = 0;
        for (Element e: part2) {
            if(e == part2.get(0)) continue;
            resultArray[index].setArrival(e.child(0).text());
            resultArray[index].setTerminal(e.child(2).text());
            resultArray[index].setStatus(e.child(4).text());
            index++;
        }
        Gson converter = new GsonBuilder().create();
        String JSONOutput = converter.toJson(resultArray);
        System.out.print(JSONOutput);
    }


    public class Flight{

        String origin;
        String airline;
        String flight;
        String arrival;
        String terminal;
        String status;


        public Flight(){
            this.origin = "";
            this.airline = "";
            this.flight = "";
            this.arrival = "";
            this.terminal = "";
            this.status = "";
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public void setAirline(String airline) {
            this.airline = airline;
        }

        public void setFlight(String flight) {
            this.flight = flight;
        }

        public void setArrival(String arrival) {
            this.arrival = arrival;
        }

        public void setTerminal(String terminal) {
            this.terminal = terminal;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}