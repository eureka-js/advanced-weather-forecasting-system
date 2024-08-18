package en.weatherforecastapp.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
@AllArgsConstructor
public class WeatherApiClient {
    private final RestClient restClient = RestClient.create();
    private ApiKey apiKey;

    public JsonNode getWeatherInfByCity(final String city) throws JsonProcessingException {
        String uri, response;
        ObjectMapper objectMapper = new ObjectMapper();

        uri = String.format(WeatherUriTemplate.CITY_INF, city, "1", apiKey.getOpenWeatherKey());
        response = restClient.get().uri(uri).retrieve().body(String.class);
        JsonNode cityInf = objectMapper.readTree(response).get(0);

        uri = String.format(WeatherUriTemplate.CITY_WEATHER_INF, cityInf.get("lat"), cityInf.get("lon"), apiKey.getWeatherbitKey());
        response = restClient.get().uri(uri).retrieve().body(String.class);

        return objectMapper.readTree(response).get("data").get(0);
    }
}
