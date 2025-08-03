import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter your name:");
            username = in.readLine();
            Server.broadcast("[Server] " + username + " joined the chat!", this);

            String msg;
            while ((msg = in.readLine()) != null) {
                if (msg.equalsIgnoreCase("exit")) break;
                System.out.println("[" + username + "]: " + msg);
                Server.broadcast("[" + username + "]: " + msg, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                Server.broadcast("[Server] " + username + " left the chat.", this);
                Server.removeClient(this);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String msg) {
        out.println(msg);
    }
}