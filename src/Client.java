import java.io.*;
import java.net.*;

/**
 * Source: https://www.baeldung.com/a-guide-to-java-sockets
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String respons = in.readLine();
        System.out.println(respons);

        //Fetch answer file
        byte[] mybytearray = new byte[1024];
        InputStream is = clientSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream("recieved.png");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead;
        do {
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            bos.write(mybytearray, 0, bytesRead);
        }while(mybytearray.length == bytesRead);
        bos.flush();
        bos.close();

        return respons;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1",8888); //use loopback adress;
        String message = "hello server";

        System.out.printf("Send    : %s\n",message);
        String response = client.sendMessage(message);
        System.out.printf("Response: %s\n",response);

        client.stopConnection();
    }
}
