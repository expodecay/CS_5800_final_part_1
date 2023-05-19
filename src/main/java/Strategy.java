import org.json.simple.JSONObject;

public abstract class Strategy {
    protected Object weatherAPI, weatherGOV, openMeteo;

    public void addJsonObjectWeatherAPI(Object newObject){
        weatherAPI = newObject;
    }
    public void addJsonObjectWeatherGOV(Object newObject){
        weatherGOV = newObject;
    }
    public void addJsonObjectOpenMeteo(Object newObject){
        openMeteo = newObject;
    }

    public abstract float getHumidity();
    public abstract float getTemperature();
    public abstract float getPressure();
}
