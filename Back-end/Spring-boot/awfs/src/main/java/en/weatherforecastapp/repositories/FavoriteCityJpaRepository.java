package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.FavoriteCity;
import en.weatherforecastapp.utilities.CustomDBQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;

import java.util.List;


public interface FavoriteCityJpaRepository extends JpaRepository<FavoriteCity, Long> {
    boolean existsByUser_IdAndCity_Id(Long idUser, Long idCity);
    @Query(value = CustomDBQuery.FAV_CITY_GET_CITY_NAME_BY_USER_ID, nativeQuery = true)
    List<String> findCity_NameByUser_Id(Long idUser);
    List<FavoriteCity> findAllByUser_Username(String username);
}
