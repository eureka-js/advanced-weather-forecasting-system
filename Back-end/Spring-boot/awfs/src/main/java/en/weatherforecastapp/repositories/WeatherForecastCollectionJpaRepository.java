package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.WeatherForecastCollection;
import en.weatherforecastapp.utilities.CustomJpaQuery;
import en.weatherforecastapp.utilities.ForecastType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


public interface WeatherForecastCollectionJpaRepository extends JpaRepository<WeatherForecastCollection, Long> {
    @Query(nativeQuery = true, value = CustomJpaQuery.WFCOLL_EXISTS_BY_CITY_AND_DATE_TIME)
    Boolean existsByCityAndDateTime(String city, Timestamp dateTime, String forecastType);
}
