package pl.nethos.rekrutacja.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.http.dto.RejestrPodatnikowResponse;
import pl.nethos.rekrutacja.kontobankowe.StanWeryfikacji;

import java.time.LocalDateTime;
import java.util.Locale;

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
                boolean aktywne = odpowiedz.getKontoPrzypisane().toLowerCase(Locale.ROOT).trim().equals("\"tak\"");
                System.out.println(odpowiedz.getKontoPrzypisane());
                return new RejestrPodatnikowResponse(StanWeryfikacji.ZWERYFIKOWANY, dataZapytania, aktywne);
            } else if(odpowiedz.getKodStatusu() == 400) {
                return new RejestrPodatnikowResponse(StanWeryfikacji.BLEDNE_KONTO, dataZapytania, false);
            } else {
                return new RejestrPodatnikowResponse(StanWeryfikacji.NIEOKRESLONY, dataZapytania, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new RejestrPodatnikowResponse(StanWeryfikacji.BLAD_POBIERANIA_DANYCH, LocalDateTime.now(), false);
        }
    }
}
