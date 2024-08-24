package en.weatherforecastapp.services;

import en.weatherforecastapp.models.User;

import java.util.Optional;


public interface UserService {
    Optional<User> findByUsername(final String username);
    Optional<User> save(final String username);
    Optional<Long> getIdByUsername(final String username);
}
