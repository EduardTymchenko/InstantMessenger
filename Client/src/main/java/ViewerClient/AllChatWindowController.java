package ViewerClient;

import ModelServer.CommandChat;
import ModelServer.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static ViewerClient.ClientWindow.clientController;
import static ViewerClient.ClientWindow.commandChat;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AllChatWindowController {

    @FXML
    private AnchorPane allChatID;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextArea textAllChatOut;
    @FXML
    private TextArea textAllChatIn;
    @FXML
    private Text idUser;
    @FXML
    private ListView<String> idactiveUsers;


    private ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    void sendMsgForm(ActionEvent event) {
        Message allChatMessage = new Message();
        try {
            String textOut = textAllChatOut.getText();
            if (!textOut.equals("")) {
                allChatMessage.setCommandMess(CommandChat.MSG);
                allChatMessage.setBodyMess(textOut);
                clientController.sendMsg(allChatMessage);
                textAllChatOut.clear();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getMessageServer(String msg) {
            textAllChatIn.appendText(msg + "\n");
    }
    public void getUserActive (ArrayList<String> usersList){
        observableList.setAll(usersList);
        idactiveUsers.setItems(observableList);
    }

    @FXML
    void initialize() {
        // Set class to controller
       clientController.setAllChatWindowController(this);

        assert textAllChatOut != null : "fx:id=\"textAllChatOut\" was not injected: check your FXML file 'AllChat.fxml'.";
        assert textAllChatIn != null : "fx:id=\"textAllChatIn\" was not injected: check your FXML file 'AllChat.fxml'.";
       // assert activeUsers != null : "fx:id=\"activeUsers\" was not injected: check your FXML file 'AllChat.fxml'.";
    }

    public void setIdUser(String idUserOk) {
        idUser.setText(idUserOk);
    }

    @FXML
    public void quitAction(ActionEvent event) {
        Message exitUser = new Message();
        exitUser.setCommandMess(CommandChat.EXIT_USERS);
        clientController.sendMsg(exitUser);
        clientController.closeSocketClient();
        System.exit(0);

    }

    @FXML
    void changePassworAction(ActionEvent event) throws IOException {
        Stage stageAllChat = (Stage) allChatID.getScene().getWindow();
        Parent changePassword = FXMLLoader.load(getClass().getResource("/ChangePassword.fxml"));
        Stage changePasswordStage = new Stage();
        changePasswordStage.setScene(new Scene(changePassword));
        changePasswordStage.setTitle("Change user password");
        changePasswordStage.initOwner(stageAllChat);
        changePasswordStage.initModality(Modality.WINDOW_MODAL);
        changePasswordStage.setResizable(false);
        changePasswordStage.showAndWait();


    }



}
