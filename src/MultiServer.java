import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class MultiServer {
    private ServerSocket serverSocket;

    /**
     * Starts a server at a specified port and then waits for a TCP connection. If a connection is made, it starts a new
     * Thread for handling the connection and ending it.
     * @param port The port of the server socket.
     * @throws IOException If the creation of the ServerSocket fails.
     */
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

    /**
     * Closes the ServerSocket.
     * @throws IOException
     */
    public void stop() throws IOException {
        serverSocket.close();
    }

    /**
     * Class for handling a TCP connection in a different thread. (similar to Client.java)
     */
    private static class ClientHandler extends Thread{
        private final Socket clientSocket;
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
        /**
         *
         */
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

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        MultiServer server = new MultiServer();
        server.start(8888);
    }
}
