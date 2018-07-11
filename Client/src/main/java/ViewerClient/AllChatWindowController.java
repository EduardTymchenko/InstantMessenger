package ViewerClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import static ViewerClient.ClientWindow.clientController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AllChatWindowController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea textAllChatOut;
    @FXML
    private TextArea textAllChatIn;

    public AllChatWindowController()  {
    }

    @FXML
    void addChatButton(ActionEvent event)  {

    }

    @FXML
    void sendMsgForm(ActionEvent event) {
        try {
            String textOut = textAllChatOut.getText();
            if (!textOut.equals("")) {
                clientController.sendMsg(textOut);
                textAllChatOut.clear();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMessageServer(String msg) {
            textAllChatIn.appendText(msg + "\n");
    }

    @FXML
    void initialize() {
        // Set class to controller
       clientController.setAllChatWindowController(this);

        assert textAllChatOut != null : "fx:id=\"textAllChatOut\" was not injected: check your FXML file 'AllChat.fxml'.";
        assert textAllChatIn != null : "fx:id=\"textAllChatIn\" was not injected: check your FXML file 'AllChat.fxml'.";

    }
// For Login.fxml ******************************************


}
