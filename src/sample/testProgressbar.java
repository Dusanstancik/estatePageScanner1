package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class testProgressbar extends Application {

    @Override
    public void start(Stage primaryStage) {

        //Nodes
        Text txtState = new Text();
        txtState.setFont(Font.font(18));
        txtState.setFill(Color.BLUE);
        TextField fldSec = new TextField();
        fldSec.setPromptText("Insert Seconds Here");

        //ProgressBar
        ProgressBar pbar = new ProgressBar(0);
        pbar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    txtState.setText("Calculating Time");
                }
                else{
                    txtState.setText("In Progress");

                }
            }
        });

        pbar.progressProperty().addListener(new ChangeListener<Number>() {


            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {

                if(t1.doubleValue()==1){
                    txtState.setText("Work Done");
                    txtState.setFill(Color.GREEN);
                }
            }

        });

        //PrograssIndicator
        ProgressIndicator pind = new ProgressIndicator(0);
        pind.indeterminateProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if(t1){
                    txtState.setText("Calculating Time");
                    txtState.setFill(Color.BLUE);
                }
                else{
                    txtState.setText("In Progress");

                }
            }
        });

        //Start Button
        Button btnStart = new Button();
        btnStart.setText("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                if(fldSec.getText().isEmpty() || Double.isNaN(Double.parseDouble(fldSec.getText()))){
                    txtState.setText("Introduce a NUMBER in the TextField");
                    txtState.setFill(Color.RED);
                }
                else{
                    //Create new Task and Thread -  Bind Progress Property to Task Progress
                    Task task = taskCreator(Integer.parseInt(fldSec.getText()));
                    pbar.progressProperty().unbind();
                    pbar.progressProperty().bind(task.progressProperty());
                    pind.progressProperty().unbind();
                    pind.progressProperty().bind(task.progressProperty());
                    new Thread(task).start();
                }
            }
        });

        //Layout
        HBox hbox = new HBox(15);
        hbox.getChildren().addAll(btnStart, fldSec, pbar, pind,txtState);
        hbox.setPadding(new Insets(50));

        Group root = new Group();
        root.getChildren().addAll(hbox);

        Scene scene = new Scene(root, 800, 150);

        primaryStage.setTitle("JavaFX8 - ProgressBar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Create a New Task
    private Task taskCreator(int seconds){
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds;i++){
                    Thread.sleep(1000);
                    updateProgress(i+1, seconds);

                }
                return true;
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
    }

}