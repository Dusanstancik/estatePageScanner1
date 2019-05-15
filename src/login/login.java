package login;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class login {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Button btn1 = new Button("btn1");
        Button btn2 = new Button("btn2");
        Button btn3 = new Button("btn3");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(25);
        flowPane.setMargin(btn1, new Insets(20,0,20,20));

        ObservableList list = flowPane.getChildren();
        list.addAll(btn1,btn2,btn3);

        Scene scene = new Scene(flowPane);
        primaryStage.setTitle("Priklad");
        primaryStage.setScene(scene);
        primaryStage.show();


        //   Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //   root.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        //   primaryStage.setTitle("Hello World");
        //   primaryStage.setScene(new Scene(root,300,400));
        //   primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
