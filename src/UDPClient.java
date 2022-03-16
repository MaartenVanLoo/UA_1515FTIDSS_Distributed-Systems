import java.io.IOException;
import java.net.*;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;

    public UDPClient(int port) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.port =port;
        this.address = InetAddress.getByName("localhost");
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        UDPClient client = new UDPClient(8889);
    }
}
