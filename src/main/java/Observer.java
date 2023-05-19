import java.util.HashMap;

public interface Observer {
    void  update(HashMap<String, Float> weatherData);
}
