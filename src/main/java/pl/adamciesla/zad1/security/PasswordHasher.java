package pl.adamciesla.zad1.security;

public interface PasswordHasher {
    String hash(String plainPassword);
    boolean matches(String plainPassword, String hashedPassword);
}