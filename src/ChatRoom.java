import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatRoom {
    public static void main(String[] args) throws Exception {
        // Get the port from the console
        Scanner scanner = new Scanner(System.in);
        int port = scanner.nextInt();
        scanner.close();

        //server socket
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started. Waiting for incoming connections...");

        // Accept socket connections
        Socket clientSocket;

        //noinspection InfiniteLoopStatement
        do {
            System.out.println("New client connected");

            // client connection
            new ChatHandler();
        } while (true);
    }

    private static class ChatHandler {
        public ChatHandler() {
        }
    }
}

class ChatUser {
    public static void main(String[] args) throws Exception {
        // Get the address and port from the console
        Scanner scanner = new Scanner(System.in);
        String address = scanner.nextLine();
        int port = Integer.parseInt(scanner.nextLine());
        scanner.close();

        // Connect to the server
        Socket socket = new Socket(address, port);
        Scanner in = new Scanner(socket.getInputStream());

        //welcome message
        String message = in.nextLine();
        System.out.println(message);

        // messages from the server
        while (true) {
            message = in.nextLine();
            if (message.startsWith("/nick ")) {
                // name change
                String newName = message.substring(6);
                System.out.println("Client changed name to " + newName);
            } else if (message.startsWith("/private ")) {
                //private message
                String sender = message.substring(9).split(" ")[0];
                String messageText = message.substring(10 + sender.length());
                System.out.println("Received private message from " + sender + ": " + messageText);
            } else if (message.startsWith("/exit")) {

                System.out.println("Client left the chat");
                break;
            } else {

                System.out.println("Received message: " + message);
            }
        }
    }
}