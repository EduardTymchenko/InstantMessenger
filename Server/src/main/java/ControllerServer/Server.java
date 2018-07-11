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

            System.out.println("otpr");

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

            //serverMessage.setBodyMess("eee");

            //Server.sendMessageToAllClients(parsingXML.messToXML(serverMessage).toString());


            //Server.sendMessageToAllClients("Клиентов в чате = " + clientSocket.getInetAddress().toString()+": "+clientSocket.getPort());



                /*
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
                */
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


