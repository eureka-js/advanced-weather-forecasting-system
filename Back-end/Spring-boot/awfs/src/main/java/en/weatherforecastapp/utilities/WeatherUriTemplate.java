package en.weatherforecastapp.utilities;

import org.springframework.stereotype.Component;


@Component
public class WeatherUriTemplate {
    public static final String CITY_INF = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";
    public static final String CITY_WEATHER_INF = "https://api.weatherbit.io/v2.0/current?lat=%s&lon=%s&key=%s";
}
