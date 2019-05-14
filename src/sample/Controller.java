package sample;

import DB.Database;
import SCAN.Topreality;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import static SCAN.Topreality.createWorker;
import static java.lang.Math.round;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Controller implements Initializable {
    public Button generuj;
    public ProgressIndicator Indicator;
    Task copyWorker;
    public Button Vyhladaj;
    @FXML
    public TextField pocetInzeratov;
    @FXML
    public TextField pocetNovychInzeratov;
    @FXML
    public TextField stavnahravania;
    public ProgressBar stavUlohy;
    @FXML
    private TextField vysledok;
    private ResourceBundle bundle;
    @FXML
    private ComboBox<String> dbtypeTransakCbx;
    @FXML
    private ComboBox<String> dbtypeCbx;
    @FXML
    private ComboBox<String> dbtypeNehnutCbx;
    @FXML
    private ComboBox<String> dbtypeMestoCbx;
    @FXML
    private Button actionZoznamNehnutelnosti;



    public Database database;
    private ObservableList<String> dbTypeList = FXCollections.observableArrayList();
    public Topreality topreality ;
    public String server,mesto,typNehnutelnosti,transakcia,linkVyhladavaci;
    public Integer pocet,indexServer;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dbtypeTransakCbx.getItems().addAll("Predaj","Prenájom","Nákup");

        try {

            Database database = new Database("kc014100db", "kc014100", "jgowihez");
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            String[] columns3 = null;
            Object[] params3 = null;
            ResultSet rs = null;

            rs = database.select("servre",columns3,params3);
            while (rs.next()) {
                // Now add the comboBox addAll statement
                dbtypeCbx.getItems().addAll(rs.getString("nazov"));

            }

            rs = database.select("typ_nehnutelnosti",columns3,params3);
            while (rs.next()) {
                // Now add the comboBox addAll statement
                dbtypeNehnutCbx.getItems().addAll(rs.getString("nazov"));
            }

            rs = database.select("lokalita",columns3,params3);
            while (rs.next()) {
                // Now add the comboBox addAll statement

                dbtypeMestoCbx.getItems().addAll(rs.getString("nazov"));
            }
        } catch (SQLException ex) {
            System.out.println("error - "+ex.getMessage());
        }
    }

    public void actionGeneruj(ActionEvent actionEvent) {
        linkVyhladavaci = server+"/"+mesto+"/"+typNehnutelnosti;
        vysledok.setText(linkVyhladavaci);
    }

    public void actionVyberServer(ActionEvent actionEvent) {
        String hodnota = this.dbtypeCbx.getValue();

        server = topreality.getServerLink(hodnota);

    }
    public int indexVybranehoServera(){
        return indexServer = this.dbtypeCbx.getSelectionModel().getSelectedIndex();
    }

    public void actionVyberTyp(ActionEvent actionEvent) {
        String hodnota = this.dbtypeNehnutCbx.getValue();
        typNehnutelnosti = "byty/"+topreality.getBezDiakritikyMale(hodnota);

    }

    public void actionVyberMesto(ActionEvent actionEvent) {
        String hodnota = this.dbtypeMestoCbx.getValue();
        mesto = "obec-"+topreality.getBezDiakritikyMale(hodnota);

    }

    public void actionVyberTransakciu(ActionEvent actionEvent) {
        String hodnota = this.dbtypeTransakCbx.getValue();
        transakcia = topreality.getBezDiakritikyMale(hodnota);


    }

    public void actionVyhladaj(ActionEvent actionEvent) {
        String linkVyhladavaciIter;
        Topreality doc1 = null;
        Integer nove = 0;
        Integer noveCelkom = 0;
        Integer pocetStranok = 0;


        stavUlohy.setProgress(0);
        copyWorker = createWorker();
        stavUlohy.progressProperty().unbind();
        stavUlohy.progressProperty().bind(copyWorker.progressProperty());

        copyWorker.messageProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });
        new Thread(copyWorker).start();


        try {
            doc1 = new Topreality(linkVyhladavaci);
            pocet = doc1.getPocetInzeratov();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pocetStranok = pocet/15;

        for (int i=1;i<round(pocetStranok+2);i++){
            linkVyhladavaciIter = linkVyhladavaci+"/"+Integer.toString(i)+".html";
            stavnahravania.setText(Integer.toString(pocetStranok*i));

            try {
                doc1 = new Topreality(linkVyhladavaciIter);
                nove = doc1.nahrajNoveInzeraty();
            } catch (IOException e) {
                e.printStackTrace();
            }
            noveCelkom = noveCelkom + nove;
        }

        pocetInzeratov.setText(Integer.toString(pocet));
        pocetNovychInzeratov.setText(Integer.toString(noveCelkom));

    }
    public void actionZoznamNehnutelnosti(ActionEvent actionEvent) throws IOException {
        Locale locale = new Locale("English","EN");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("zoznamNehnutelnosti.fxml"), ResourceBundle.getBundle("string",locale));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();

        stage.setTitle("Zoznam Nehnutelnosti");
        stage.setScene(new Scene(root, 1300, 900));

        stage.show();

    }
}


