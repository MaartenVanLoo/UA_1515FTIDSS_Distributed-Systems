package FileTransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferReceiver{
    private ServerSocket serverSocket ;
    private Socket clientSocket;
    private PrintWriter out;
    private InputStream in;

    public void ReceiveFile(String fileName){
        ReceiveFile(fileName,21);
    }
    public void ReceiveFile(String fileName, int port){
        try {
            startSocket(port);

            //wait for fileTransfer
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = clientSocket.getInputStream();

            //create new file
            File file = new File(fileName);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            //receive file
            receiveStream(new FileOutputStream(file));

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            stopSocket();
        }
        return;
    };

    private void startSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);

    }
    private void startSocket() throws IOException {
        serverSocket = new ServerSocket(21);
    }

    private boolean receiveStream(FileOutputStream fileOutputStream) throws IOException {
        int count;
        byte[] buffer = new byte[8192]; // or 4096, or more
        while ((count = in.read(buffer)) > 0)
        {
            fileOutputStream.write(buffer, 0, count);
        }
        fileOutputStream.flush();
        fileOutputStream.close();
        return true;
    }

    private void stopSocket() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    };

}
