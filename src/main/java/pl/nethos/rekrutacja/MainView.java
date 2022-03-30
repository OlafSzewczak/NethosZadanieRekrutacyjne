package pl.nethos.rekrutacja;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.nethos.rekrutacja.http.dto.RejestrPodatnikowResponse;
import pl.nethos.rekrutacja.kontobankowe.KontoBankowe;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.serwis.KontoBankoweSerwis;
import pl.nethos.rekrutacja.serwis.KontrahentSerwis;
import pl.nethos.rekrutacja.serwis.NadawcaZapytanSerwis;

import java.io.IOException;
import java.time.LocalDate;

@Route
@Component
@PWA(name = "Nethos - Zadanie rekrutacyjne na stanowisko programisty", shortName = "Nethos - Rekrutacja")
public class MainView extends VerticalLayout {

    private final Grid<Kontrahent> kontrahentGrid = new Grid<>(Kontrahent.class);
    private final Grid<KontoBankowe> kontoBankoweGrid = new Grid<>(KontoBankowe.class);
    private final KontrahentSerwis kontrahentSerwis;
    private final KontoBankoweSerwis kontoBankoweSerwis;
    private final NadawcaZapytanSerwis nadawcaZapytanSerwis;


    @Autowired
    public MainView(KontrahentSerwis kontrahentSerwis, KontoBankoweSerwis kontoBankoweSerwis, NadawcaZapytanSerwis nadawcaZapytanSerwis) {
        this.kontrahentSerwis = kontrahentSerwis;
        this.kontoBankoweSerwis = kontoBankoweSerwis;
        this.nadawcaZapytanSerwis = nadawcaZapytanSerwis;

        addClassName("list-view");
        skonfigurujKontrahentGrid();
        skonfigurujKontoBankoweGrid();
        add(kontrahentGrid);
        zaktualizujListeKontrahentow();
    }

    private void wyslijZapytanie(String numerKonta, Long kontrahentId) throws IOException {
        String kontrahentNip = kontrahentSerwis.pobierzNip(kontrahentId);
        RejestrPodatnikowResponse rejestrPodatnikowResponse = nadawcaZapytanSerwis.sprawdzStatusPodatnika(numerKonta, kontrahentNip, LocalDate.now().toString());
        kontoBankoweSerwis.zaktualizuj(numerKonta, rejestrPodatnikowResponse.getStanWeryfikacji(), rejestrPodatnikowResponse.getRequestDateTime());
        zaktualizujListeKontBankowych(kontrahentId);
    }

    private void wyswietlKontaBankowe(Long kontrahentId) {
        add(kontoBankoweGrid);
        zaktualizujListeKontBankowych(kontrahentId);
    }

    private void skonfigurujKontoBankoweGrid() {
        kontoBankoweGrid.addClassNames("konto-bankowe-grid");
        kontoBankoweGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        kontoBankoweGrid.setColumns("numer", "aktywne", "domyslne", "wirtualne", "stanWeryfikacji", "dataWeryfikacji");

        kontoBankoweGrid.getColumnByKey("numer").setWidth("275px");
        kontoBankoweGrid.getColumnByKey("aktywne").setAutoWidth(true);
        kontoBankoweGrid.getColumnByKey("domyslne").setAutoWidth(true);
        kontoBankoweGrid.getColumnByKey("wirtualne").setAutoWidth(true);
        kontoBankoweGrid.getColumnByKey("stanWeryfikacji").setAutoWidth(true);
        kontoBankoweGrid.getColumnByKey("dataWeryfikacji").setWidth("200px");

        kontoBankoweGrid.addComponentColumn(t -> new Button("Weryfikuj", buttonClickEvent -> {
            try {
                wyslijZapytanie(t.getNumer(), t.getIdKontrahent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        })).setHeader("Weryfikacja");
    }

    private void skonfigurujKontrahentGrid() {
        kontrahentGrid.addClassNames("kontrahent-grid");
        kontrahentGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        kontrahentGrid.setColumns("nazwa", "nip");
        kontrahentGrid.getColumns().forEach(column -> column.setAutoWidth(true));
        kontrahentGrid.addItemClickListener(event -> wyswietlKontaBankowe(event.getItem().getId()));
    }

    private void zaktualizujListeKontrahentow() {
        kontrahentGrid.setItems(kontrahentSerwis.pobierzWszystkich());
    }

    private void zaktualizujListeKontBankowych(long kontrahentId) {
        kontoBankoweGrid.setItems(kontoBankoweSerwis.pobierzWszystkie(kontrahentId));
    }
}
