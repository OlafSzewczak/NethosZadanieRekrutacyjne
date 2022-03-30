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

    public void updateKontoBankowe(String numerKonta, StanWeryfikacji stanWeryfikacji, LocalDateTime dataWeryfikacji) {

        em.createQuery(
                        "UPDATE KontoBankowe kb SET kb.dataWeryfikacji = :dataWeryfikacji, kb.stanWeryfikacji = :stanWeryfikacji WHERE kb.numer = :numerKonta "
                )
                .setParameter("numerKonta", numerKonta)
                .setParameter("stanWeryfikacji", stanWeryfikacji)
                .setParameter("dataWeryfikacji", dataWeryfikacji)
                .executeUpdate();
    }
}
