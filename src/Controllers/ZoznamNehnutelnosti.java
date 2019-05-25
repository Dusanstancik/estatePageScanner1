package Controllers;



import DB.Database;
import SCAN.Parametre;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import Models.ModelNehnutelnosti;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;

import static sample.Windows.winPotvrdzujucaSprava;


public class ZoznamNehnutelnosti implements Initializable {
    @FXML
    public Button sortByServer;
    @FXML
    public Button btnDelete;
    @FXML
    public ComboBox<String> cbxLokalita;
    @FXML
    public Button btnEdit;
    @FXML
    public Button btnDeleteFilter;
    public TextField tfMaxValue;
    public TextField tfMinValue;
    public TextField tfAverageValue;
    public TextField tfMaxM2;
    public TextField tfMinM2;
    public TextField tfAVGM2;
    public TextField tfCountRecords;
    @FXML
    private ComboBox<String> cbxTransakcia;
    @FXML
    private ComboBox<String> dbtypeNehnutCbx;
    @FXML
    private ComboBox<String> cbxServer;
    @FXML
    public Button btnOpenURL;
    @FXML
    public Button btnOpenSoret;



    Connection con;
    @FXML
    public TableColumn <ModelNehnutelnosti,String> column1;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column2;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column3;
    @FXML
    public TableColumn <ModelNehnutelnosti, Hyperlink>column4;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column5;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column6;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column7;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column8;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column9;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column10;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column11;


    @FXML
    public TableView <ModelNehnutelnosti> table;
    public String podmTypeProperty;
    public String podmTransaction;
    public String indexServer;
    public String podmLokalita;



