package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.WeatherForecast;
import en.weatherforecastapp.utilities.CustomJpaQuery;
import en.weatherforecastapp.utilities.ForecastType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;


public interface WeatherForecastJpaRepository extends JpaRepository<WeatherForecast, Long> {
    @Query(nativeQuery = true, value = CustomJpaQuery.WF_EXISTS_BY_CITY_AND_DATE_TIME)
    Boolean existsByCityAndDateTime(String city, Timestamp dateTime, String forecastType);
}
