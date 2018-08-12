package ControllerClient;

import ModelServer.CommandChat;
import ModelServer.Message;
import ModelServer.ParsingXML;
import ModelServer.User;
import ViewerClient.AllChatWindowController;
import ViewerClient.ChangePassWindow;
import ViewerClient.LoginWindowController;
import javafx.application.Platform;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.Socket;

public class ClientController {
    private AllChatWindowController allChatWindowController;
    private LoginWindowController loginWindowController;
    private ChangePassWindow changePassWindow;
    private Socket clSocket;
    private final int portServer = 8888;
    private final String ipServer = "localhost";
    private BufferedReader in;
    private BufferedWriter out;
    private ParsingXML parsingXML = new ParsingXML();
    private Message inputMess = new Message();
    private User user;

    public User getUser() {
        return user;
    }

    public void setChangePassWindow(ChangePassWindow changePassWindow) {
        this.changePassWindow = changePassWindow;
    }

    public void setAllChatWindowController(AllChatWindowController allChatWindowController) {
        this.allChatWindowController = allChatWindowController;
    }

    public void setLoginWindowController(LoginWindowController loginWindowController) {
        this.loginWindowController = loginWindowController;
    }

    public ClientController() throws IOException {

        clSocket = new Socket(ipServer, portServer);
        in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clSocket.getOutputStream()));
        new Thread(() -> {
            while (!clSocket.isClosed()) {
                try {
                    inputMess = parsingXML.readXmlFromStream(in);
                    switch (inputMess.getCommandMess()) {
                        case CommandChat.ERR_USER:
                            Platform.runLater(() -> loginWindowController.errorLogin("User does not exist"));
                            break;
                        case CommandChat.ERR_PSW:
                            Platform.runLater(() -> loginWindowController.errorLogin("Password does not correct"));
                            break;
                        case CommandChat.OK_LOGIN:
                            user = inputMess.getUser();
                            Platform.runLater(() -> {
                                try {
                                    loginWindowController.showAllChatWindow();
                                    allChatWindowController.setIdUser(user.getNameUser());
                                    allChatWindowController.getUserActive(inputMess.getUserOnline());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            break;
                        case CommandChat.ERR_NEW_USER:
                            Platform.runLater(() -> loginWindowController.errorLogin("This user already exists"));
                            break;
                        case CommandChat.MSG:
                            Platform.runLater(() -> allChatWindowController.getMessageServer(inputMess.getFrom() + ": " + inputMess.getBodyMess()));
                            break;
                        case CommandChat.UPDATE_USERS:
                            Platform.runLater(() -> {
                                allChatWindowController.getUserActive(inputMess.getUserOnline());
                                allChatWindowController.getMessageServer(inputMess.getFrom() + ": " + inputMess.getBodyMess());
                            });
                            break;
                    }

                } catch (IOException | JAXBException e) {
                    closeSocketClient();
                    Platform.runLater(() -> loginWindowController.errorLogin("Server connection error.\nTry later."));
                    //e.printStackTrace();
                }
                //условия выхода
            }
        }).start();
    }

    // отправка сообщения
    public synchronized void sendMsg(Message message) {
        try {
            if (clSocket.isClosed()) {
                return;
            }
            if (user != null) {
                message.setFrom(user.getNameUser());
            }
            parsingXML.writeXMLinStream(message, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Закрывает входной и выходной потоки и сокет
     */
    public void closeSocketClient() {
        try {
            out.close();
            in.close();
            clSocket.close();
        } catch (Exception e) {
            System.err.println("Потоки не были закрыты!");
        }

    }
}

