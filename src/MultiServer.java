import java.io.*;
import java.net.*;

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
        private final Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
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
                    System.out.printf("Recieved: %s\n",inputLine);

                    String[] cmd = inputLine.split(":");
                    if (cmd.length == 0) continue;

                    if (cmd.length == 1) {
                        out.println(cmd[0]);} //echo back;
                    //parse other commands
                    if (cmd[0].equals("RequestFile")) sendFile(cmd[1]);
                }
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
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
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Starting server");
        System.out.println("Server host ip:");
        IPUtils.printIpv4Interfaces();
        MultiServer server = new MultiServer();
        server.start(8888);
    }
}
