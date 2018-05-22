package ControllerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    private Socket clSocket;
    private final int portServer = 8888;
    private final String ipServer = "localhost";
    private Scanner in;
    private PrintWriter out;
    private Scanner scan = new Scanner(System.in);

    public ClientController() throws IOException {
        try {
            clSocket = new Socket(ipServer, portServer);
            in = new Scanner(clSocket.getInputStream());
            out = new PrintWriter(clSocket.getOutputStream(), true);
            new Thread(new Runnable() {
                public void run() {
                    while (in.hasNext()) {
                        String inMes = in.nextLine();
                        System.out.println(inMes);
                    }
                }
            }).start();

            String outM;
            while (true) {
                outM = scan.nextLine();
                if (outM.equals("exit"))
                    break;
                sendMsg(outM);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
        // отправка сообщения

        public void sendMsg (String messageStr){
try {
    out.println(messageStr);
    out.flush();

} catch (Exception e){
    e.printStackTrace();
}


        }


        /**
         * Закрывает входной и выходной потоки и сокет
         */
        private void close () {
            try {
                in.close();
                out.close();
                clSocket.close();
            } catch (Exception e) {
                System.err.println("Потоки не были закрыты!");
            }

        }

    }

