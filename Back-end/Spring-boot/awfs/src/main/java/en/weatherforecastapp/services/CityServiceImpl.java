package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import en.weatherforecastapp.models.City;
import en.weatherforecastapp.models.FavoriteCity;
import en.weatherforecastapp.models.User;
import en.weatherforecastapp.models.dto.FavoriteCityDTO;
import en.weatherforecastapp.repositories.CityJpaRepository;
import en.weatherforecastapp.repositories.FavoriteCityJpaRepository;
import en.weatherforecastapp.utilities.WeatherApiClient;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityJpaRepository cityJpaRepository;
    private final FavoriteCityJpaRepository favCityJpaRepository;
    private final UserService userService;
    private final WeatherApiClient weatherApiClient;


    @Override
    public boolean addFavoriteCity(final FavoriteCityDTO newFavCityDTO) throws JsonProcessingException, NotFoundException {
        Optional<User> user = userService.findByUsername(newFavCityDTO.getUsername());
        if (user.isEmpty()) {
            user = userService.save(newFavCityDTO.getUsername());

            if (user.isEmpty()) {
                throw new RuntimeException();
            }
        }

        Optional<City> newFavCity = cityJpaRepository.findCityByName(newFavCityDTO.getCityName());
        if (newFavCity.isEmpty()) {
            newFavCity = getCityByName(newFavCityDTO.getCityName());
            if (newFavCity.isEmpty()) {
                throw new NotFoundException("The city doesn't exist");
            }
        }

        if (favCityJpaRepository.existsByUser_IdAndCity_Id(user.get().getId(), newFavCity.get().getId())) {
            return false;
        }

        favCityJpaRepository.save(new FavoriteCity(null, user.get(), newFavCity.get()));

        return true;
    }

    @Override
    public Optional<City> getCityByName(final String cityName) throws NotFoundException, JsonProcessingException {
        final JsonNode cityInf = weatherApiClient.getCityInf(cityName, new ObjectMapper());
        final String cityNameFromWebService = cityInf.get("name").asText();

        return Optional.of(cityJpaRepository.findCityByName(cityNameFromWebService)
            .orElseGet(() -> cityJpaRepository.save(new City(null, cityNameFromWebService
                , cityInf.get("lat").floatValue(), cityInf.get("lon").floatValue()))));
    }

    @Override
    public Optional<City> findFirstByName(final String cityName) {
        return cityJpaRepository.findFirstByName(cityName);
    }

    @Override
    public List<String> findCityNameByUserId(final Long userId) {
        return favCityJpaRepository.findCity_NameByUser_Id(userId);
    }

    @Override
    public List<FavoriteCityDTO> getFavoriteCities(final String username) {
        return favCityJpaRepository.findAllByUser_Username(username).stream().map(this::mapFavCityToFavCityDTO).toList();
    }

    @Override
    public boolean removeFavoriteCity(final Long id) {
        favCityJpaRepository.deleteById(id);

        return true;
    }


    public FavoriteCityDTO mapFavCityToFavCityDTO(final FavoriteCity fc) {
        return new FavoriteCityDTO(fc);
    }
}