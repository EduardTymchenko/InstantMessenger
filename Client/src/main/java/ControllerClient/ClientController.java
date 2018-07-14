package ControllerClient;

import ModelServer.Message;
import ModelServer.ParsingXML;
import ViewerClient.AllChatWindowController;
import javafx.application.Platform;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    private AllChatWindowController allChatWindowController;
    private String messagString;
    private Socket clSocket;
    private final int portServer = 8888;
    private final String ipServer = "localhost";
    private BufferedReader in;
    private BufferedWriter out;
    private Scanner scan = new Scanner(System.in);
    private ParsingXML parsingXML = new ParsingXML();
    private Message clientMess = new Message();

    public void setAllChatWindowController(AllChatWindowController allChatWindowController) {
        this.allChatWindowController = allChatWindowController;
    }

    public ClientController() throws IOException {

            clSocket = new Socket(ipServer, portServer);
            in = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clSocket.getOutputStream()));
            new Thread(() -> {
                while (true) {

                    try {
                        messagString = parsingXML.readXmlFromStream(in).getBodyMess();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(()->{
                           allChatWindowController.getMessageServer(messagString);
                        });
                        System.out.println("111111");


                    //условия выхода
                }
            }).start();
        }

    public String getMessagString() {
        return messagString;
    }
// отправка сообщения

        public synchronized void sendMsg(String tekstOut){
try {
    clientMess.setBodyMess(tekstOut);
    parsingXML.writeXMLinStream(clientMess,out);
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

