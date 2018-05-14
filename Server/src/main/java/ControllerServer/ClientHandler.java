package ControllerServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{
    private final Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
        start();
    }
    public void  run(){
        while (true){
            try {
                DataInputStream inputStream = new DataInputStream(client.getInputStream());
                if (inputStream.available() > 0){
                    //чтение и обработка от клиента модель парсинг сообщения
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
