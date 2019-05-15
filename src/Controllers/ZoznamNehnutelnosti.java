package Controllers;



import DB.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import Models.ModelNehnutelnosti;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


public class ZoznamNehnutelnosti implements Initializable {
    public ComboBox cbxServer;
    public Button sortByServer;
    public Button btnDelete;
    Database database;
    Connection con;
    @FXML
    public TableColumn <ModelNehnutelnosti,String> column1;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column2;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column3;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column4;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column5;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column6;
    @FXML
    public TableColumn <ModelNehnutelnosti,String>column7;
    @FXML
    public TableView <ModelNehnutelnosti> table;

    ObservableList<ModelNehnutelnosti> nehnlist = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {

        try {
            database = new Database("kc014100db", "kc014100", "jgowihez");
            con = database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columns3 = null;
        Object[] params3 = null;
        ResultSet rs = null;
        try {
            rs = database.select("servre",columns3,params3);
            while (rs.next()) {
                cbxServer.getItems().addAll(rs.getString("nazov"));

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        ResultSet rs1 = null;
        try {
            rs1 = this.getAllNehnutelnosti();
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


     }



    public void ActionSortByServer (ActionEvent actionEvent){
        ResultSet rs1 = null;
        String indexServer = Integer.toString(this.cbxServer.getSelectionModel().getSelectedIndex()+1);
        try {
            rs1 = this.getbyServer(indexServer);
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    private void naplnZoznam (ResultSet rs2) {
        ObservableList<ModelNehnutelnosti> nehnlist = FXCollections.observableArrayList();
        try {

            while (rs2.next()){
                nehnlist.add(new ModelNehnutelnosti(
                        rs2.getInt("id"),
                        rs2.getString("nazov"),
                        rs2.getInt("idnaserveri"),
                        rs2.getString("druh_transakcie"),
                        rs2.getString("nazovTypu"),
                        rs2.getDouble("cena"),
                        rs2.getString("titulka")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("nazov"));
        column3.setCellValueFactory(new PropertyValueFactory<>("idnaserveri"));
        column4.setCellValueFactory(new PropertyValueFactory<>("druh_transakcie"));
        column5.setCellValueFactory(new PropertyValueFactory<>("nazovTypu"));
        column6.setCellValueFactory(new PropertyValueFactory<>("cena"));
        column7.setCellValueFactory(new PropertyValueFactory<>("titulka"));

        table.setItems(nehnlist);
    }


    private ResultSet getAllNehnutelnosti() throws SQLException {
        PreparedStatement query = con.prepareStatement("SELECT a.id,b.nazov,a.idnaserveri,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka " +
                "FROM nehnutelnosti a " +
                "INNER JOIN servre b ON a.server_id = b.id " +
                "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id ");
        return query.executeQuery();
    }

    private ResultSet getbyServer(String Id) throws SQLException {
        PreparedStatement query = con.prepareStatement("SELECT a.id,b.nazov,a.idnaserveri,a.druh_transakcie,c.nazov AS nazovTypu,a.cena,a.titulka " +
                "FROM nehnutelnosti a " +
                "INNER JOIN servre b ON a.server_id = b.id " +
                "INNER JOIN typ_nehnutelnosti c ON a.typ_nehnutelnosti_id = c.id " +
                "WHERE a.server_id = ?");
        query.setString(1, Id);
        return  query.executeQuery();
    }

    private void getbyServerLokalitaTyp(String IdServer,String IdLokalita,String IdTyp) throws SQLException {
        PreparedStatement query = con.prepareStatement("SELECT a.id,b.nazov,a.cena, from nehnutelnosti a, servre b, lokalita c,typ_nehnutelnosti d WHERE a.server_id = ? AND a.lokalita_id = ? AND a.typ_nehnutelnosti_id = ?");
        query.setString(1, IdServer);
        query.setString(2, IdLokalita);
        query.setString(3, IdTyp);
        ResultSet rs = query.executeQuery();
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
        Locale locale2 = new Locale("English","EN");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../windows/messagewithOK.fxml"), ResourceBundle.getBundle("string",locale2));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage2 = new Stage();
        /*posielanie parametrov do okna*/
        MessagewithOK messagewithOK = (MessagewithOK)fxmlLoader.getController();
        messagewithOK.getMessage(sprava);
        stage2.setTitle("Informacia o vykonaní operácie");
        stage2.setScene(new Scene(root2, 400 , 99));
        stage2.initModality(Modality.WINDOW_MODAL);
        stage2.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage2.show();
        /*znovu nacitanie databazy*/
        ResultSet rs1 = null;
        try {
            rs1 = this.getAllNehnutelnosti();
            this.naplnZoznam(rs1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
