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

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (".".equals(inputLine)) {
                out.println("bye");
                break;
            }
            System.out.printf("Recieved: %s\n", inputLine);

            String[] cmd = inputLine.split(":");
            if (cmd.length == 0) continue;

            if (cmd.length == 1) {
                out.println(cmd[0]);
            } //echo back;
            //parse other commands
            if (cmd[0].equals("RequestFile")) sendFile(cmd[1]);
        }
    }

    private void sendFile(String filename) throws IOException {
        //send file
        File myFile = new File(filename);
        byte[] mybytearray = new byte[(int) myFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        int byteCount = bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = clientSocket.getOutputStream();
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        SimpleServer server = new SimpleServer();
        server.start(8888);
    }
}
