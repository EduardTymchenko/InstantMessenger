package ViewerClient;

import ControllerClient.ClientController;
import ModelServer.CommandChat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientWindow extends Application {

    // public static Stage parentWindow;
    static ClientController clientController;
    static CommandChat commandChat;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //parentWindow = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/Login.fxml"));
        primaryStage.setTitle("Login to Messenger");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        }
}

