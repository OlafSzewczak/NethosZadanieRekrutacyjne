package pl.nethos.rekrutacja.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.nethos.rekrutacja.util.FormatyzatorDaty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Service
final class NadawcaZapytan {

    public Odpowiedz checkKontoBankowe(String numerKonta, String nip, String data) throws IOException {
        URL url = new URL("https://wl-api.mf.gov.pl/api/check/nip/" + nip + "/bank-account/" + numerKonta + "?date=" + data);
        HttpURLConnection polaczenie = (HttpURLConnection) url.openConnection();
        polaczenie.setRequestMethod("GET");

        int kodStatusu = polaczenie.getResponseCode();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(polaczenie.getInputStream()))) {
            String liniaWejscia;
            StringBuilder zawartosc = new StringBuilder();
            while ((liniaWejscia = in.readLine()) != null) {
                zawartosc.append(liniaWejscia);
            }
            String json = zawartosc.toString();
            JsonNode jsonDrzewo = new ObjectMapper().readTree(json);
            JsonNode nodeRodzic = jsonDrzewo.get("result");

            String kontoPrzypisane = nodeRodzic.get("accountAssigned").toString();
            LocalDateTime dataZapytania = FormatyzatorDaty.toLocalDateTime(nodeRodzic.get("requestDateTime").toString());
            String zapytanieId = nodeRodzic.get("requestId").toString();
            return new Odpowiedz(kontoPrzypisane, dataZapytania, zapytanieId, kodStatusu);
        }
    }


}
