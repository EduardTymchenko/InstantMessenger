package ControllerServer;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private BufferedWriter outMessage;
    private BufferedReader inputMessage;

    public ServerThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        inputMessage = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        outMessage = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
        start();
    }
    public void  run(){

    }

}
