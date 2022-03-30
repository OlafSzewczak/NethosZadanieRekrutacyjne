package pl.nethos.rekrutacja.http.dto;

import lombok.Value;
import pl.nethos.rekrutacja.kontobankowe.StanWeryfikacji;

import java.time.LocalDateTime;

@Value
public class RejestrPodatnikowResponse {

    StanWeryfikacji stanWeryfikacji;
    LocalDateTime requestDateTime;
    boolean aktywne;

}
