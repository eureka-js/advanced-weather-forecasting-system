package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import en.weatherforecastapp.models.City;
import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.models.dto.WeatherForecastCollectionDTO;
import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.repositories.*;
import en.weatherforecastapp.utilities.ForecastType;
import en.weatherforecastapp.utilities.WeatherApiClient;
import en.weatherforecastapp.utilities.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;


@Service
@AllArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private static final Integer M_IN_KM = 1000;
    //private static final Long WEATHERBIT_CURR_W_F_UPDATE_MAX_INTERVAL_IN_MIN = 30L;
    private static final Long OPEN_METEO_CURR_W_F_UPDATE_MAX_INTERVAL_IN_MIN = 15L;

    private final WeatherForecastJpaRepository wfJpaRepository;
    private final WeatherForecastCollectionJpaRepository wfCollJpaRepository;
    private final UserService userService;
    private final CityService cityService;
    private WeatherApiClient weatherApiClient;


    @Override
    public Optional<WeatherForecastCollectionDTO> getWForecastCollByCity(final String cityName, final ForecastType ft
            , BiFunction<City, JsonNode, WeatherForecastCollection> createChosenTypeWForecastColl)
            throws NotFoundException, JsonProcessingException {
        Optional<City> city = cityService.findFirstByName(cityName);
        if (city.isEmpty()) {
            city = cityService.getCityByName(cityName);

            if (city.isEmpty()) {
                throw new NotFoundException();
            }
        }

        Optional<WeatherForecastCollection> newWForecastColl;
        if (ft == ForecastType.Current) {
            // Get the most recent current weather forecast for a given city,
            //  and then stop referencing it if it's outdated.
            newWForecastColl = wfCollJpaRepository
                .findOneWithYoungestDateTimeByCityNameAndForecastType(city.get().getName(), ft.name());
            if (newWForecastColl.isPresent()) {
                Timestamp upperLimitDateTime = getUpperLimitDateTimeTimestamp(newWForecastColl.get().getDateTime());
                Timestamp currentDateTime = getCurrentDateTimeTimestamp();
                if (currentDateTime.after(upperLimitDateTime)) {
                    newWForecastColl = Optional.empty();
                }
            }
        } else {
            // Get today's non-current weather forecast collection from the db if there exists one.
            final Timestamp currDayTimestamp = getCurrentDateTimeWithFlattedTimeTimestamp();
            newWForecastColl = wfCollJpaRepository
                .findFirstByCity_NameAndDateTimeAndForecastType(city.get().getName(), currDayTimestamp, ft);
        }

        // If new forecast object is empty by this point then fetch new weather information from the web service and
        if (newWForecastColl.isEmpty()) {
            final JsonNode wForecastInf = weatherApiClient
                .getWeatherInfByCityNameAndForecastType(city.get().getName(), ft, city);

            newWForecastColl = Optional.of(createChosenTypeWForecastColl.apply(city.get(), wForecastInf));

            wfCollJpaRepository.save(newWForecastColl.get());
        }

        return Optional.of(mapToWeatherForecastCollectionDTO(newWForecastColl.get()));
    }

    @Override
    public WeatherForecastCollection createCurrWForecastColl(final City city, final JsonNode wForecastInf) {
        final WeatherForecastCollection wfc = new WeatherForecastCollection(
                null
                , Timestamp.from(Instant.ofEpochSecond(wForecastInf.get("time").asLong()))
                , ForecastType.Current
                , city
                , new ArrayList<>()
        );

        wfc.getWeatherForecasts().add(new WeatherForecast(
                null
                , city.getName()
                , wForecastInf.get("temperature_2m").floatValue()
                , WeatherApiClient.weatherDesc.get(wForecastInf.get("weather_code").asInt())
                , Timestamp.from(Instant.ofEpochSecond(wForecastInf.get("time").asLong()))
                , wForecastInf.get("uv_index").intValue()
                , wForecastInf.get("visibility").intValue()
                , wForecastInf.get("relative_humidity_2m").asInt()
                , wForecastInf.get("wind_speed_10m").floatValue()
                , ForecastType.Current
                , wForecastInf.get("apparent_temperature").floatValue()
                , wForecastInf.get("surface_pressure").asInt()
                , wfc
        ));

        return wfc;
    }

    @Override
    public WeatherForecastCollection createHourlyWForecastColl(final City city, final JsonNode wForecastCollInf) {
        final WeatherForecastCollection wfc = new WeatherForecastCollection(
                null
                , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(0).asLong()))
                , ForecastType.Hourly
                , city
                , new ArrayList<>()
        );

        for (int i = 0; i < wForecastCollInf.get("time").size(); ++i) {
            wfc.getWeatherForecasts().add(new WeatherForecast(
                null
                , city.getName()
                , wForecastCollInf.get("temperature_2m").get(i).floatValue()
                , WeatherApiClient.weatherDesc.get(wForecastCollInf.get("weather_code").get(i).asInt())
                , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(i).asLong()))
                , wForecastCollInf.get("uv_index").get(i).intValue()
                , wForecastCollInf.get("visibility").get(i).intValue()
                , wForecastCollInf.get("relative_humidity_2m").get(i).asInt()
                , wForecastCollInf.get("wind_speed_10m").get(i).floatValue()
                , ForecastType.Hourly
                , wForecastCollInf.get("apparent_temperature").get(i).floatValue()
                , wForecastCollInf.get("surface_pressure").get(i).asInt()
                , wfc
            ));
        }

        return wfc;
    }

    @Override
    public WeatherForecastCollection createDailyWForecastColl(final City city, final JsonNode wForecastCollInf) {
        final WeatherForecastCollection wfc = new WeatherForecastCollection(
                null
                , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(0).asLong()))
                , ForecastType.Daily
                , city
                , new ArrayList<>()
        );

        for (int i = 0; i < wForecastCollInf.get("time").size(); ++i) {
            // Couldn't find any free weather forecast web services that offer the values that I need for daily forecasts
            //  by this point in time; so I have taken max values, or I have hardcoded zeroes (Date: 2024-08-24)
            wfc.getWeatherForecasts().add(new WeatherForecast(
                null
                , city.getName()
                , wForecastCollInf.get("temperature_2m_max").get(i).floatValue() // Placeholder value
                , WeatherApiClient.weatherDesc.get(wForecastCollInf.get("weather_code").get(i).asInt())
                , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(i).asLong()))
                , wForecastCollInf.get("uv_index_max").get(i).intValue() // Placeholder value
                , 0 // Placeholder value
                , 0 // Placeholder value
                , wForecastCollInf.get("wind_speed_10m_max").get(i).floatValue() // Placeholder value
                , ForecastType.Daily
                , wForecastCollInf.get("apparent_temperature_max").get(i).floatValue() // Placeholder value
                , 0 // Placeholder value
                , wfc
            ));
        }

        return wfc;
    }

    @Override
    public List<WeatherForecastCollectionDTO> getFavoriteCityWForecasts(final String username)
            throws NotFoundException, JsonProcessingException {
        final Optional<Long> idUser = userService.getIdByUsername(username);
        if (idUser.isEmpty()) {
            return List.of();
        }
        final List<String> cityNames = cityService.findCityNameByUserId(idUser.get());

        final List<WeatherForecastCollectionDTO> wfCollList = new ArrayList<>();
        for (final String cityName : cityNames) {
            getWForecastCollByCity(cityName, ForecastType.Current, this::createCurrWForecastColl)
                .ifPresent(wfCollList::add);
            getWForecastCollByCity(cityName, ForecastType.Hourly, this::createHourlyWForecastColl)
                .ifPresent(wfCollList::add);
            getWForecastCollByCity(cityName, ForecastType.Daily, this::createDailyWForecastColl)
                .ifPresent(wfCollList::add);
        }

        return wfCollList;
    }


    private WeatherForecastDTO mapToWeatherForecastDTO(final WeatherForecast wf) {
        return new WeatherForecastDTO(wf);
    }

    private WeatherForecastCollectionDTO mapToWeatherForecastCollectionDTO(final WeatherForecastCollection wfc) {
        return new WeatherForecastCollectionDTO(wfc
            , wfc.getWeatherForecasts().stream().map(this::mapToWeatherForecastDTO).toList());
    }

    private Timestamp getUpperLimitDateTimeTimestamp(Timestamp ts) {
        return Timestamp.from(ts.toLocalDateTime().plusMinutes(OPEN_METEO_CURR_W_F_UPDATE_MAX_INTERVAL_IN_MIN)
            .toInstant(ZoneOffset.UTC));
    }

    private Timestamp getCurrentDateTimeTimestamp() {
        return Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    private Timestamp getCurrentDateTimeWithFlattedTimeTimestamp() {
        return Timestamp.from(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
            .toInstant(ZoneOffset.UTC));
    }
}