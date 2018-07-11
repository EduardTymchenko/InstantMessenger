package ViewerClient;

import ControllerClient.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientWindow extends Application {

    // public static Stage parentWindow;
    static ClientController clientController;

    public static void main(String[] args) {


          //  clientController = new ClientController();

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
    /*
    public static void errorConnectServer(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Server connection error.\nTry later.");
        alert.showAndWait();
    }
    */

