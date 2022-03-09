import java.io.*;
import java.net.*;
import java.util.Enumeration;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class SimpleServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String greeting = in.readLine();
        if ("hello server".equals(greeting)) {
            out.println("hello client");
        }
        else {
            out.println("unrecognised greeting");
        }
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                out.println("good bye");
                break;
            }
            out.println(inputLine); //echo input back to client
        }
    }
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void printIpv4Interfaces() throws SocketException {
        Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = e.nextElement();
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = ee.nextElement();
                if (i.getClass() == Inet4Address.class){ //filter for ipv4
                    System.out.printf("%-40s\t",n.getDisplayName());
                    System.out.println(i.getHostAddress());
                }
            }
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        SimpleServer.printIpv4Interfaces();
        SimpleServer server = new SimpleServer();
        server.start(8888);
    }
}