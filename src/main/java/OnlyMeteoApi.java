import org.json.simple.JSONObject;

public class OnlyMeteoApi extends Strategy{
    @Override
    public float getHumidity() {
        float humidity = Float.NaN;
        if(openMeteo!=null)
            try {
                humidity = Float.parseFloat(((JSONObject) this.openMeteo).get("relativehumidity_2m").toString());
            }catch (NullPointerException ex){
                System.out.println("Unable to retrieve humidity data. Falling back to WeatherAPI.");
                humidity = Float.parseFloat(((JSONObject)this.weatherAPI).get("humidity").toString());
            }
        else
            System.out.println("Humidity not available.");

        return humidity;
    }

    @Override
    public float getTemperature() {

        float temp = Float.NaN;
       if(openMeteo!=null)
           try {
               temp = Float.parseFloat(((JSONObject) this.openMeteo).get("temperature").toString());
           }catch (NullPointerException ex){
               System.out.println("Unable to retrieve temperature data. Falling back to WeatherAPI.");
               temp = Float.parseFloat(((JSONObject)this.weatherAPI).get("temp").toString());
           }
        else
            System.out.println("Temp not available.");

        return temp;
    }

    @Override
    public float getPressure() {
        float pressure = Float.NaN;
        if(openMeteo!=null)
            try {
                pressure = Float.parseFloat(((JSONObject) this.openMeteo).get("pressure").toString());
            }catch(NullPointerException ex){
                System.out.println("Unable to retrieve pressure data. Falling back to WeatherAPI.");
                pressure = Float.parseFloat(((JSONObject)this.weatherAPI).get("pressure").toString());
            }
        else
            System.out.println("Pressure not available.");

        return pressure;
    }
}
