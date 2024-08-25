package en.weatherforecastapp.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import en.weatherforecastapp.models.City;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Optional;


@Component
@AllArgsConstructor
public class WeatherApiClient {
    public static final Map<Integer, String> weatherDesc = Map.ofEntries(
        Map.entry(0, "Clear sky"),
        Map.entry(1, "Mainly clear"), Map.entry(2, "Partly cloudy"), Map.entry(3, "Overcast"),
        Map.entry(45, "Fog"), Map.entry(48, "Depositing rime fog"),
        Map.entry(51, "Drizzle: light intensity"), Map.entry(53, "Drizzle: moderate intensity"), Map.entry(55, "Drizzle: dense intensity"),
        Map.entry(56, "Freezing Drizzle: light intensity"), Map.entry(57, "Freezing Drizzle: dense intensity"),
        Map.entry(61, "Rain: slight intensity"), Map.entry(63, "Rain: moderate intensity"), Map.entry(65, "Rain: heavy intensity"),
        Map.entry(66, "Freezing Rain: light intensity"), Map.entry(67, "Freezing Rain: heavy intensity"),
        Map.entry(71, "Snow fall: slight intensity"), Map.entry(73, "Snow fall: moderate intensity"), Map.entry(75, "Snow fall: heavy intensity"),
        Map.entry(77, "Snow grains"),
        Map.entry(80, "Rain showers: slight"), Map.entry(81, "Rain showers: moderate"), Map.entry(82, "Rain showers: violent"),
        Map.entry(85, "Snow showers: slight"), Map.entry(86, "Snow showers: heavy"),
        Map.entry(95, "Thunderstorm: slight or moderate"),
        Map.entry(96, "Thunderstorm with slight hail"), Map.entry(99, "Thunderstorm with heavy hail")
    );

    private final RestClient restClient = RestClient.create();
    private ApiKey apiKey;


    public JsonNode getWeatherInfByCityNameAndForecastType(final String cityName
            , final ForecastType ft, Optional<City> city) throws JsonProcessingException, NotFoundException {
        final ObjectMapper ObjMapper = new ObjectMapper();
        final String lat, lon;
        if (city.isPresent()) {
            lat = city.get().getLatitude().toString();
            lon = city.get().getLongitude().toString();
        } else {
            JsonNode cityInf = getCityInf(cityName, ObjMapper);
            lat = cityInf.get("lat").asText();
            lon = cityInf.get("lon").asText();
        }

        final String uri, res;
        switch (ft) {
            case Current -> {
                uri = String.format(WeatherUriTemplate.CURR_WEATHER_INF, lat, lon);
                res = restClient.get().uri(uri).retrieve().body(String.class);

                return ObjMapper.readTree(res).get("current");
            }
            case Hourly -> {
                uri = String.format(WeatherUriTemplate.HOURLY_WEATHER_INF, lat, lon);
                res = restClient.get().uri(uri).retrieve().body(String.class);

                return ObjMapper.readTree(res).get("hourly");
            }
            default -> {
                uri = String.format(WeatherUriTemplate.DAILY_WEATHER_INF, lat, lon);
                res = restClient.get().uri(uri).retrieve().body(String.class);

                return ObjMapper.readTree(res).get("daily");
            }
        }
    }

    public JsonNode getCityInf(final String cityName, final ObjectMapper om)
            throws JsonProcessingException, NotFoundException {
        final String uri = String.format(WeatherUriTemplate.CITY_INF, cityName, "1", apiKey.getOpenWeatherKey());
        final String res = restClient.get().uri(uri).retrieve().body(String.class);

        JsonNode cityInf = om.readTree(res);
        if (cityInf.isEmpty()) {
            throw new NotFoundException("The city doesn't exist");
        } else {
            // This version of the project takes only a single city entry per city name.
            cityInf = cityInf.get(0);
        }

        return cityInf;
    }
}