    ObservableList<ModelNehnutelnosti> nehnlist = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {

        try {
            Database database = new Database("kc014100db", "kc014100", "jgowihez");
            con = database.getConnection();
            nastavComboBoxy(database, cbxServer, dbtypeNehnutCbx, cbxLokalita);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs1 = null;
        try {
            rs1 = this.getAllNehnutelnosti();
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cbxTransakcia.getItems().add("Predaj");
        cbxTransakcia.getItems().add("Kúpa");
        cbxTransakcia.getItems().add("Prenájom");

    }

    static void nastavComboBoxy(Database database, ComboBox<String> cbxServer, ComboBox<String> dbtypeNehnutCbx, ComboBox<String> cbxLokalita) throws SQLException {
        String[] columns3 = null;
        Object[] params3 = null;
        ResultSet rs = null;
        rs = database.select("servre",columns3,params3);
        while (rs.next()) {
            cbxServer.getItems().addAll(rs.getString("nazov"));

        }
        rs = database.select("typ_nehnutelnosti",columns3,params3);
        while (rs.next()) {
            // Now add the comboBox addAll statement
            dbtypeNehnutCbx.getItems().addAll(rs.getString("nazov"));
        }
        rs = database.select("lokalita",columns3,params3);
        while (rs.next()) {
            // Now add the comboBox addAll statement
            cbxLokalita.getItems().addAll(rs.getString("nazov"));
        }
    }


    public void handleWindowEvent(WindowEvent event, EventType<? extends WindowEvent> type) {
        if (type == WindowEvent.WINDOW_CLOSE_REQUEST) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }


    public void ActionUrl (ActionEvent actionEvent){
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        Hyperlink selectedItem = table.getSelectionModel().getSelectedItem().getLinkserver();
        String completeLink = selectedItem.getText();
        /*webEngine.load(String.valueOf(selectedItem));*/
        try {

            Desktop.getDesktop().browse(new URL(completeLink).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void ActionSortByServer (ActionEvent actionEvent){
        ResultSet rs1 = null;
        indexServer = Integer.toString(this.cbxServer.getSelectionModel().getSelectedIndex()+1);

        try {
            rs1 = this.getbyServer(indexServer);
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    private void naplnZoznam (ResultSet rs2) throws SQLException {
        ObservableList<ModelNehnutelnosti> nehnlist = FXCollections.observableArrayList();
        try {

            while (rs2.next()){
                nehnlist.add(new ModelNehnutelnosti(
                        rs2.getInt("id"),
                        rs2.getString("nazov"),
                        rs2.getInt("idnaserveri"),
                        rs2.getString("linkserver"),
                        rs2.getString("aktualizacia"),
                        rs2.getString("lokalita"),
                        rs2.getString("druh_transakcie"),
                        rs2.getString("nazovTypu"),
                        rs2.getDouble("cena"),
                        rs2.getDouble("cenam2"),
                        rs2.getString("titulka"),
                        rs2.getInt("lokalita_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("nazov"));
        column3.setCellValueFactory(new PropertyValueFactory<>("idnaserveri"));
        column4.setCellValueFactory(new PropertyValueFactory<>("linkserver"));

        column5.setCellValueFactory(new PropertyValueFactory<>("aktualizacia"));
        column6.setCellValueFactory(new PropertyValueFactory<>("lokalita"));
        column7.setCellValueFactory(new PropertyValueFactory<>("druh_transakcie"));
        column8.setCellValueFactory(new PropertyValueFactory<>("nazovTypu"));
        column9.setCellValueFactory(new PropertyValueFactory<>("cena"));
        column10.setCellValueFactory(new PropertyValueFactory<>("cenam2"));
        column11.setCellValueFactory(new PropertyValueFactory<>("titulka"));

        table.setItems(nehnlist);
        Double max = Double.MIN_VALUE;
        Double maxm2 = Double.MIN_VALUE;
        Double min = Double.MAX_VALUE;
        Double minm2 = Double.MAX_VALUE;
        Double sucet = 0.00;
        Double sucetm2 = 0.00;
        Integer pocet = 0;
        Integer pocetm2 = 0;
        Double average,averagem2 = 0.00;
        rs2.first();
        try{
            while (rs2.next()){
                if (rs2.getDouble("cena")>0){
                    sucet = sucet + rs2.getDouble("cena");
                    pocet ++;
                    if(rs2.getDouble("cena") > max){
                        max = rs2.getDouble("cena");
                    }
                    if(rs2.getDouble("cena") < min) {
                        min = rs2.getDouble("cena");
                    }
                }
                if (rs2.getDouble("cenam2")>0){
                    sucetm2 = sucetm2 + rs2.getDouble("cenam2");
                    pocetm2 ++;
                    if(rs2.getDouble("cenam2") > maxm2){
                        maxm2 = rs2.getDouble("cenam2");
                    }
                    if(rs2.getDouble("cenam2") < minm2) {
                        minm2 = rs2.getDouble("cenam2");
                    }
                }
            }
            /* Zistenie poctu zaznamov v ResultSet*/
            rs2.beforeFirst();
            boolean b = rs2.last();
            Integer countRecords = rs2.getRow();
            tfCountRecords.setText(Integer.toString(countRecords));
            //
            average = sucet/pocet;
            averagem2 = sucetm2/pocetm2;
            tfAverageValue.setText(Double.toString(Math.round(average)));
            tfMaxValue.setText(Double.toString(max));
            tfMinValue.setText(Double.toString(min));

            tfAVGM2.setText(Double.toString(Math.round(averagem2)));
            tfMaxM2.setText(Double.toString(maxm2));
            tfMinM2.setText(Double.toString(minm2));


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private ResultSet getAllNehnutelnosti() throws SQLException {
        PreparedStatement query = con.prepareStatement
                ("SELECT a.id,b.nazov,a.idnaserveri,a.linkserver,a.aktualizacia,a.lokalita,a.lokalita_id,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka,a.cenam2,a.uzitplocha " +
                        "FROM nehnutelnosti a " +
                        "INNER JOIN servre b ON a.server_id = b.id " +
                        "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id ");
        return query.executeQuery();
    }

    private ResultSet getbyServer(String Id) throws SQLException {
        PreparedStatement query = con.prepareStatement
                ("SELECT a.id,b.nazov,a.idnaserveri,a.linkserver,a.aktualizacia,a.lokalita,a.lokalita_id,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka,a.cenam2,a.uzitplocha " +
                        "FROM nehnutelnosti a " +
                        "INNER JOIN servre b ON a.server_id = b.id " +
                        "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id " +
                        "WHERE a.server_id = ?");
        query.setString(1, Id);
        return  query.executeQuery();
    }

    private ResultSet getbyServerLokalitaTyp(String IdServer,String IdTypProperty,String IdTransakcia,String IdLokalita) throws SQLException {
        PreparedStatement query = con.prepareStatement
                ("SELECT a.id,b.nazov,a.idnaserveri,a.linkserver,a.aktualizacia,a.lokalita,a.lokalita_id,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka,a.cenam2,a.uzitplocha " +
                        "FROM nehnutelnosti a " +
                        "INNER JOIN servre b ON a.server_id = b.id " +
                        "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id " +
                        "WHERE a.server_id = ? AND a.typ_nehnutelnosti_id = ? AND a.druh_transakcie = ? AND a.lokalita_id = ?");
        query.setString(1, IdServer);
        query.setString(2, IdTypProperty);
        query.setString(3, IdTransakcia);
        query.setString(4, IdLokalita);
        return query.executeQuery();
    }


    public void ActionDelete(ActionEvent actionEvent) throws SQLException, IOException {
        String sprava = "";
        /* vyber ID parametra z oznaceneho zaznamu*/
        Integer selectedItem = table.getSelectionModel().getSelectedItem().getId();
        /* vymazanie zaznamu podla ID*/
        PreparedStatement query = con.prepareStatement("DELETE FROM nehnutelnosti WHERE id = ?");
        query.setString(1, Integer.toString(selectedItem));
        Boolean result = query.execute();
        if (!result){
            sprava="Zaznam cislo "+selectedItem.toString()+" bol vymazany";
        }else{
            sprava="Zaznam cislo "+selectedItem.toString()+" nebol vymazany";
        }
        /* Otvorenie modalneho okna */
        winPotvrdzujucaSprava(actionEvent,sprava);
        /*znovu nacitanie databazy*/
        ResultSet rs1 = null;
        try {
            rs1 = this.getAllNehnutelnosti();
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActionSortTypeProperty(ActionEvent actionEvent) {
        podmTypeProperty="";
        String hodnota = this.dbtypeNehnutCbx.getValue();
        Parametre.setTyp_nehnutelnosti_id(hodnota+" /");
        podmTypeProperty = Integer.toString(Parametre.getTyp_nehnutelnosti_id());
    }

    public void ActionSortTransaction(ActionEvent actionEvent) {
        podmTransaction="";
        String hodnota = this.cbxTransakcia.getValue().trim();
        podmTransaction = hodnota;
    }


    public void ActionRunSort(ActionEvent actionEvent) {
        ResultSet rs1 = null;
        try {
            rs1 = this.getbyServerLokalitaTyp(indexServer,podmTypeProperty,podmTransaction,podmLokalita);
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActionLokalita(ActionEvent actionEvent) {
        podmLokalita="";
        String hodnota = this.cbxLokalita.getValue();
        Parametre.setLokalita_id(hodnota);
        podmLokalita = Integer.toString(Parametre.getLokalita_id());
    }

    public void ActionEdit(ActionEvent actionEvent) {

    }

    public void ActionDeletetFilter(ActionEvent actionEvent) {
        ResultSet rs1 = null;
        try {
            rs1 = this.getAllNehnutelnosti();
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}