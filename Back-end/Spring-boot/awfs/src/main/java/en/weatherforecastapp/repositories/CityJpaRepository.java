package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CityJpaRepository extends JpaRepository<City, Long> {
    Optional<City> findCityByName(String name);
    Optional<City> findFirstByName(String name);
}
