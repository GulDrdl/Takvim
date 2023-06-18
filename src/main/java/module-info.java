module me.takvim.xtakvim {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.takvim.xtakvim to javafx.fxml;
    exports me.takvim.xtakvim;
}