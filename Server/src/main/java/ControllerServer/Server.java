package ControllerServer;

import ModelServer.*;
import ViewerServer.ServerWinController;
import javafx.application.Platform;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Server {

    private ServerSocket serverSocket = null;
    private Socket clientSockets = null;
    private final int PORT = 8888;
    private boolean stopServer = true;
    private Users usersList = new Users();
    private String usersFilePaht;
    private ConcurrentHashMap<ServerThread, String> activeUserList = new ConcurrentHashMap<>();
    private ServerWinController serverWinController;

    public void setServerWinController(ServerWinController serverWinController) {
        this.serverWinController = serverWinController;
    }

    public void startServer() throws JAXBException {
        try {
            if (serverSocket == null || serverSocket.isClosed()) {
                stopServer = false;
                serverSocket = new ServerSocket(PORT);
                usersFilePaht = checkUserFile();
                usersList = readFileUser(usersFilePaht);
                Platform.runLater(() -> serverWinController.sendMessage("!!Server running..."));
            } else {
                Platform.runLater(() -> serverWinController.sendMessage("The server is already running"));
            }
            while (!stopServer) {
                try {
                    clientSockets = serverSocket.accept();
                    new ServerThread(clientSockets);
                } catch (SocketException e) {
                    if (stopServer) {
                        continue;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ServerThread extends Thread {
        private Socket clientSocket;
        private BufferedWriter outMessage;
        private BufferedReader inputMessage;
        private ParsingXML parsingXML = new ParsingXML();

        private ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            inputMessage = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            outMessage = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            start();
        }

        public void run() {
            while (!clientSocket.isClosed()) {
                try {
                    if (inputMessage.ready()) {
                        readCommand(this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        }

        //
        public void sendMsg(Message message) {
            try {
                parsingXML.writeXMLinStream(message, outMessage);
                outMessage.flush();
            } catch (IOException | JAXBException e) {
                for (HashMap.Entry entry : activeUserList.entrySet()) {
                    ServerThread clientThead = (ServerThread) entry.getKey();
                    String nameUser = (String) entry.getValue();
                    if (clientThead.equals(this)) {
                        try {
                            clientThead.clientSocket.close();
                            clientThead.inputMessage.close();
                            clientThead.outMessage.close();
                            updateListUser(clientThead, nameUser, false);
                            return;
                        } catch (IOException | JAXBException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                e.printStackTrace();
            }
        }
    }

    // server
    private synchronized void readCommand(ServerThread client) throws IOException, JAXBException {
        Message toClient = new Message();
        Message fromClient = client.parsingXML.readXmlFromStream(client.inputMessage);
        CommandChat commandFromMess = fromClient.getCommandMess();
        User connectUser = null;

        if ((commandFromMess == CommandChat.LOGIN) || (commandFromMess == CommandChat.NEW_USER)) {
            connectUser = fromClient.getUser();
        }
        switch (commandFromMess) {
            case LOGIN:
                for (User userList : usersList.getUserList()) {
                    if ((connectUser.getNameUser().equals(userList.getNameUser()))) {
                        if (connectUser.getPasswordUser().equals(userList.getPasswordUser())) {
                            toClient.setUser(userList);
                            toClient.setCommandMess(CommandChat.OK_LOGIN);
                            client.sendMsg(toClient);
                            updateListUser(client, connectUser.getNameUser(), true);
                            return;
                        } else {
                            toClient.setCommandMess(CommandChat.ERR_PSW);
                            client.sendMsg(toClient);
                            return;
                        }
                    }
                }
                toClient.setCommandMess(CommandChat.ERR_USER);
                client.sendMsg(toClient);
                break;
            case OK_LOGIN:
                break;
            case ERR_USER:
                break;
            case ERR_PSW:
                break;
            case NEW_USER:
                for (User userList : usersList.getUserList()) {
                    if ((connectUser.getNameUser().equals(userList.getNameUser()))) {
                        toClient.setCommandMess(CommandChat.ERR_NEW_USER);
                        client.sendMsg(toClient);
                        return;
                    }
                }
                usersList.addUser(connectUser);
                saveFileUser(usersFilePaht);
                readFileUser(usersFilePaht);
                toClient.setUser(connectUser);
                toClient.setCommandMess(CommandChat.OK_LOGIN);
                client.sendMsg(toClient);
                updateListUser(client, connectUser.getNameUser(), true);
                break;
            case ERR_NEW_USER:
                break;
            case MSG:
                sendMessageToAllClients(fromClient);
                break;
            case UPDATE_USERS:
                break;
            case EXIT_USERS:
                for (HashMap.Entry entry : activeUserList.entrySet()) {
                    ServerThread clientThead = (ServerThread) entry.getKey();
                    String nameUser = (String) entry.getValue();
                    if (nameUser.equals(fromClient.getFrom())) {
                        clientThead.clientSocket.close();
                        clientThead.outMessage.close();
                        clientThead.inputMessage.close();
                        updateListUser(clientThead, nameUser, false);
                    }
                }
                break;
        }
    }

    public void stopServer() {
        try {
            if (serverSocket == null || serverSocket.isClosed()) {
                Platform.runLater(() -> {
                    serverWinController.sendMessage("Server is not running!");
                });
            } else {
                Platform.runLater(() -> {
                    serverWinController.sendMessage("The server will be shut down after 1 second");
                });
                Thread.sleep(1000);
                if (activeUserList.size() > 0) {
                    for (ServerThread client : activeUserList.keySet()) {
                        client.clientSocket.close();
                    }
                }
                serverSocket.close();
                stopServer = true;
                Platform.runLater(() -> {
                    serverWinController.sendMessage("Server is closed");
                });
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void sendMessageToAllClients(Message msg) throws IOException, JAXBException {
        for (HashMap.Entry entry : activeUserList.entrySet()) {
            ServerThread client = (ServerThread) entry.getKey();
            String nameUser = (String) entry.getValue();
            client.sendMsg(msg);
        }
    }

    private synchronized void updateListUser(ServerThread client, String currentUserName, boolean addUser) throws IOException, JAXBException {
        Message updateUser = new Message();
        if (addUser) {
            activeUserList.put(client, currentUserName);
            updateUser.setBodyMess("New user on chat: " + currentUserName);
        } else {
            updateUser.setBodyMess("The user: " + currentUserName + " left the chat\n");
            activeUserList.remove(client, currentUserName);
        }
        updateUser.setCommandMess(CommandChat.UPDATE_USERS);
        updateUser.setFrom("Server");
        ArrayList<String> listUsers = new ArrayList<>();
        listUsers.addAll(activeUserList.values());
        updateUser.setUserOnline(listUsers);
        sendMessageToAllClients(updateUser);
    }

    private String checkUserFile() throws IOException, JAXBException {
        // get the path separator in the current operating system
        String fileSeparator = System.getProperty("file.separator");
        String dirPath = "files";
        String fileName = "users.xml";
        Path path = Paths.get(dirPath);
        // if not create a directory
        if (!Files.exists(path)) {
            File newDir = new File(dirPath);
            //Если директория не создана выход из программы
            if (!newDir.mkdir()) {
                System.out.println("Directory not created");
                //LOGGER.error("Папку " + dirPath + "создать не удалось. Программа завершена");
                System.exit(0);
            }
        }
        String filePath = dirPath + fileSeparator + fileName;
        path = Paths.get(filePath);
        if (!Files.exists(path) || (new File(filePath).length() < 237)) {
            User defaultUser = new User("admin", "admin", true, false);
            usersList.addUser(defaultUser);
            saveFileUser(filePath);
        }
        return filePath;
    }

    public Users readFileUser(String filePath) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(Users.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Users) unmarshaller.unmarshal(new FileReader(filePath));
    }

    public void saveFileUser(String filePath) throws JAXBException, IOException {
        FileOutputStream newFile = new FileOutputStream(filePath);
        JAXBContext context = JAXBContext.newInstance(Users.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(usersList, newFile);
        newFile.close();
    }

    public void chedkUser(String nameUser) {

    }


}



