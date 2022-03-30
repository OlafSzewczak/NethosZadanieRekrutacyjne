package pl.nethos.rekrutacja.kontobankowe;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class KontoBankowe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "konto_bankowe_gen")
    @SequenceGenerator(name="konto_bankowe_gen", sequenceName = "konto_bankowe_seq")
    private Long id;

    private Long idKontrahent;

    private String numer;

    private Boolean aktywne;

    private Boolean domyslne;

    private Boolean wirtualne;

    private StanWeryfikacji stanWeryfikacji;

    private LocalDateTime dataWeryfikacji;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        KontoBankowe that = (KontoBankowe) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

