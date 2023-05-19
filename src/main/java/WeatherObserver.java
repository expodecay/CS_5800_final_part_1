import org.json.simple.JSONObject;

import java.util.HashMap;

public class WeatherObserver implements Observer{
    private static int observerIDTracker = 0;
    private int observerID;

    private Subject weatherGrabber;

    public WeatherObserver(Subject weatherGrabber){
        this.weatherGrabber = weatherGrabber;
        this.observerID = ++ observerIDTracker;

        System.out.println("New Observer " + this.observerID);
    }

    @Override
    public void update(HashMap<String, Float> weatherData) {
        System.out.println("\nObserver: " + observerID + "\nTemperature: " + weatherData.get("temp") +
                "\nHumidity: " + weatherData.get("humid") + "\nPressure: " + weatherData.get("pres"));
    }
}
