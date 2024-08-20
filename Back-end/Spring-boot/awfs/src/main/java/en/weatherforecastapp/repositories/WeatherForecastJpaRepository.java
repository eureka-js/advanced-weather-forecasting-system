package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface WeatherForecastJpaRepository extends JpaRepository<WeatherForecast, Long> {
    @Query(nativeQuery = true, value = "SELECT \"Id\" FROM \"WeatherForecast\" WHERE \"City\" = :city LIMIT 1;")
    Optional<Long> getIdByCity(@Param("city") String city);
}
