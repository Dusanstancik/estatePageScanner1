/*
 * Copyright (c) 2019. Dušan Stančík
 */

package sample;

import Controllers.EditNehnutelnost;
import Controllers.MessagewithOK;
import DB.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Windows {
    static Database database;

    public static void winZoznamNehnutelnosti() throws IOException {
        Locale locale = new Locale("English","EN");
        FXMLLoader fxmlLoader = new FXMLLoader(Windows.class.getResource("../windows/zoznamNehnutelnosti.fxml"), ResourceBundle.getBundle("string",locale));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Zoznam Nehnutelnosti");
        stage.setScene(new Scene(root, 1300, 900));
        stage.show();
       /*       stage.setOnCloseRequest(event -> {
       /*           try {
       /*               database.getConnection().close();
       /*           } catch (SQLException e) {
       /*               e.printStackTrace();
       /*           }
       /*       });  */

    }
    public static void winEditNehnutelnost(ActionEvent actionEvent,String IdNehnutelnosti) throws IOException {
        Locale locale = new Locale("English","EN");
        FXMLLoader fxmlLoader = new FXMLLoader(Windows.class.getResource("../windows/editNehnutelnost.fxml"), ResourceBundle.getBundle("string",locale));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        /*posielanie IdNehnutelnosti do okna*/
        EditNehnutelnost editNehnutelnost = (EditNehnutelnost)fxmlLoader.getController();
        editNehnutelnost.getIdNehnutelnosti(IdNehnutelnosti);
        stage.setTitle("Úprava Nehnuteľnosti");
        stage.setScene(new Scene(root, 800, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();


    }
    public static void winPotvrdzujucaSprava(ActionEvent actionEvent,String message) throws IOException {
        Locale locale = new Locale("English","EN");
        FXMLLoader fxmlLoader = new FXMLLoader(Windows.class.getResource("../windows/messagewithOK.fxml"), ResourceBundle.getBundle("string",locale));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        /*posielanie parametrov do okna*/
        MessagewithOK messagewithOK = (MessagewithOK)fxmlLoader.getController();
        messagewithOK.getMessage(message);
        stage.setTitle("Informacia o vykonaní operácie");
        stage.setScene(new Scene(root, 400 , 99));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }
}
