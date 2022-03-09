import java.io.*;
import java.net.*;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class SimpleServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Starts a server that waits for a TCP connection at the specified port. After a connection has been set up, it reads
     * the input from the client and responds with "hello client" if the message equaled "hello server". Next up, the server
     * keeps reading the following lines at the input until a period appears.
     * @param port The port of the server socket.
     * @throws IOException If an I/O exception occurs.
     */
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

    /**
     * Closes the buffers and ends the connection.
     * @throws IOException
     */
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    /**
     * Starts a server that waits for 1 TCP connection at a time.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        SimpleServer server = new SimpleServer();
        server.start(8888);
    }
}
