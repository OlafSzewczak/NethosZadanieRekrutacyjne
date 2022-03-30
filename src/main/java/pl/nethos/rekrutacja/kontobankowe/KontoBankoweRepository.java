package pl.nethos.rekrutacja.kontobankowe;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class KontoBankoweRepository {

    @PersistenceContext
    private EntityManager em;

    public List<KontoBankowe> findWithKontrahentId(Long kontrahentId) {
        return em.createQuery(
                        "SELECT kb FROM KontoBankowe kb WHERE kb.idKontrahent = :kontrahentId", KontoBankowe.class)
                .setParameter("kontrahentId", kontrahentId)
                .getResultList();
    }

    public void updateKontoBankowe(Long id, StanWeryfikacji stanWeryfikacji, LocalDateTime dataWeryfikacji, boolean aktywne) {

        em.createQuery(
                        "UPDATE KontoBankowe kb SET kb.dataWeryfikacji = :dataWeryfikacji, kb.stanWeryfikacji = :stanWeryfikacji, kb.aktywne = :aktywne WHERE kb.id = :id "
                )
                .setParameter("id", id)
                .setParameter("stanWeryfikacji", stanWeryfikacji)
                .setParameter("dataWeryfikacji", dataWeryfikacji)
                .setParameter("aktywne", aktywne)
                .executeUpdate();
    }
}
