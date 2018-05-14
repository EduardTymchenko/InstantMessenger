package ControllerClient;

import java.io.IOException;
import java.net.Socket;

public class ClientController {
    private static Socket clientSocket;

    private static final int portServer = 8888;
    private static final String ipServer = "localhoct";

    public static void main(String[] args) {
        connect();
        handle();
        end();
    }


    private static void connect(){
        try {
            clientSocket = new Socket(ipServer,portServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // обработка подключения к серверу
    private static void handle(){

    }
    private static void end(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
