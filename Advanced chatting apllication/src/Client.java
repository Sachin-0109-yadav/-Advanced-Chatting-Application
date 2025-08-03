import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Thread to read messages from server
        new Thread(() -> {
            String msg;
            try {
                while ((msg = in.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Main thread to send messages to server
        String userInput;
        while ((userInput = input.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("exit")) break;
        }

        socket.close();
    }
}