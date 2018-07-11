package ViewerServer;

import ControllerServer.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerWindow extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ServerViewer.fxml"));
        //TextArea ta=(TextArea) root.lookup("#ta");
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }




}
    /*add textArea to your scene somewhere in the start method
    public  void printlnInServer(final String s){
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                ta.setText("Heloy"+"\n");
                System.out.println(s);//for echo if you want
            }
        });
    }*/




