package me.takvim.xtakvim;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    private TextField kullaniciAdiField;
    private PasswordField sifreField;
    private VBox girisEkran;
    private TextField adField;
    private TextField soyadField;
    private TextField kullaniciAdiField2;
    private PasswordField sifreField2;
    private TextField tcKimlikNoField;
    private TextField telefonField;
    private TextField emailField;
    private TextField adresField;
    private ComboBox<String> kullaniciTipiComboBox;

    private TableView<Olay> olaylarTable;
    private List<Olay> olaylarList;

    String girisYapanKullanici;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Giriş Uygulaması");

        girisEkran = new VBox(10);
        girisEkran.setPadding(new Insets(10));
        girisEkran.setAlignment(Pos.CENTER);

        Label girisBaslik = new Label("Giriş Yap");
        girisBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        kullaniciAdiField = new TextField();
        kullaniciAdiField.setPromptText("Kullanıcı Adı");

        sifreField = new PasswordField();
        sifreField.setPromptText("Şifre");

        Button girisButton = new Button("Giriş");
        girisButton.setOnAction(e -> {
            boolean girisBasarili = girisYap(kullaniciAdiField.getText(), sifreField.getText());
            if (girisBasarili) {
                girisYapanKullanici = kullaniciAdiField.getText();
                anaEkran(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Geçersiz kullanıcı adı veya şifre!");
                alert.showAndWait();
            }
        });

        Button kayitOlButton = new Button("Kayıt Ol");
        kayitOlButton.setOnAction(e -> {
            kayitEkran(primaryStage);
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(girisButton, kayitOlButton);
        buttonBox.setAlignment(Pos.CENTER);

        girisEkran.getChildren().addAll(girisBaslik, kullaniciAdiField, sifreField, buttonBox);

        VBox anaEkran = new VBox(10);
        anaEkran.setPadding(new Insets(10));
        anaEkran.setAlignment(Pos.CENTER);

        Label anaEkranBaslik = new Label("Ana Ekran");
        anaEkranBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button olayEkleButton = new Button("Olay Ekle");
        olayEkleButton.setOnAction(e -> {
            olayEkleEkran(primaryStage);
        });

        Button olayDuzenleButton = new Button("Olay Düzenle");
        olayDuzenleButton.setDisable(true);

        Button olaySilButton = new Button("Olay Sil");
        olaySilButton.setDisable(true);

        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(olayEkleButton, olayDuzenleButton, olaySilButton);
        buttonContainer.setAlignment(Pos.CENTER);

        TableColumn<Olay, LocalDateTime> islemZamaniColumn = new TableColumn<>("İşlem Zamanı");
        islemZamaniColumn.setCellValueFactory(cellData -> cellData.getValue().islemZamaniProperty());

        TableColumn<Olay, LocalDateTime> olayBaslangicZamaniColumn = new TableColumn<>("Olayın Başlangıç Zamanı");
        olayBaslangicZamaniColumn.setCellValueFactory(cellData -> cellData.getValue().olayBaslangicZamaniProperty());

        TableColumn<Olay, String> olayTanimiColumn = new TableColumn<>("Olayın Tanımlanması");
        olayTanimiColumn.setCellValueFactory(cellData -> cellData.getValue().olayTanimiProperty());

        TableColumn<Olay, String> olayTipiColumn = new TableColumn<>("Olayın Tipi");
        olayTipiColumn.setCellValueFactory(cellData -> cellData.getValue().olayTipiProperty());

        TableColumn<Olay, String> olayAciklamasiColumn = new TableColumn<>("Olayın Açıklaması");
        olayAciklamasiColumn.setCellValueFactory(cellData -> cellData.getValue().olayAciklamasiProperty());

        olaylarTable = new TableView<>();
        olaylarTable.getColumns().addAll(
                islemZamaniColumn,
                olayBaslangicZamaniColumn,
                olayTanimiColumn,
                olayTipiColumn,
                olayAciklamasiColumn
        );
        olaylarTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        anaEkran.getChildren().addAll(anaEkranBaslik, buttonContainer, olaylarTable);

        primaryStage.setScene(new Scene(girisEkran, 300, 200));
        primaryStage.show();
    }

    private boolean girisYap(String kullaniciAdi, String sifre) {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] kullaniciBilgileri = line.split(",");
                String kayitliKullaniciAdi = kullaniciBilgileri[2];
                String kayitliSifre = kullaniciBilgileri[3];

                if (kayitliKullaniciAdi.equals(kullaniciAdi) && kayitliSifre.equals(sifre)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    private void kayitEkran(Stage primaryStage) {
        VBox kayitEkran = new VBox(10);
        kayitEkran.setPadding(new Insets(10));
        kayitEkran.setAlignment(Pos.CENTER);

        Label kayitBaslik = new Label("Kayıt Ol");
        kayitBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        adField = new TextField();
        adField.setPromptText("Ad");

        soyadField = new TextField();
        soyadField.setPromptText("Soyad");

        kullaniciAdiField2 = new TextField();
        kullaniciAdiField2.setPromptText("Kullanıcı Adı");

        sifreField2 = new PasswordField();
        sifreField2.setPromptText("Şifre");

        tcKimlikNoField = new TextField();
        tcKimlikNoField.setPromptText("TC Kimlik No");

        telefonField = new TextField();
        telefonField.setPromptText("Telefon");

        emailField = new TextField();
        emailField.setPromptText("E-mail");

        adresField = new TextField();
        adresField.setPromptText("Adres");

        kullaniciTipiComboBox = new ComboBox<>();
        kullaniciTipiComboBox.getItems().addAll("Admin", "Kullanıcı");
        kullaniciTipiComboBox.setPromptText("Kullanıcı Tipi");

        Button kayitOlButton = new Button("Kayıt Ol");
        kayitOlButton.setOnAction(e -> {
            boolean kayitBasarili = kayitOl();
            if (kayitBasarili) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setHeaderText(null);
                alert.setContentText("Kayıt başarıyla tamamlandı!");
                alert.showAndWait();
                start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Kayıt oluşturulamadı!");
                alert.showAndWait();
            }
        });

        kayitEkran.getChildren().addAll(kayitBaslik, adField, soyadField, kullaniciAdiField2, sifreField2,
                tcKimlikNoField, telefonField, emailField, adresField, kullaniciTipiComboBox, kayitOlButton);

        primaryStage.setScene(new Scene(kayitEkran, 300, 400));
        primaryStage.show();
    }

    private boolean kayitOl() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            String ad = adField.getText();
            String soyad = soyadField.getText();
            String kullaniciAdi = kullaniciAdiField2.getText();
            String sifre = sifreField2.getText();
            String tcKimlikNo = tcKimlikNoField.getText();
            String telefon = telefonField.getText();
            String email = emailField.getText();
            String adres = adresField.getText();
            String kullaniciTipi = kullaniciTipiComboBox.getValue();

            writer.write(ad + "," + soyad + "," + kullaniciAdi + "," + sifre + "," +
                    tcKimlikNo + "," + telefon + "," + email + "," + adres + "," + kullaniciTipi);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void anaEkran(Stage primaryStage) {
        VBox anaEkran = new VBox(10);
        anaEkran.setPadding(new Insets(10));
        anaEkran.setAlignment(Pos.CENTER);

        Label anaEkranBaslik = new Label("Ana Ekran");
        anaEkranBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button olayEkleButton = new Button("Olay Ekle");
        olayEkleButton.setOnAction(e -> {
            olayEkleEkran(primaryStage);
        });

        Button olayDuzenleButton = new Button("Olay Düzenle");
        olayDuzenleButton.setDisable(true);

        Button olaySilButton = new Button("Olay Sil");
        olaySilButton.setDisable(true);

        Button yenileButton = new Button("Yenile");
        yenileButton.setOnAction(e -> {
            List<Olay> olaylar = oku("edb.txt");
            olaylarTable.getItems().clear();
            olaylarTable.getItems().setAll(olaylar);
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(olayEkleButton, olayDuzenleButton, olaySilButton, yenileButton);
        buttonContainer.setAlignment(Pos.CENTER);

        TableColumn<Olay, LocalDateTime> islemZamaniColumn = new TableColumn<>("İşlem Zamanı");
        islemZamaniColumn.setCellValueFactory(cellData -> cellData.getValue().islemZamaniProperty());

        TableColumn<Olay, LocalDateTime> olayBaslangicZamaniColumn = new TableColumn<>("Olayın Başlangıç Zamanı");
        olayBaslangicZamaniColumn.setCellValueFactory(cellData -> cellData.getValue().olayBaslangicZamaniProperty());

        TableColumn<Olay, String> olayTanimiColumn = new TableColumn<>("Olayın Tanımlanması");
        olayTanimiColumn.setCellValueFactory(cellData -> cellData.getValue().olayTanimiProperty());

        TableColumn<Olay, String> olayTipiColumn = new TableColumn<>("Olayın Tipi");
        olayTipiColumn.setCellValueFactory(cellData -> cellData.getValue().olayTipiProperty());

        TableColumn<Olay, String> olayAciklamasiColumn = new TableColumn<>("Olayın Açıklaması");
        olayAciklamasiColumn.setCellValueFactory(cellData -> cellData.getValue().olayAciklamasiProperty());

        olaylarTable = new TableView<>();
        olaylarTable.getColumns().addAll(
                islemZamaniColumn,
                olayBaslangicZamaniColumn,
                olayTanimiColumn,
                olayTipiColumn,
                olayAciklamasiColumn
        );
        olaylarTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        anaEkran.getChildren().addAll(anaEkranBaslik, buttonContainer, olaylarTable);

        yenileButton.fire();

        primaryStage.setScene(new Scene(anaEkran, 400, 300));
        primaryStage.show();
    }

    private List<Olay> oku(String dosyaAdi) {
        List<Olay> olaylar = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaAdi))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] veriler = satir.split(",");

                if (veriler.length == 6) {
                    LocalDateTime islemTarihi = LocalDateTime.parse(veriler[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    LocalDateTime olayBaslangicZamani = LocalDateTime.parse(veriler[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String kullaniciAdiT = veriler[2].trim();
                    String olayTanimi = veriler[3].trim();
                    String olayTipi = veriler[4].trim();
                    String olayAcikl = veriler[5].trim();

                    //System.out.println(islemTarihi + "," + olayBaslangicZamani + "," + kullaniciAdiT + "," + olayTanimi + "," + olayTipi + "," + olayAcikl);

                    if (kullaniciAdiT.equals(girisYapanKullanici)) {
                        Olay olay = new Olay(islemTarihi, olayBaslangicZamani, olayTanimi, olayTipi, olayAcikl);
                        //System.out.println(olay.getIslemZamani() + "," + olay.getOlayBaslangicZamani() + "," + olay.getOlayTanimi() + "," + olay.getOlayTipi() + "," + olay.getOlayAciklamasi());
                        // Olayı listeye ekleme
                        olaylar.add(olay);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return olaylar;
    }

    private void olayEkleEkran(Stage primaryStage) {
        VBox olayEkleEkran = new VBox(10);
        olayEkleEkran.setPadding(new Insets(10));
        olayEkleEkran.setAlignment(Pos.CENTER);

        Label olayEkleBaslik = new Label("Olay Ekle");
        olayEkleBaslik.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        DatePicker tarihPicker = new DatePicker();
        tarihPicker.setPromptText("Tarih");

        TextField saatPicker = new TextField();
        saatPicker.setPromptText("Saat (HH:mm)");

        TextField olayTanimiField = new TextField();
        olayTanimiField.setPromptText("Olayın Tanımlanması");

        TextField olayTipiField = new TextField();
        olayTipiField.setPromptText("Olayın Tipi");

        TextField olayAciklamasiField = new TextField();
        olayAciklamasiField.setPromptText("Olayın Açıklaması");

        Button olayEkleButton = new Button("Olay Ekle");
        olayEkleButton.setOnAction(e -> {
            LocalDate tarih = tarihPicker.getValue();
            LocalTime saat = LocalTime.parse(saatPicker.getText());
            LocalDateTime tarihVeSaat = LocalDateTime.of(tarih, saat);

            String olayTanimi = olayTanimiField.getText();
            String olayTipi = olayTipiField.getText();
            String olayAciklamasi = olayAciklamasiField.getText();

            Olay yeniOlay = new Olay(LocalDateTime.now(), tarihVeSaat, olayTanimi, olayTipi, olayAciklamasi);
            olaylarTable.getItems().add(yeniOlay);

            // Dosyaya kaydetme işlemi
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("edb.txt", true))) {
                String satir =
                        LocalDate.now() + " " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) +
                        "," + tarihVeSaat.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                        "," + girisYapanKullanici +
                        "," + olayTanimi +
                        "," + olayTipi +
                        "," + olayAciklamasi;
                writer.write(satir);
                writer.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            anaEkran(primaryStage);
        });

        Button iptalButton = new Button("İptal");
        iptalButton.setOnAction(e -> {
            anaEkran(primaryStage);
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.getChildren().addAll(olayEkleButton, iptalButton);
        buttonContainer.setAlignment(Pos.CENTER);

        olayEkleEkran.getChildren().addAll(
                olayEkleBaslik,
                tarihPicker,
                saatPicker,
                olayTanimiField,
                olayTipiField,
                olayAciklamasiField,
                buttonContainer
        );

        primaryStage.setScene(new Scene(olayEkleEkran, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}