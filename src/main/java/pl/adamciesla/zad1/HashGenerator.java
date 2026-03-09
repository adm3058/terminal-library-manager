package pl.adamciesla.zad1;
import org.mindrot.jbcrypt.BCrypt;

public class HashGenerator {

    public static void main(String[] args) {
        System.out.println("user123:");
        System.out.println(BCrypt.hashpw("user123", BCrypt.gensalt()));

        System.out.println("admin123:");
        System.out.println(BCrypt.hashpw("admin123", BCrypt.gensalt()));
    }
}