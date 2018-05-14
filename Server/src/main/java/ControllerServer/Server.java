package ControllerServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements TCPListener{
    //public static void main(String[] args) {
    //    new Server();

    //}
    private boolean stopServ;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int PORT = 8888;
    private final ArrayList<TCPConnection> conections = new ArrayList<>();

/*public void start() {
    try {
        serverSocket = new ServerSocket(PORT);
    } catch (IOException e) {
        e.printStackTrace();
    }
}*/

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server running...");

        while (!stopServ) {
            try {
                clientSocket = serverSocket.accept();
                //new ClientHandler(clientSocket);
                //new TCPConnection(this, serverSocket.accept());

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }

    public void serverHandler(){

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
    }
}

