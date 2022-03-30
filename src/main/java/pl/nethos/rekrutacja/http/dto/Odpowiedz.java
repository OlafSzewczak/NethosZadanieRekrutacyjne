package pl.nethos.rekrutacja.http.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Odpowiedz {

    private String kontoPrzypisane;
    private LocalDateTime dataZapytania;
    private String zapytanieId;
    private int kodStatusu;

    public Odpowiedz(String kontoPrzypisane, LocalDateTime dataZapytania, String zapytanieId) {
        this.kontoPrzypisane = kontoPrzypisane;
        this.dataZapytania = dataZapytania;
        this.zapytanieId = zapytanieId;
    }
}
