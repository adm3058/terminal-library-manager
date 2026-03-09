package pl.adamciesla.zad1.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Loan {

    private long id;
    private long userId;
    private long bookId;
    private LocalDate loanDate;
    private LocalDate returnDate;

}