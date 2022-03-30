package pl.nethos.rekrutacja.serwis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.kontobankowe.KontoBankowe;
import pl.nethos.rekrutacja.kontobankowe.KontoBankoweRepository;
import pl.nethos.rekrutacja.kontobankowe.StanWeryfikacji;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class KontoBankoweSerwis{

    private final KontoBankoweRepository kontoBankoweRepository;

    @Autowired
    public KontoBankoweSerwis(KontoBankoweRepository kontoBankoweRepository) {
        this.kontoBankoweRepository = kontoBankoweRepository;
    }

    public List<KontoBankowe> pobierzWszystkie(Long kontrahentId) {
        return kontoBankoweRepository.findWithKontrahentId(kontrahentId);
    }

    public void zaktualizuj(String numerKonta, StanWeryfikacji stanWeryfikacji, LocalDateTime dataWeryfikacji) {
        kontoBankoweRepository.updateKontoBankowe(numerKonta, stanWeryfikacji, dataWeryfikacji);
    }




}
