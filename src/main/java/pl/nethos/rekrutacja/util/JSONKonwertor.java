package pl.nethos.rekrutacja.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.nethos.rekrutacja.http.dto.Odpowiedz;

import java.io.IOException;
import java.time.LocalDateTime;

public class JSONKonwertor {


    public static Odpowiedz jsonToOdpowiedz(String json) throws IOException {

        JsonNode jsonDrzewo = new ObjectMapper().readTree(json);
        Odpowiedz wartoscOdpowiedzi = new Odpowiedz();
        JsonNode nodeRodzic = jsonDrzewo.get("result");

        String kontoPrzypisane = nodeRodzic.get("accountAssigned").toString();
        LocalDateTime dataZapytania = FormatyzatorDaty.toLocalDateTime(nodeRodzic.get("requestDateTime").toString());
        String zapytanieId = nodeRodzic.get("requestId").toString();

        wartoscOdpowiedzi.setKontoPrzypisane(kontoPrzypisane);
        wartoscOdpowiedzi.setDataZapytania(dataZapytania);
        wartoscOdpowiedzi.setZapytanieId(zapytanieId);

        return wartoscOdpowiedzi;
    }

}
