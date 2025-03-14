package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MultiThreadedClient {
	public static void main(String[] args) {
		try {
			// Connect to the server running on localhost, port 6666
			Socket socket = new Socket("localhost", 6666);
			System.out.println("Connected to server!");

			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			Scanner scanner = new Scanner(System.in);

			// Read the message from the server upon successful connection
			System.out.println("Server: " + input.readUTF());

			while (true) {
				System.out.print("Enter message: ");
				String message = scanner.nextLine();

				output.writeUTF(message);
				if (message.equalsIgnoreCase("exit"))
					break;

				// Receive response from the server
				System.out.println("Server: " + input.readUTF());
			}

			// Close connection
			scanner.close();
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Client Error: " + e.getMessage());
		}
	}
}
