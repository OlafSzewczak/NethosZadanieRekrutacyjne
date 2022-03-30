package pl.nethos.rekrutacja.http;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Odpowiedz {

    private String kontoPrzypisane;
    private LocalDateTime dataZapytania;
    private String zapytanieId;
    private int kodStatusu;
}
