package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.User;
import java.util.Optional;

public interface LoginService {
    Optional<User> login(String username, String password);
}