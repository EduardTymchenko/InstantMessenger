package ViewerServer;

import ControllerServer.Server;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerWindow extends Application {
    private  Server server = new Server();


    public static void main(String[] args) {
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ServerViewer.fxml"));
        primaryStage.setTitle("Server");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    @FXML
    void startServer(ActionEvent event) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.startServer();
            }
        }).start();
    }


    public void stopServer(ActionEvent actionEvent) {
            server.stopServer();
    }
}



