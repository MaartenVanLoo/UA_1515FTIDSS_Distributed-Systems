import java.io.IOException;
import java.net.*;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;

    public UDPClient(String ip, int port) throws SocketException {
        this.socket = new DatagramSocket();
        this.port =port;
        //this.address = InetAddress(ip);
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        UDPClient client = new UDPClient("127.0.0.1",8889);
    }
}
