import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class MultiServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            try {
                new ClientHandler(serverSocket.accept()).start();
            }
            catch (IOException exception){
                exception.printStackTrace();
                break;
            }
        }
    }
    public void stop() throws IOException {
        serverSocket.close();
    }

    private static class ClientHandler extends Thread{
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public synchronized void start() {
            super.start();
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (".".equals(inputLine)) {
                        out.println("bye");
                        break;
                    }
                    out.println(inputLine); //echo input back to client
                    System.out.printf("Recieved: %s\n",inputLine);
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
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
        MultiServer.printIpv4Interfaces();
        MultiServer server = new MultiServer();
        server.start(8888);
    }
}
