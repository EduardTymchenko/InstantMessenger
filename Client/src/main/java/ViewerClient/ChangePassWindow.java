package ViewerClient;

import ModelServer.Message;
import ModelServer.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;



import static ViewerClient.ClientWindow.clientController;

public class ChangePassWindow {
    @FXML
    private Button cancelButtonId;
    @FXML
    private TextField loginForm;
    @FXML
    private PasswordField repeatPassField;
    @FXML
    private Text formCheck;
    @FXML
    private Text passwordTextField;
    @FXML
    private Text repeatPassText;




    @FXML
    private PasswordField passwordForm;

    @FXML
    void cancelButtonAction(ActionEvent event) {
        Stage cancelChage = (Stage) cancelButtonId.getScene().getWindow();
        cancelChage.close();
    }
    @FXML
    void changeButtonActive(ActionEvent event) {
       if (checkForm()){
           System.out.println("@@@@@@@@");
       }
    }
    public void setUserName (String nameUser){
        loginForm.setText(nameUser);
    }
    private boolean checkForm() {
        Message changePassMessage = new Message();
        User changeUser = changePassMessage.setUser(new User());
        if (passwordForm.getText().length() >= 1) {
            passwordTextField.setFill(Color.BLACK);
            //+в вообщение!!!!
            changeUser.setPasswordUser(passwordForm.getText());
        } else {
            formCheck.setVisible(true);
            passwordForm.setTooltip(new Tooltip("Password length < 1"));
            passwordTextField.setFill(Color.RED);
        }
        if ((repeatPassField.getText().equals(passwordForm.getText())) && (repeatPassField.getText().length() >= 1)) {
            repeatPassText.setFill(Color.BLACK);
        } else {
            formCheck.setVisible(true);
            repeatPassField.setTooltip(new Tooltip("Passwords do not match"));
            repeatPassText.setFill(Color.RED);

        }
        if ((passwordTextField.getFill() == Color.BLACK) &
                (repeatPassText.getFill() == Color.BLACK)) {
            formCheck.setVisible(false);
            return true;
        } else {
            return false;
        }

    }
    @FXML
    void initialize() {
        // Set class to controller
        clientController.setChangePassWindow(this);
        setUserName(clientController.getUser().getNameUser());
    }
}
