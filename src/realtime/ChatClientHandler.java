package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClientHandler implements Runnable {
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private String clientName;

    public ChatClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());

            output.writeUTF("Enter your name: ");
            this.clientName = input.readUTF();
            System.out.println(clientName + " has joined.");

            ChatServer.broadcastMessage(clientName + " has joined the chat!", this);
        } catch (IOException e) {
            System.out.println("ClientHandler Error: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = input.readUTF();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println(clientName + " disconnected.");
                    ChatServer.broadcastMessage(clientName + " has left the chat.", this);
                    break;
                }
                System.out.println(clientName + ": " + message);
                ChatServer.broadcastMessage(clientName + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println(clientName + " disconnected abruptly.");
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Error sending message to " + clientName);
        }
    }

    private void closeConnection() {
        try {
            input.close();
            output.close();
            clientSocket.close();
            ChatServer.removeClient(this);
        } catch (IOException e) {
            System.out.println("Error closing connection for " + clientName);
        }
    }
}
