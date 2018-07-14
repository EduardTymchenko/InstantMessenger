package ViewerServer;

import ControllerServer.Server;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ServerWinController {
    //verWindowController serverWindowController;
    private Server server;
    {
            server = new Server();
    }
    @FXML
    void startServer(ActionEvent event) {
        new Thread(() -> server.startServer()).start();
        sendMessage("Start Server");
    }

    @FXML
    public void stopServer(ActionEvent actionEvent) {
        server.stopServer();
    }
    @FXML
    public void messServer(ActionEvent actionEvent) {

    }
    @FXML
    private TextArea textArea;
    @FXML
    public TextArea ta  ;

    public TextArea getTa() {
        return ta;
    }

    public  void sendMessage(final String message) {
        Platform.runLater(new Runnable() {//GUI поток
            @Override
            public void run() {
                ta.appendText(message + "\n");
            }
        });
    }
}
