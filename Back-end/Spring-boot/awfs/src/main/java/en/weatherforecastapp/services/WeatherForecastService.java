package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import en.weatherforecastapp.models.dto.WeatherForecastCollectionDTO;
import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.utilities.ForecastType;

import java.util.Optional;


public interface WeatherForecastService {
    Optional<WeatherForecastDTO> getCurrWForecastByCity(final String city) throws JsonProcessingException;
    Optional<WeatherForecastCollectionDTO> getWForecastCollByCity(final String city, final ForecastType ft)
            throws JsonProcessingException;
}
