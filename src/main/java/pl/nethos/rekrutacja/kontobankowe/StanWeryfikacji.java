package pl.nethos.rekrutacja.kontobankowe;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum StanWeryfikacji {

    NIEOKRESLONY,
    BLEDNE_KONTO,
    ZWERYFIKOWANY,
    BLAD_POBIERANIA_DANYCH;

}
