package ViewerServer;

import java.net.URL;
import java.util.ResourceBundle;

import ControllerServer.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServerViewerController {
    private Server server;
    private Thread myThready;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button restart;

    @FXML
    private TextArea serverMessage;



    @FXML
    void startServer(ActionEvent event) {

         myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                if(server == null) {
                    server = new Server();
                    server.startServer();
                } else {
                    System.out.println("YYYY");
                }
            }
        });
        myThready.start();
    }

    @FXML
    void initialize() {
        /*
        assert restart != null : "fx:id=\"restart\" was not injected: check your FXML file 'ServerViewer.fxml'.";
        assert serverMessage != null : "fx:id=\"serverMessage\" was not injected: check your FXML file 'ServerViewer.fxml'.";
        assert shutdown != null : "fx:id=\"shutdown\" was not injected: check your FXML file 'ServerViewer.fxml'.";
*/
    }

    public void stopServer(ActionEvent actionEvent) {
        if (server == null){
            System.out.println("OPOPO");
        }else {

            server.setStopServ(true);
            server.stopServer();
myThready.interrupt();


        }
    }
}
