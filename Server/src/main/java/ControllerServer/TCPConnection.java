package ControllerServer;

import java.io.*;
import java.net.Socket;

public class TCPConnection {
    private final Socket socket;
    private final Thread thread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final TCPListener eventListner;

    public TCPConnection(TCPListener eventListner, String ipAdress, int port) throws IOException {
        this(eventListner, new Socket(ipAdress,port));
    }

    public TCPConnection(final TCPListener eventListner, Socket socket) throws IOException {
        this.eventListner = eventListner;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventListner.connectionReady(TCPConnection.this);
                    while (!thread.isInterrupted()){
                        eventListner.receive(TCPConnection.this,in.readLine());
                    }
                }catch (IOException e){
                    eventListner.exeption(TCPConnection.this,e);
                }finally {
                    eventListner.disconection(TCPConnection.this);
                }


            }
        });
        thread.start();
    }
    public synchronized void sendMsg(String value){
        try {
            out.write(value + "\r\n");
            out.flush();
        } catch (IOException e) {
            eventListner.exeption(TCPConnection.this,e);
            disconnect();
        }
    }
    public synchronized void disconnect(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListner.exeption(TCPConnection.this,e);
        }
    }

    @Override
    public String toString() {
        return "ConnectionNet.TCPConection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
