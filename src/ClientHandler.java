import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    Socket socket;
    PrintWriter writer;
    BufferedReader reader;

    public ClientHandler(Socket socket, PrintWriter writer) throws IOException {
        this.socket = socket;
        this.writer = writer;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run() {
        try {
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.println("Client says: " + msg);
                Server.broadcast(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

