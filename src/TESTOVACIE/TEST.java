package TESTOVACIE;

import javafx.application.Application;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class TEST extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        String cenaString ="cena dohodou   1235";
        Boolean nasiel = cenaString.contains("cena dohodou");
        System.out.println(nasiel);




    }



    public static void main(String[] args) {
        launch(args);
    }
}
