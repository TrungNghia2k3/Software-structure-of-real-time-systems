package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666)) {
            System.out.println("Connected to server!");

            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.print(input.readUTF());
            String name = scanner.nextLine();
            output.writeUTF(name);

            // Start a thread to listen for messages from server
            new Thread(() -> {
                try {
                    while (true) {
                        String receivedMessage = input.readUTF();
                        System.out.println(receivedMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            // Read user input and send to server
            while (true) {
                String message = scanner.nextLine();
                output.writeUTF(message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
