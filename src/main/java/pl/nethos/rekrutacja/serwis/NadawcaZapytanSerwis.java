package pl.nethos.rekrutacja.serwis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.http.dto.RejestrPodatnikowResponse;
import pl.nethos.rekrutacja.kontobankowe.StanWeryfikacji;
import pl.nethos.rekrutacja.http.NadawcaZapytan;
import pl.nethos.rekrutacja.http.dto.Odpowiedz;

import java.time.LocalDateTime;

@Service
public class NadawcaZapytanSerwis {

    private final NadawcaZapytan nadawcaZapytan;

    @Autowired
    public NadawcaZapytanSerwis(NadawcaZapytan nadawcaZapytan) {
        this.nadawcaZapytan = nadawcaZapytan;
    }

    public RejestrPodatnikowResponse sprawdzStatusPodatnika(String numerKonta, String nip, String data) {
        try {
            Odpowiedz odpowiedz = this.nadawcaZapytan.checkKontoBankowe(numerKonta, nip, data);
            LocalDateTime dataZapytania = odpowiedz.getDataZapytania();
            if(odpowiedz.getKodStatusu() == 200) {
                return new RejestrPodatnikowResponse(StanWeryfikacji.ZWERYFIKOWANY, dataZapytania);
            } else if(odpowiedz.getKodStatusu() == 400) {
                return new RejestrPodatnikowResponse(StanWeryfikacji.BLEDNE_KONTO, dataZapytania);
            } else {
                return new RejestrPodatnikowResponse(StanWeryfikacji.NIEOKRESLONY, dataZapytania);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new RejestrPodatnikowResponse(StanWeryfikacji.BLAD_POBIERANIA_DANYCH, LocalDateTime.now());
        }
    }
}
