package pl.nethos.rekrutacja.kontrahent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class KontrahentRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Kontrahent> all() {
        return em.createQuery("SELECT k FROM Kontrahent k", Kontrahent.class).getResultList();
    }

    public String findNipWithId(Long id) {
        return em.createQuery("SELECT k.nip FROM Kontrahent k WHERE k.id = :id",String.class)
                .setParameter("id", id)
                .getSingleResult();
    }

}
