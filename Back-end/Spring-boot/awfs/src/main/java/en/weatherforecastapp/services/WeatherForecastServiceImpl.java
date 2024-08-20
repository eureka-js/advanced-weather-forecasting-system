package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.repositories.WeatherForecastJpaRepository;
import en.weatherforecastapp.utilities.WeatherApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService{
    private static final Integer M_IN_KM = 1000;

    private WeatherForecastJpaRepository wfJpaRepository;
    private WeatherApiClient weatherApiClient;


    @Override
    public Optional<WeatherForecastDTO> getWeatherForecast(final String city) throws JsonProcessingException {
        JsonNode cityWeatherInf = weatherApiClient.getWeatherInfByCity(city);
        WeatherForecast newWForecast = new WeatherForecast(
            null
            , cityWeatherInf.get("city_name").asText()
            , cityWeatherInf.get("temp").floatValue()
            , cityWeatherInf.get("weather").get("description").asText()
            , Timestamp.from(Instant.ofEpochSecond(cityWeatherInf.get("ts").asLong()))
            , cityWeatherInf.get("uv").intValue()
            , cityWeatherInf.get("vis").intValue() * M_IN_KM
        );

        if (!wfJpaRepository.existsByCityAndDateTime(newWForecast.getCity(), newWForecast.getDateTime())) {
            wfJpaRepository.save(newWForecast);
        }

        return Optional.of(mapToWeatherForecastDTO(newWForecast));
    }

    private WeatherForecastDTO mapToWeatherForecastDTO(WeatherForecast wf) {
        return new WeatherForecastDTO(wf.getCity(), wf.getTemperature(), wf.getDescription()
                , wf.getDateTime(), wf.getUVIndex(), wf.getVisibility());
    }
}
