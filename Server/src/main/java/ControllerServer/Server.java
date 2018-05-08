package ControllerServer;


import ConnectionNet.TCPConection;
import ConnectionNet.TCPListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server implements TCPListener{
    public static void main(String[] args) {
        new Server();

    }
    private final ArrayList<TCPConection> conections = new ArrayList<>();
    private Server() {
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(8888)){
            while (true){
                try {
                    new TCPConection(this,serverSocket.accept());

                }catch (IOException e){
                    System.out.println(e);
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }


    }

    public synchronized void connectionReady(TCPConection tcpConection) {
        conections.add(tcpConection);
        sendToAllClients("Client connected: " + tcpConection);

    }

    public synchronized void receive(TCPConection tcpConection, String mess) {
        sendToAllClients(mess);

    }

    public synchronized void disconection(TCPConection tcpConection) {
        conections.remove(tcpConection);
        sendToAllClients("Client disconnected: " + tcpConection);
    }

    public synchronized void exeption(TCPConection tcpConection, Exception e) {
        System.out.println("Connection Exeption " + e);

    }
    private void sendToAllClients(String value){
        System.out.println(value);
        for (int i=0; i < conections.size(); i++){
            conections.get(i).sendMsg(value);
        }
    }
}
