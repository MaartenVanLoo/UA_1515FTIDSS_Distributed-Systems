import java.io.*;
import java.net.*;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Starts a TCP connection with a specified IPv4 address and port.
     * @param ip The IPv4 address to start a connection with.
     * @param port The port of the server it tries connecting to.
     * @throws IOException
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Sends a specified string to the outputstream of Socket.
     * @param msg String it sends to the outputstream.
     * @return The response it gets from the inputstream at the Socket.
     * @throws IOException If there's an I/O error at the inputstream.
     */
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine(); //Return response;
    }

    /**
     * Ends the TCP connection.
     * @throws IOException If an I/O error occurs when closing the inputstream or clientSocket.
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    /**
     * Initializes a Client class. Then starts a connection with a specific IP address and port number. When the connection has
     * been set up, it sends a simple message to the server. Finally it prints out what it has just sent and what the reponse
     * of the server was.
     * @param args
     * @throws IOException If an I/O error occurs in one of the previous methods.
     */
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("143.169.200.2",8888); //use loopback adress;
        String message = "hello server";

        System.out.printf("Send    : %s\n",message);
        String response = client.sendMessage(message);
        System.out.printf("Response: %s\n",response);

        client.stopConnection();
    }
}
