import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/***
 * source:https://www.baeldung.com/udp-in-java
 */
public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;
    private byte[] buf;

    public UDPClient(String ip, int port) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.port =port;
        this.address = InetAddress.getByName("localhost"); //= 127.0.0.1
    }

    public String sendMessage(String msg) throws IOException {
        buf = msg.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
        socket.send(packet);
        packet = new DatagramPacket(buf,buf.length);
        socket.receive(packet);
        return new String (packet.getData(),0,packet.getLength());
    }

    public void requestFile(String filename) throws IOException {
        //request
        String request = "RequestFile:testfile.png";
        buf = request.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
        socket.send(packet);

        //receive frist packet with length
        packet = new DatagramPacket(buf,buf.length);
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength());
        int packetSize = Integer.valueOf(received);
        System.out.println(packetSize);
        //Actual data
        packet = new DatagramPacket(buf,buf.length);
        FileOutputStream fos = new FileOutputStream("received.png");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = 0;
        while (bytesRead < packetSize){
            packet = new DatagramPacket(buf,buf.length);
            socket.receive(packet);
            bos.write(packet.getData());
            bytesRead+=packet.getLength();
        }
        bos.flush();
        bos.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        UDPClient client = new UDPClient("127.0.0.1",8889);
        String answer = client.sendMessage("Hello");
        System.out.println(answer);
        client.requestFile("testfile.png");

    }
}
