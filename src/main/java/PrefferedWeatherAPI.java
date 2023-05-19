import org.json.simple.JSONObject;

public class PrefferedWeatherAPI extends Strategy{

    @Override
    public float getHumidity() {
        float humidity = Float.NaN;

        if(weatherAPI!=null)
            humidity = Float.parseFloat(((JSONObject)this.weatherAPI).get("humidity").toString());
        else if(weatherGOV!=null)
            humidity = Float.parseFloat(((JSONObject)this.weatherGOV).get("humidity").toString());
        else if(openMeteo!=null)
            humidity = Float.parseFloat(((JSONObject)this.openMeteo).get("humidity").toString());
        else
            System.out.println("Humidity not available.");

        return humidity;
    }

    @Override
    public float getTemperature() {

        float temp = Float.NaN;

        if(weatherAPI!=null)
            temp = Float.parseFloat(((JSONObject)this.weatherAPI).get("temp").toString());
        else if(weatherGOV!=null)
            temp = Float.parseFloat(((JSONObject)this.weatherGOV).get("temp").toString());
        else if(openMeteo!=null)
            temp = Float.parseFloat(((JSONObject)this.openMeteo).get("temp").toString());
        else
            System.out.println("Temp not available.");

        return temp;
    }

    @Override
    public float getPressure() {
        float pressure = Float.NaN;

        if(weatherAPI!=null)
            pressure = Float.parseFloat(((JSONObject)this.weatherAPI).get("pressure").toString());
        else if(weatherGOV!=null)
            pressure = Float.parseFloat(((JSONObject)this.weatherGOV).get("pressure").toString());
        else if(openMeteo!=null)
            pressure = Float.parseFloat(((JSONObject)this.openMeteo).get("pressure").toString());
        else
            System.out.println("Pressure not available.");

        return pressure;
    }
}
