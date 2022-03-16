import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/***
 * source:https://www.baeldung.com/udp-in-java
 */
public class UDPServer  extends Thread{
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
                received = received.substring(0,received.indexOf(0)); //remove buffer padding on the end!! (\0)
                System.out.println("Received: " +received );
                //received.replace(0,"");
                String[] cmd = received.split(":");
                if (cmd.length == 0) continue;

                if (cmd.length == 1) socket.send(packet); //Echo packet back to sender;
                //parse other commands
                if (cmd[0].equals("RequestFile")) sendFile(cmd[1],address,port);

            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendFile(String filename, InetAddress address,int port) throws IOException {
        //send file => file not found??????
        System.out.println(filename);
        File myFile = new File(filename);
        if (!myFile.exists()) System.out.println("File not found");

        //Send packet with datasize
        String length = String.valueOf(myFile.length());
        DatagramPacket packet = new DatagramPacket(length.getBytes(StandardCharsets.UTF_8), length.getBytes(StandardCharsets.UTF_8).length,address,port);
        socket.send(packet);

        //send actual image
        byte[] mybytearray = new byte[(int) myFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        int byteCount = bis.read(mybytearray, 0, mybytearray.length);
        packet = new DatagramPacket(mybytearray, mybytearray.length,address,port);
        socket.send(packet);
        System.out.println("File send");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        UDPServer server = new UDPServer(8889);
        server.run();
    }
}
