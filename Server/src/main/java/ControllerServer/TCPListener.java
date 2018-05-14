package ControllerServer;


public interface TCPListener {
    void connectionReady (TCPConnection tcpConection);
    void receive (TCPConnection tcpConection, String mess);
    void disconection (TCPConnection tcpConection);
    void exeption (TCPConnection tcpConection, Exception e);
}
