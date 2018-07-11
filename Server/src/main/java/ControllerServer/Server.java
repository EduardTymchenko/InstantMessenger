package ControllerServer;

import ModelServer.Message;
import ModelServer.ParsingXML;
import ViewerServer.ServerWinController;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket = null;
    private Socket clientSockets = null;
    private final int PORT = 8888;
    private boolean stopServer = true;
    private static ArrayList<ServerThread> activeUsers = new ArrayList<>();
    private ServerWinController serverWinController;
    private Message serverMessage = new Message();

    //Запускаем сервер
    public void startServer() {
        try {
            if (serverSocket == null || serverSocket.isClosed()){
                stopServer = false;
                serverSocket = new ServerSocket(PORT);
                System.out.println("!!Server running...");
            }else {
                System.out.println("The server is already running");
            }
            while (!stopServer) {
                try {
                    clientSockets = serverSocket.accept();
                    ServerThread client = new ServerThread(clientSockets);

                    // обработка пользователя
                    activeUsers.add(client);
                } catch (SocketException e) {
                    if (stopServer){
                        continue;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                clientSockets.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public class ServerThread extends Thread {
        private Socket clientSocket;
        private BufferedWriter outMessage;
        private BufferedReader inputMessage;
        private ParsingXML parsingXML = new ParsingXML();

        private ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            System.out.println("new user connected from " + clientSocket.getInetAddress().toString()+ clientSocket.getPort());
            inputMessage = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            outMessage = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));

            start();
        }

        public void  run() {
            // сервер отправляет сообщение
            //sendMessageToAllClients("!!!Новый участник вошёл в чат!");

            System.out.println("new User");

            while (true) {
                try {
                    try {
                        if(inputMessage.ready()) {
                            readCommand(this);
                        }
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // отправляем сообщение
        public synchronized void sendMsg(String msg) {

            try {
                serverMessage.setBodyMess(msg);
                parsingXML.writeXMLinStream(serverMessage,outMessage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void stopServer () {
            try {
                if (serverSocket == null || serverSocket.isClosed()){
                    System.out.println("Server is not running!");
                } else {
                    System.out.println("The server will be shut down after 1 second");
                    Thread.sleep(1000);
                    if (clientSockets != null ){
                        clientSockets.close();
                    }
                    serverSocket.close();
                    stopServer = true;
                    System.out.println("Server is closed");
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    private synchronized void readCommand(ServerThread client) throws IOException, JAXBException {

        String commandFromMess = client.parsingXML.readXmlFromStream(client.inputMessage).getBodyMess();

            System.out.println(commandFromMess);
            sendMessageToAllClients(commandFromMess);
        }

    // отправляем сообщение всем клиентам
    private synchronized void sendMessageToAllClients(String msg) {
        for (ServerThread client : activeUsers) {
            client.sendMsg(msg);
        }
    }

}



