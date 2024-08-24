package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WeatherForecastJpaRepository extends JpaRepository<WeatherForecast, Long> {
}
