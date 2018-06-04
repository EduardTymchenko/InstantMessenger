package ControllerServer;

import ModelServer.MessageXML;
import ViewerServer.ServerWindowController;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private String toArreyText;
    private  ServerSocket serverSocket = null;
    private Socket clientSockets = null;
    private final int PORT = 8888;
    private boolean stopServer;
    private static ArrayList<ServerThread> activeUsers = new ArrayList<>();
    private ServerWindowController serverWindowController ;
    private MessageXML serverMessage = new MessageXML("Server","");

    public Server() throws ParserConfigurationException {
    }

    public String getToArreyText() {
        return toArreyText;
    }

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
                System.out.println("$$$$");
                e.printStackTrace();
            }
        }
    // отправляем сообщение всем клиентам
    public static void sendMessageToAllClients(String msg) {
        for (ServerThread client : activeUsers) {
            client.sendMsg(msg);
        }
    }

    public class ServerThread extends Thread {
        private Socket clientSocket;
        private PrintWriter outMessage;
        private Scanner inputMessage;
        private Document message;

        public ServerThread(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            System.out.println("new user connected from " + clientSocket.getInetAddress().toString()+ clientSocket.getPort());
            inputMessage = new Scanner(this.clientSocket.getInputStream());
            outMessage = new PrintWriter(this.clientSocket.getOutputStream(),true);
            start();
        }
        public void  run() {


                        // сервер отправляет сообщение
                        Server.sendMessageToAllClients("Новый участник вошёл в чат!");
                        serverMessage.setBodyMess("Новый участник вошёл в чат!");
                        System.out.println(serverMessage);


                Server.sendMessageToAllClients(serverMessage.xmlString());


            Server.sendMessageToAllClients("Клиентов в чате = " + clientSocket.getInetAddress().toString()+
                        ": "+clientSocket.getPort());
                        sendMsg("WWWWWWWWWWWW");
            while (true) {
                // Если от клиента пришло сообщение
                if (inputMessage.hasNext()) {
                    String clientMessage = inputMessage.nextLine();
                    // если клиент отправляет данное сообщение, то цикл прерывается и
                    // клиент выходит из чата
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                    // выводим в консоль сообщение (для теста)
                    System.out.println(clientMessage);
                    // отправляем данное сообщение всем клиентам
                    Server.sendMessageToAllClients(clientMessage);
                }
            }
        }
        // отправляем сообщение
        public void sendMsg(String msg) {
            try {
                outMessage.println(msg);
                outMessage.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

    /*public void serverHandler(){

    }

    public synchronized void connectionReady(TCPConnection tcpConection) {
        conections.add(tcpConection);
        sendToAllClients("Client connected: " + tcpConection);

    }

    public synchronized void receive(TCPConnection tcpConection, String mess) {
        sendToAllClients(mess);

    }

    public synchronized void disconection(TCPConnection tcpConection) {
        conections.remove(tcpConection);
        sendToAllClients("Client disconnected: " + tcpConection);
    }

    public synchronized void exeption(TCPConnection tcpConection, Exception e) {
        System.out.println("Connection Exeption " + e);

    }

    }
    public void stopServer(){
        try {

                System.out.println("The server will be shut down after 1 second");
                //Thread.sleep(1000);
            if (clientSocket != null) {
                clientSocket.close();
            }
                serverSocket.close();
            //stopServ = true;



        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean isStopServ() {
        return stopServ;
    }

    public void setStopServ(boolean stopServ) {
        this.stopServ = stopServ;
    }*/


