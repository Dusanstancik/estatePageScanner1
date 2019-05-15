package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controler {

    @FXML
    private Text messageLogin;

    @FXML
    public void loginAction(ActionEvent actionEvent) {
        messageLogin.setText("Spracovane cez jazyk Java");
    }
}
