package ViewerServer;

import ControllerServer.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.xml.bind.JAXBException;

public class ServerWinController {
    private Server server = new Server();

    @FXML
    void startServer(ActionEvent event) {
        new Thread(() -> {
            try {
                server.startServer();

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }).start();
        server.setServerWinController(this);
        sendMessage("Start Server");
    }

    @FXML
    public void stopServer(ActionEvent actionEvent) {
        server.stopServer();
    }


    @FXML
    private TextArea textArea;



    public  void sendMessage(String message) {
        textArea.appendText(message + "\n");
    }
}
