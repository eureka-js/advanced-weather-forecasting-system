package en.weatherforecastapp.repositories;

import en.weatherforecastapp.models.User;
import en.weatherforecastapp.utilities.CustomDBQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = CustomDBQuery.USER_GET_ID_BY_USERNAME)
    Optional<Long> getIdByUsername(String username);
    Optional<User> findByUsername(String username);
}
