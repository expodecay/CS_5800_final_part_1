public class Main {
    public static void main(String[] args) {
        Strategy prefferedWeatherData = new PrefferedWeatherAPI();

        WeatherGrabber weatherGrabber = new WeatherGrabber(prefferedWeatherData);

        // user registers temp humidity pressure
        WeatherObserver observer1 = new WeatherObserver(weatherGrabber);

        weatherGrabber.register(observer1, "temp");

        WeatherObserver observer2 = new WeatherObserver(weatherGrabber);

        weatherGrabber.register(observer2, "pres");

        weatherGrabber.changeAPIStrategy(new OnlyMeteoApi());

        weatherGrabber.getWeather();

        weatherGrabber.unregister(observer1, "temp");

        weatherGrabber.getWeather();
    }
}
