package ControllerServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {


    private  ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    private final int PORT = 8888;
    private final ArrayList<ServerThread> activeUsers = new ArrayList<>();

    public void startServer() {
        try {
            if (serverSocket == null || serverSocket.isClosed()){
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server running...");
            }else {
                System.out.println("The server is already running");
            }
            while (!serverSocket.isClosed()) {
                try {
                    clientSocket = serverSocket.accept();
                    ServerThread client = new ServerThread(clientSocket);
                    activeUsers.add(client);
                } catch (SocketException e) {
                        continue;
                        //e.printStackTrace();
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
                    if (clientSocket != null ){
                        clientSocket.close();
                    }
                    serverSocket.close();
                    System.out.println("Server is closed");
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("$$$$");
                e.printStackTrace();
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
    private void sendToAllClients(String value){
        System.out.println(value);
        for (int i=0; i < conections.size(); i++){
            conections.get(i).sendMsg(value);
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


