/*
 * Copyright (c) 2019. Ondrej Lajzo
 */

package splashscreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {

    @FXML AnchorPane ap;

    class ShowSplashScreen extends Thread{

        @Override

        public void run(){

            try {

                Thread.sleep(5000);

                Platform.runLater(() -> {

                    Stage stage = new Stage();

                    Parent root = null;

                    try {

                        root = FXMLLoader.load(getClass().getResource("SecondScene.fxml"));

                    } catch (IOException ex) {

                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);

                    }

                    Scene scene = new Scene(root);

                    stage.setScene(scene);

                    stage.show();

                    ap.getScene().getWindow().hide();

                });

            } catch (InterruptedException ex) {

                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        new ShowSplashScreen().start();

    }

}
