package en.weatherforecastapp.utilities;

import org.springframework.stereotype.Component;


@Component
public class WeatherUriTemplate {
    public static final String CITY_INF = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";
    //public static final String CURR_WEATHER_INF = "https://api.weatherbit.io/v2.0/current?lat=%s&lon=%s&key=%s";
    public static final String CURR_WEATHER_INF
        = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,surface_pressure,visibility,wind_speed_10m,uv_index&wind_speed_unit=ms&timeformat=unixtime&forecast_days=1";
    public static final String HOURLY_WEATHER_INF
        = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,relative_humidity_2m,apparent_temperature,weather_code,surface_pressure,visibility,wind_speed_10m,uv_index&wind_speed_unit=ms&timeformat=unixtime&forecast_days=1";
    public static final String DAILY_WEATHER_INF
        = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=weather_code,temperature_2m_max,apparent_temperature_max,uv_index_max,wind_speed_10m_max&timeformat=unixtime";
}
