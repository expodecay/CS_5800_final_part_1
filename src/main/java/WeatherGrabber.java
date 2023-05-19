import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WeatherGrabber implements Subject{

    private HashMap<Observer, HashMap<String, Float>> observersData;
    private HashMap<String, ArrayList<Observer>> registeredObservers;
    private Object weatherAPI;
    private Object weatherGOV;
    private Object openMeteo;

    private Strategy weatherStrategy;

    public WeatherGrabber(Strategy newWeatherStrategy){
        registeredObservers = new HashMap<>();
        weatherStrategy = newWeatherStrategy;
        observersData = new HashMap<>();
    }

    @Override
    public void register(Observer newObserver, String info) {
        if(!registeredObservers.containsKey(info)){
            ArrayList<Observer> new_observer_list = new ArrayList<>();
            new_observer_list.add(newObserver);
            registeredObservers.put(info, new_observer_list);
        }
        else{
            ArrayList<Observer> list = registeredObservers.get(info);
            list.add(newObserver);
        }

        if(!observersData.containsKey(newObserver)){
            HashMap<String, Float> data = new HashMap<>();
            observersData.put(newObserver, data);
        }
    }

    @Override
    public void unregister(Observer deleteObserver, String info) {
        if(registeredObservers.containsKey(info)){
            ArrayList<Observer> list = registeredObservers.get(info);
            list.remove(deleteObserver);
        }
        else{
            System.out.println("Observer not subscribed to " + info);
        }

        if(observersData.containsKey(deleteObserver)){
            observersData.remove(deleteObserver);
        }
    }

    @Override
    public void notifyObserver() {
        observersData.forEach((observer, data) ->{
            if(!data.isEmpty()) {
                observer.update(data);
            }
        });
    }

    @Override
    public void changeAPIStrategy(Strategy strat) {
        weatherStrategy = strat;
    }

    private Object getJson(String address, String mainSection) {
        Object json_result = null;
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                Object obj  = parser.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(obj);

                JSONObject countryData = (JSONObject) array.get(0);

                json_result = countryData.get(mainSection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json_result;
    }

    private void setWeatherAPI(){
        this.weatherAPI = getJson("https://api.openweathermap.org/data/2.5/weather?q=Riverside&appid=26d98288a752ed83cd8b75a6a07eba14", "main");
    }

    private void setWeatherGOV(){
        this.weatherGOV = getJson("https://api.weather.gov/gridpoints/TOP/31,80/forecast", "properties");
    }
    private void setOpenMeteo(){
        this.openMeteo = getJson("https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m", "current_weather");
    }

    public void getWeather(){
        setWeatherAPI();
        setWeatherGOV();
        setOpenMeteo();

        weatherStrategy.addJsonObjectWeatherAPI(weatherAPI);
        weatherStrategy.addJsonObjectWeatherGOV(weatherGOV);
        weatherStrategy.addJsonObjectOpenMeteo(openMeteo);

        float temp = weatherStrategy.getTemperature();
        float humid = weatherStrategy.getHumidity();
        float pres = weatherStrategy.getPressure();

        for(Map.Entry<String, ArrayList<Observer>> entry: registeredObservers.entrySet()){
            if(entry.getKey().equalsIgnoreCase("temp")){
                for(Observer ob : entry.getValue()){
                    Map<String, Float> data = observersData.get(ob);
                    data.put("temp", temp);
                }
            }
            if(entry.getKey().equalsIgnoreCase("humid")){
                for(Observer ob : entry.getValue()){
                    Map<String, Float> data = observersData.get(ob);
                    data.put("humid", humid);
                }
            }
            if(entry.getKey().equalsIgnoreCase("pres")){
                for(Observer ob : entry.getValue()){
                    Map<String, Float> data = observersData.get(ob);
                    data.put("pres", pres);
                }
            }
            notifyObserver();
        }
    }
}
