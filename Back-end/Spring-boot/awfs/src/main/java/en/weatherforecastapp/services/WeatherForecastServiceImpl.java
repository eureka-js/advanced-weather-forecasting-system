package en.weatherforecastapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.models.dto.WeatherForecastCollectionDTO;
import en.weatherforecastapp.models.dto.WeatherForecastDTO;
import en.weatherforecastapp.repositories.WeatherForecastCollectionJpaRepository;
import en.weatherforecastapp.repositories.WeatherForecastJpaRepository;
import en.weatherforecastapp.utilities.ForecastType;
import en.weatherforecastapp.utilities.WeatherApiClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {
    private static final Integer M_IN_KM = 1000;

    private final WeatherForecastJpaRepository wfJpaRepository;
    private final WeatherForecastCollectionJpaRepository wfCollJpaRepository;
    private WeatherApiClient weatherApiClient;


    @Override
    public Optional<WeatherForecastDTO> getCurrWForecastByCity(final String city) throws JsonProcessingException {
        JsonNode wForecastInf = weatherApiClient.getWeatherInfByCity(city, ForecastType.Current);

        WeatherForecast newWForecast = new WeatherForecast(
            null
            , wForecastInf.get("city_name").asText()
            , wForecastInf.get("temp").floatValue()
            , wForecastInf.get("weather").get("description").asText()
            , Timestamp.from(Instant.ofEpochSecond(wForecastInf.get("ts").asLong()))
            , wForecastInf.get("uv").intValue()
            , wForecastInf.get("vis").intValue() * M_IN_KM
            , wForecastInf.get("rh").asInt()
            , wForecastInf.get("wind_spd").floatValue()
            , ForecastType.Current
            , wForecastInf.get("app_temp").floatValue()
            , wForecastInf.get("pres").asInt()
            , null
        );

        if (!wfJpaRepository.existsByCityAndDateTime(newWForecast.getCity(), newWForecast.getDateTime()
                , newWForecast.getForecastType().name())) {
            wfJpaRepository.save(newWForecast);
        }

        return Optional.of(mapToWeatherForecastDTO(newWForecast));
    }

    @Override
    public Optional<WeatherForecastCollectionDTO> getWForecastCollByCity(final String city, final ForecastType ft)
            throws JsonProcessingException {
        JsonNode wForecastCollInf = weatherApiClient.getWeatherInfByCity(city, ft);

        WeatherForecastCollection newWForecastColl = new WeatherForecastCollection(
            null
            , city
            , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(0).asLong()))
            , ft
            , new ArrayList<>()
        );

        if (ft == ForecastType.Hourly) {
            for (int i = 0; i < wForecastCollInf.get("time").size(); ++i) {
                newWForecastColl.getWeatherForecasts().add(new WeatherForecast(
                    null
                    , city
                    , wForecastCollInf.get("temperature_2m").get(i).floatValue()
                    , WeatherApiClient.weatherDesc.get(wForecastCollInf.get("weather_code").get(i).asInt())
                    , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(i).asLong()))
                    , wForecastCollInf.get("uv_index").get(i).intValue()
                    , wForecastCollInf.get("visibility").get(i).intValue()
                    , wForecastCollInf.get("relative_humidity_2m").get(i).asInt()
                    , wForecastCollInf.get("wind_speed_10m").get(i).floatValue()
                    , ft
                    , wForecastCollInf.get("apparent_temperature").get(i).floatValue()
                    , wForecastCollInf.get("pressure_msl").get(i).asInt()
                    , newWForecastColl
                ));
            }
        } else {
            for (int i = 0; i < wForecastCollInf.get("time").size(); ++i) {
                // Temporary placeholder values because, at this point, I couldn't find any free web services
                //  that could provide me with them for daily forecasts (if there is any)
                newWForecastColl.getWeatherForecasts().add(new WeatherForecast(
                    null
                    , city
                    , wForecastCollInf.get("temperature_2m_max").get(i).floatValue()
                    , WeatherApiClient.weatherDesc.get(wForecastCollInf.get("weather_code").get(i).asInt())
                    , Timestamp.from(Instant.ofEpochSecond(wForecastCollInf.get("time").get(i).asLong()))
                    , wForecastCollInf.get("uv_index_max").get(i).intValue()
                    , 0
                    , 0
                    , wForecastCollInf.get("wind_speed_10m_max").get(i).floatValue()
                    , ft
                    , wForecastCollInf.get("apparent_temperature_max").get(i).floatValue()
                    , 0
                    , newWForecastColl
                ));
            }
        }

        if (!wfCollJpaRepository.existsByCityAndDateTime(newWForecastColl.getCity(), newWForecastColl.getDateTime()
                , newWForecastColl.getForecastType().name())) {
            wfCollJpaRepository.save(newWForecastColl);
        }

        return Optional.of(mapToWeatherForecastCollectionDTO(newWForecastColl));
    }


    private WeatherForecastDTO mapToWeatherForecastDTO(final WeatherForecast wf) {
        return new WeatherForecastDTO(wf.getCity(), wf.getTemperature(), wf.getDescription(), wf.getDateTime()
            , wf.getUVIndex(), wf.getVisibility(), wf.getHumidity(), wf.getWindSpeed(), wf.getForecastType()
            , wf.getFeelsLikeTemperature(), wf.getPressure());
    }

    private WeatherForecastCollectionDTO mapToWeatherForecastCollectionDTO(final WeatherForecastCollection wfc) {
        return new WeatherForecastCollectionDTO(wfc.getCity(), wfc.getDateTime(), wfc.getForecastType()
            , wfc.getWeatherForecasts().stream().map(this::mapToWeatherForecastDTO).toList());
    }
}
