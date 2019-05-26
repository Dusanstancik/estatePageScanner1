/*
 * Copyright (c) 2019. Ondrej Lajzo
 */

package splashscreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SplashScreen extends Application {

    @Override

    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}