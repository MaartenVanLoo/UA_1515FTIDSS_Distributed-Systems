import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;

/***
 * source:https://www.baeldung.com/udp-in-java
 */
public class UDPServer extends Thread{

    private DatagramSocket socket;
    private boolean running = true;
    private byte[] buf = new byte[256];

    public UDPServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        running = true;
        try {
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());

                if (received.equals("")) {
                    running = false;
                    continue;
                }
                socket.send(packet); //Echo packet back to sender;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        UDPServer server = new UDPServer(8889);
        server.run();
    }
}
