package FileTransfer;

import java.io.*;
import java.net.Socket;

public class FileTransferSender {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public void SendFile(String path,String destIP,int port) throws IOException {
        this.startConnection(destIP,port);
        this.sendFile(path);
        this.stopConnection();
    };

    /**
     * Starts a TCP connection with a specified IPv4 address and port.
     * @param ip The IPv4 address to start a connection with.
     * @param port The port of the server it tries connecting to.
     * @throws IOException
     */
    private void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Send a file to the output stream.
     * @param filename Filename of the file being send.
     * @throws IOException
     */
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

    /**
     * Ends the TCP connection.
     * @throws IOException If an I/O error occurs when closing the inputstream or clientSocket.
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
