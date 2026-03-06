package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.model.User;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);

}