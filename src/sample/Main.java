package sample;

import DB.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        try {
            /*Class.forName("com.mysql.jdbc.Driver");*/
            Database database = new Database("kc014100db", "kc014100", "jgowihez");
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            Object[] firstParams = {0,"Dušan", "Stančík", "admin", "12345",timeStamp,timeStamp};
            int uspech1 = database.insert("user", firstParams);
            System.out.println("Uložení uživatele = "+uspech1);

            String[] columns = {"meno","update_at"};
            Object[] params = {"Galileo", timeStamp,6};
            int uspech4 = database.update("user", columns, "iduser = ?", params);
            System.out.println("Přepsání uživate = "+uspech4);

        } catch (SQLException ex) {
            System.out.println("error - "+ex.getMessage());
        }
        Document doc = Jsoup.connect("http://www.topreality.sk/id6824075").get();
        Element content = doc.getElementById("content");
        String title = doc.title();
        Elements cena = doc.getElementsByClass("price");
        Elements cena1=cena.select("a");
        Elements textcena = cena1.select("title");

        System.out.println("Class price = "+cena);
        System.out.println("cena = "+cena1);
        System.out.println("cena = "+textcena);

    }





    public static void main(String[] args) {
        launch(args);
    }
}
