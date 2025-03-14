package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(6666)) {
			System.out.println("Server is running on port 6666...");

			while (true) {
				// Wait for and accept a connection from a client
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket);

				// Each client is handled by a separate thread
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				new Thread(clientHandler).start();
			}
		} catch (IOException e) {
			System.out.println("Server Error: " + e.getMessage());
		}
	}
}

// Class to handle each client in a separate thread
class ClientHandler implements Runnable {
	private Socket clientSocket;

	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		try {
			// Create input and output streams
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

			output.writeUTF("Hello! You are connected to the Server.");

			while (true) {
				String message = input.readUTF();
				System.out.println("Received from client: " + message);

				if (message.equalsIgnoreCase("exit")) {
					System.out.println("Client disconnected.");
					break;
				}

				output.writeUTF("Server received: " + message);
			}

			// Close connection
			input.close();
			output.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Client disconnected abruptly: " + e.getMessage());
		}
	}
}
