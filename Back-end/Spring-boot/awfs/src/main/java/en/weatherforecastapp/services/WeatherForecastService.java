package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import en.weatherforecastapp.models.City;
import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.models.dto.WeatherForecastCollectionDTO;
import en.weatherforecastapp.utilities.ForecastType;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;


public interface WeatherForecastService {
    Optional<WeatherForecastCollectionDTO> getWForecastCollByCity(final String cityName, final ForecastType ft
            , BiFunction<City, JsonNode, WeatherForecastCollection> createChosenTypeWForecastColl)
            throws NotFoundException, JsonProcessingException;
    WeatherForecastCollection createCurrWForecastColl(final City city, final JsonNode wForecastInf);
    WeatherForecastCollection createHourlyWForecastColl(final City city, final JsonNode wForecastCollInf);
    WeatherForecastCollection createDailyWForecastColl(final City city, final JsonNode wForecastCollInf);
    List<WeatherForecastCollectionDTO> getFavoriteCityWForecasts(final String username)
            throws NotFoundException, JsonProcessingException;
}
