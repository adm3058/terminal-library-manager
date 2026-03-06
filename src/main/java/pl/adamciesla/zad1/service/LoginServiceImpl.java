package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.User;
import pl.adamciesla.zad1.repository.UserRepository;
import pl.adamciesla.zad1.security.PasswordHasher;
import java.util.Optional;

public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;
    private PasswordHasher passwordHasher;

    public LoginServiceImpl(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }
    @Override
    public Optional<User> login(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordHasher.matches(password, user.getPasswordHash())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}