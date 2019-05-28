/*
 * Copyright (c) 2019. Dušan Stančík
 */

package Controllers;

import DB.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static Controllers.ZoznamNehnutelnosti.nastavComboBoxy;

public class EditNehnutelnost implements Initializable {
    @FXML
    public TextField cena;
    @FXML
    public TextArea popis;
    @FXML
    public TextField titulka;
    @FXML
    public TextField priemerCena;
    @FXML
    public Label nazovServera;
    @FXML
    public Label nazovTypu;
    @FXML
    public Label typTransakcie;
    @FXML
    public Label lokalita;
    @FXML
    public Label ulica;
    @FXML
    public Button cancel;
    @FXML
    public Button save;
    @FXML
    public Label uzitplocha;
    @FXML
    private ComboBox<String> cbxTransakcia;
    @FXML
    private ComboBox<String> dbtypeNehnutCbx;
    @FXML
    private ComboBox<String> cbxServer;
    @FXML
    public ComboBox<String> cbxLokalita;

    Connection con;
    private String IdNehnutelnosti;
    ResultSet rs1;
    public String ID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Database database = new Database("kc014100db", "kc014100", "jgowihez");
            con = database.getConnection();
            /*Controllers.ZoznamNehnutelnosti.nastavComboBoxy(database, cbxServer, dbtypeNehnutCbx, cbxLokalita);*/


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void getIdNehnutelnosti (String IdNehnutelnosti){
        ID = IdNehnutelnosti;
        Database database = null;
        rs1 = null;
        try {
            PreparedStatement query = con.prepareStatement
                    ("SELECT a.id,b.nazov,a.idnaserveri,a.linkserver,a.aktualizacia,a.lokalita,a.ulica,a.lokalita_id,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka,a.cenam2,a.uzitplocha,a.popis " +
                            "FROM nehnutelnosti a " +
                            "INNER JOIN servre b ON a.server_id = b.id " +
                            "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id " +
                            "WHERE a.id = ?");
            query.setString(1, ID);
            rs1 =  query.executeQuery();
            rs1.first();
            try {
                cena.setText(Double.toString(rs1.getDouble("cena")));
                priemerCena.setText(Double.toString(rs1.getDouble("cenam2")));
                titulka.setText(rs1.getString("titulka"));
                popis.setWrapText(true);
                popis.setText(rs1.getString("popis"));
                nazovTypu.setText(rs1.getString("nazovTypu"));
                nazovServera.setText(rs1.getString("nazov"));
                typTransakcie.setText(rs1.getString("druh_transakcie"));
                lokalita.setText(rs1.getString("lokalita"));
                ulica.setText(rs1.getString("ulica"));
                uzitplocha.setText(rs1.getString("uzitplocha"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void ActionCancel(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) cancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    public void ActionSave (ActionEvent actionEvent){
        PreparedStatement query = null;
        try {
            query = con.prepareStatement (
                    "UPDATE nehnutelnosti SET cena = ? , cenam2 = ?, titulka = ?, popis = ? " +
                            "WHERE nehnutelnosti.id = ?");
            query.setString(1, cena.getText());
            query.setString(2, priemerCena.getText());
            query.setString(3, titulka.getText());
            query.setString(4, popis.getText());
            query.setString(5, ID);
            Integer pocet = query.executeUpdate();
            this.ActionCancel(actionEvent);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void ActionPrepocitaj (ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Oznam");
        alert.setHeaderText("Zmena hodnôt v poli Cena");
        alert.setContentText("Prajete si prepočítať Cenu na m2");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Double averagem2 = Double.parseDouble(cena.getText())/Double.parseDouble(uzitplocha.getText());
            Double roundedAverage = Math.round(averagem2*100)/100D;
            priemerCena.setText(Double.toString(roundedAverage));
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

}
