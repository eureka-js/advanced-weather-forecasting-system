package en.weatherforecastapp.services;

import en.weatherforecastapp.models.User;
import en.weatherforecastapp.repositories.UserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    
    
    @Override
    public Optional<User> findByUsername(final String username) {
        return userJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<User> save(final String username) {
        return Optional.of(userJpaRepository.save(new User(null, username)));
    }

    @Override
    public Optional<Long> getIdByUsername(final String username) {
        return userJpaRepository.getIdByUsername(username);
    }
}
