package ControllerServer;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        new Server();

    }
    private Server(){
        System.out.println("Server running...");
        try (ServerSocket serverSocket = new ServerSocket(1313)){
            while (true){
                try {
new TCPC

                }catch (IOException e){
                    System.out.println(e);
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }


    }
}
