package ViewerClient;

import java.io.IOException;
import java.net.ConnectException;
import ControllerClient.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static ViewerClient.ClientWindow.clientController;

public class LoginWindowController {
    private boolean newUser = false;

    @FXML
    private Text passwordTextField;
    @FXML
    private TextField loginForm;
    @FXML
    private Button closeButton;
    @FXML
    private PasswordField repeatPassField;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordForm;
    @FXML
    private Text formCheck;
    @FXML
    private Button newUserButton;
    @FXML
    private Text repeatPassText;
    @FXML
    private Text loginWindowText;
    @FXML
    private Text loginTextField;

    @FXML
    void loginButtonActive(ActionEvent event) throws IOException {

        Stage stageLogin = (Stage) loginButton.getScene().getWindow();
        // do what you have to do проверка

        if (loginForm.getText().length() < 1 || loginForm.getText().equals(" ")) {
            formCheck.setVisible(true);
            loginForm.setTooltip(new Tooltip("Login length < 1 or space"));
            loginTextField.setFill(Color.RED);
        } else {
            loginTextField.setFill(Color.BLACK);
            //+в вообщение!!!!
        }
        if (passwordForm.getText().length() < 1) {
            formCheck.setVisible(true);
            passwordForm.setTooltip(new Tooltip("Password length < 1"));
            passwordTextField.setFill(Color.RED);
        } else {
            passwordTextField.setFill(Color.BLACK);
            //+в вообщение!!!!
        }
        if (newUser) {
            if (!(repeatPassField.getText().equals(passwordForm.getText())) || (repeatPassField.getText().length() < 1)) {
                formCheck.setVisible(true);
                repeatPassField.setTooltip(new Tooltip("Passwords do not match"));
                repeatPassText.setFill(Color.RED);

            } else {
                repeatPassText.setFill(Color.BLACK);
                //+в вообщение!!!!
            }
        } else {
            repeatPassText.setFill(Color.BLACK);
        }
        if ((loginTextField.getFill() == Color.BLACK) &
                (passwordTextField.getFill() == Color.BLACK) &
                (repeatPassText.getFill() == Color.BLACK)) {
            formCheck.setVisible(false);

            try {
                clientController = new ClientController();
                clientController.sendMsg("Hi!!!");
                showAllChatWindow();
                stageLogin.close();
            } catch (ConnectException e) {
                //e.printStackTrace();
                errorConnectServer();
            }
        }
    }

    @FXML
    void newUserAction(ActionEvent event) {
        if (!newUser) {
            loginWindowText.setText("Add new User");
            repeatPassText.setVisible(true);
            repeatPassField.setVisible(true);
            newUserButton.setText("< Back");
            newUser = true;
        } else {
            loginWindowText.setText("Login to chat");
            repeatPassText.setVisible(false);
            repeatPassField.setVisible(false);
            newUserButton.setText("New User");
            newUser = false;
        }
    }

    @FXML
    void closeButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void showAllChatWindow() throws IOException {
        Parent allChat = FXMLLoader.load(getClass().getResource("/AllChat.fxml"));
        Stage allChatStage = new Stage();
        allChatStage.setTitle("Messenger Client");
        allChatStage.setScene(new Scene(allChat));
        allChatStage.setMinHeight(300);
        allChatStage.setMinWidth(400);
        allChatStage.show();
        allChatStage.setOnCloseRequest(event -> System.exit(0));
    }
    @FXML
    private void errorConnectServer(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error connection");
        alert.setHeaderText(null);
        alert.setContentText("Server connection error.\nTry later.");
        alert.showAndWait();
    }
}


