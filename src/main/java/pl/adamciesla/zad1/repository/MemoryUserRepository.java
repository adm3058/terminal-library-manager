package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.model.Role;
import pl.adamciesla.zad1.model.User;
import pl.adamciesla.zad1.security.PasswordHasher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    public MemoryUserRepository(PasswordHasher hasher) {
        users.add(new User("user", hasher.hash("user123"), Role.USER));
        users.add(new User("admin", hasher.hash("admin123"), Role.ADMIN));
    }
    @Override
    public Optional<User> findByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}