package pl.nethos.rekrutacja.serwis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import java.util.List;

@Service
public class KontrahentSerwis {

    private final KontrahentRepository kontrahentRepository;

    @Autowired
    public KontrahentSerwis(KontrahentRepository kontrahentRepository) {
        this.kontrahentRepository = kontrahentRepository;
    }

    public List<Kontrahent> pobierzWszystkich() {
        return kontrahentRepository.all();
    }

    public String pobierzNip(Long id) {
        return kontrahentRepository.findNipWithId(id);
    }

}
