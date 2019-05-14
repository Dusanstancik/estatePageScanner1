package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagewithOK implements Initializable {
    @FXML
    public Button OK;
    public TextArea message;
    public Label lblMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void getMessage(String message){
        lblMessage.setText(message);

    }

    public void OK(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) OK.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
