package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.utilities.CustomDBQuery;
import en.weatherforecastapp.utilities.ForecastType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;


public interface WeatherForecastCollectionJpaRepository extends JpaRepository<WeatherForecastCollection, Long> {
    Optional<WeatherForecastCollection> findFirstByCity_NameAndDateTimeAndForecastType(String cityName
        , Timestamp dateTime, ForecastType forecastType);
    //@Query(value = CustomDBQuery.WFCOLL_FIND_WITH_MAX_DATE_TIME_BY_CITY_AND_FORECAST_TYPE, nativeQuery = true)
    @Query(value = CustomDBQuery.TEMPORARY_NON_CTE, nativeQuery = true)
    Optional<WeatherForecastCollection> findOneWithYoungestDateTimeByCityNameAndForecastType(String cityName
        , String forecastType);
}
