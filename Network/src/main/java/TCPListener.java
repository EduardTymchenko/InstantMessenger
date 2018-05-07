public interface TCPListener {
    void connectionReady (TCPConection tcpConection);
    void receive (TCPConection tcpConection, String mess);
    void disconection (TCPConection tcpConection);
    void exeption (TCPConection tcpConection, Exception e);
}
