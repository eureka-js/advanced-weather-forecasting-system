package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import en.weatherforecastapp.models.City;
import en.weatherforecastapp.models.dto.FavoriteCityDTO;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;


public interface CityService {
    boolean addFavoriteCity(FavoriteCityDTO newFavCityDTO) throws JsonProcessingException, NotFoundException;
    Optional<City> getCityByName(final String name) throws NotFoundException, JsonProcessingException;
    Optional<City> findFirstByName(final String cityName);
    List<String> findCityNameByUserId(final Long userId);
}
