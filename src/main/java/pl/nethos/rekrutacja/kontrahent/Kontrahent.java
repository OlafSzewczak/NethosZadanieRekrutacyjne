package pl.nethos.rekrutacja.kontrahent;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Kontrahent {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kontrahent_gen")
    @SequenceGenerator(name="kontrahent_gen", sequenceName = "kontrahent_seq", allocationSize = 1)
    private Long id;

    private String nazwa;

    private String nip;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Kontrahent that = (Kontrahent) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
