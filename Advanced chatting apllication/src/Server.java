import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<ClientHandler> clients = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("[Server] Listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[Server] New client connected: " + clientSocket);
            ClientHandler handler = new ClientHandler(clientSocket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}