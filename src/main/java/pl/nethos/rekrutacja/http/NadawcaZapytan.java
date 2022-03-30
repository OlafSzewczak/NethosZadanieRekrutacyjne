package pl.nethos.rekrutacja.http;

import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.http.dto.Odpowiedz;
import pl.nethos.rekrutacja.util.JSONKonwertor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public final class NadawcaZapytan {

    public Odpowiedz checkKontoBankowe(String numerKonta, String nip, String data) throws IOException {

        URL url = new URL("https://wl-api.mf.gov.pl/api/check/nip/" + nip + "/bank-account/" + numerKonta + "?date=" + data);
        HttpURLConnection polaczenie = (HttpURLConnection) url.openConnection();
        polaczenie.setRequestMethod("GET");

        int kodStatusu = polaczenie.getResponseCode();

        try(BufferedReader in = new BufferedReader(new InputStreamReader(polaczenie.getInputStream()))) {
            String liniaWejscia;
            StringBuilder zawartosc = new StringBuilder();
            while ((liniaWejscia = in.readLine()) != null) {
                zawartosc.append(liniaWejscia);
            }
            String json = zawartosc.toString();
            Odpowiedz odpowiedz = JSONKonwertor.jsonToOdpowiedz(json);
            return new Odpowiedz(odpowiedz.getKontoPrzypisane(), odpowiedz.getDataZapytania(), odpowiedz.getZapytanieId(), kodStatusu);
        }
    }



}
