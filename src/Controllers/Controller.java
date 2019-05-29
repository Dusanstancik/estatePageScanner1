/*
 * Copyright (c) 2019. Dušan Stančík
 */

package Controllers;

import DB.Database;
import SCAN.SearchAndSave;
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

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static SCAN.Topreality.createWorker;
import static sample.Windows.*;
import static java.lang.Math.round;

import javafx.stage.Stage;


public class Controller implements Initializable {
    public Button generuj;
    @FXML
    public Label ProcesNahravania;
    @FXML
    public Button Vyhladaj;
    @FXML
    public TextField pocetInzeratov;
    @FXML
    public TextField pocetNovychInzeratov;
    @FXML
    public TextField stavnahravania;
    @FXML
    public ProgressBar stavUlohy;
    @FXML
    public Button ZoznamNehnutelnosti;
    public Button cancel;
    @FXML
    private TextField vysledok;
    @FXML
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
            String timeStamp = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Controllers.ZoznamNehnutelnosti.nastavComboBoxy(database, dbtypeCbx, dbtypeNehnutCbx, dbtypeMestoCbx);
        } catch (SQLException ex) {
            System.out.println("error - "+ex.getMessage());
        }
    }

    public void actionGeneruj(ActionEvent actionEvent) {

        /** testovanie vybratia vsetkych poloziek pri vytvarani dotazu **/
        Integer allChoice = 1;
        String menoPolozky = "";

        if (server==null){
            menoPolozky="Nazov servera";
            allChoice = 0;
        }
        if (mesto==null){
            menoPolozky=menoPolozky+",Nazov lokality";
            allChoice = 0;
        }

        if (typNehnutelnosti==null){
            menoPolozky=menoPolozky+",Typ nehnutelnosti";
            allChoice = 0;
        }
        if (transakcia==null){ transakcia="";}

        if (allChoice != 0) {
            if (typNehnutelnosti.equals("byty")) {
                linkVyhladavaci = server + "/" + mesto + "/" + typNehnutelnosti;
            } else {
                linkVyhladavaci = server + "/" + mesto + "/" + typNehnutelnosti + "/" + transakcia;
            }
            vysledok.setText(linkVyhladavaci);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chyba");
            alert.setHeaderText("Nespravne zadany vyhladavaci dopyt:");
            alert.setContentText("Nevybrali ste polozku: " + menoPolozky);
            alert.showAndWait();

            return;

        }


    }

    /**
     *
     * @param actionEvent
     */
    public void actionVyberServer(ActionEvent actionEvent) {
        String hodnota = this.dbtypeCbx.getValue();
        server = topreality.getServerLink(hodnota);

    }
    public int indexVybranehoServera(){
        return indexServer = this.dbtypeCbx.getSelectionModel().getSelectedIndex();
    }

    @SuppressWarnings("AccessStaticViaInstance")
    public void actionVyberTyp(ActionEvent actionEvent) {
        String hodnota = this.dbtypeNehnutCbx.getValue();
        if (hodnota.contains("Byty")){
            typNehnutelnosti = "byty" ;
        } else {
            typNehnutelnosti = "byty/" + topreality.getBezDiakritikyMale(hodnota);
        }
    }

    /**
     *
     * @param actionEvent
     */
    public void actionVyberMesto(ActionEvent actionEvent) {
        String hodnota = this.dbtypeMestoCbx.getValue();
        mesto = "obec-"+topreality.getBezDiakritikyMale(hodnota);

    }

    public void actionVyberTransakciu(ActionEvent actionEvent) {
        String hodnota = this.dbtypeTransakCbx.getValue();
        transakcia = topreality.getBezDiakritikyMale(hodnota);


    }

    public void actionVyhladaj(ActionEvent actionEvent) {

        if (linkVyhladavaci==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Chyba");
            alert.setHeaderText("Chyba vyhladavaci dopyt:");
            alert.setContentText("Nezadali ste vyhladavaci dopyt");
            alert.showAndWait();

            return;
        }


        SearchAndSave task = new SearchAndSave(linkVyhladavaci);
        ProcesNahravania.textProperty().bind(task.messageProperty());
        stavUlohy.progressProperty().unbind();
        stavUlohy.progressProperty().bind(task.progressProperty());
        pocetNovychInzeratov.setText("");

        task.setOnSucceeded((succeededEvent) -> {
            stavUlohy.progressProperty().unbind();
            stavUlohy.setProgress(100);
            pocetNovychInzeratov.setText(task.getValue().toString());
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();


    }
    public void actionZoznamNehnutelnosti(ActionEvent actionEvent) throws IOException {
        /* spusti okno zoznam nehnutelnosti*/
        winZoznamNehnutelnosti();

    }
    public void ActionCancel(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) cancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}


