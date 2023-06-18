package me.takvim.xtakvim;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class Olay {
    private ObjectProperty<LocalDateTime> islemZamani;
    private ObjectProperty<LocalDateTime> olayBaslangicZamani;
    private StringProperty olayTanimi;
    private StringProperty olayTipi;
    private StringProperty olayAciklamasi;

    public Olay(LocalDateTime islemZamani, LocalDateTime olayBaslangicZamani, String olayTanimi,
                String olayTipi, String olayAciklamasi) {
        this.islemZamani = new SimpleObjectProperty<>(islemZamani);
        this.olayBaslangicZamani = new SimpleObjectProperty<>(olayBaslangicZamani);
        this.olayTanimi = new SimpleStringProperty(olayTanimi);
        this.olayTipi = new SimpleStringProperty(olayTipi);
        this.olayAciklamasi = new SimpleStringProperty(olayAciklamasi);
    }

    public LocalDateTime getIslemZamani() {
        return islemZamani.get();
    }

    public ObjectProperty<LocalDateTime> islemZamaniProperty() {
        return islemZamani;
    }

    public void setIslemZamani(LocalDateTime islemZamani) {
        this.islemZamani.set(islemZamani);
    }

    public LocalDateTime getOlayBaslangicZamani() {
        return olayBaslangicZamani.get();
    }

    public ObjectProperty<LocalDateTime> olayBaslangicZamaniProperty() {
        return olayBaslangicZamani;
    }

    public void setOlayBaslangicZamani(LocalDateTime olayBaslangicZamani) {
        this.olayBaslangicZamani.set(olayBaslangicZamani);
    }

    public String getOlayTanimi() {
        return olayTanimi.get();
    }

    public StringProperty olayTanimiProperty() {
        return olayTanimi;
    }

    public void setOlayTanimi(String olayTanimi) {
        this.olayTanimi.set(olayTanimi);
    }

    public String getOlayTipi() {
        return olayTipi.get();
    }

    public StringProperty olayTipiProperty() {
        return olayTipi;
    }

    public void setOlayTipi(String olayTipi) {
        this.olayTipi.set(olayTipi);
    }

    public String getOlayAciklamasi() {
        return olayAciklamasi.get();
    }

    public StringProperty olayAciklamasiProperty() {
        return olayAciklamasi;
    }

    public void setOlayAciklamasi(String olayAciklamasi) {
        this.olayAciklamasi.set(olayAciklamasi);
    }
}
