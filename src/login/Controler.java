/*
 * Copyright (c) 2019. Dušan Stančík
 */

package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class Controler {

    @FXML
    private Text messageLogin;

    public Controler(Text messageLogin) {
        this.messageLogin = messageLogin;
    }

    @FXML
    public void loginAction(ActionEvent actionEvent) {
        messageLogin.setText("Spracovane cez jazyk Java");
    }
}
