import java.io.*;
import java.net.Socket;

public class TCPConection {
    private final Socket socket;
    private final Thread thread;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final TCPListener eventListner;

    public TCPConection(TCPListener eventListner,String ipAdress,int port) throws IOException{
        this(eventListner, new Socket(ipAdress,port));
    }

    public TCPConection(TCPListener eventListner,Socket socket) throws IOException {
        this.eventListner = eventListner;
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    eventListner.connectionReady(TCPConection.this);
                    while (!thread.isInterrupted()){
                        eventListner.receive(TCPConection.this,in.readLine());
                    }
                }catch (IOException e){
                    eventListner.exeption(TCPConection.this,e);
                }finally {
                    eventListner.disconection(TCPConection.this);
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
            eventListner.exeption(TCPConection.this,e);
            disconnect();
        }
    }
    public synchronized void disconnect(){
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListner.exeption(TCPConection.this,e);
        }
    }

    @Override
    public String toString() {
        return "TCPConection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
