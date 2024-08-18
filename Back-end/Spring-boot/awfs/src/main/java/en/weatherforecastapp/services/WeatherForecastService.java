package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import en.weatherforecastapp.models.dto.WeatherForecastDTO;

import java.util.Optional;


public interface WeatherForecastService {
    Optional<WeatherForecastDTO> getWeatherForecast(final String city) throws JsonProcessingException;
}
