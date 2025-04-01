package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * MultiThreadedServer: Một server có thể xử lý nhiều client cùng lúc bằng cách
 * tạo một thread riêng cho mỗi client kết nối.
 */
public class MultiThreadedServer {
	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(6666)) {
			System.out.println("Server is running on port 6666...");

			while (true) {
				// Chờ và chấp nhận kết nối từ client
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket);

				// Tạo một luồng mới để xử lý client
				ClientHandler clientHandler = new ClientHandler(clientSocket);
				new Thread(clientHandler).start();
			}
		} catch (IOException e) {
			System.out.println("Server Error: " + e.getMessage());
		}
	}
}

/**
 * ClientHandler: Xử lý từng client riêng biệt trong một luồng.
 */
class ClientHandler implements Runnable {
	private Socket clientSocket; // Socket để giao tiếp với client

	public ClientHandler(Socket socket) {
		this.clientSocket = socket;
	}

	@Override
	public void run() {
		try {
			// Tạo luồng đọc dữ liệu từ client
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());

			// Tạo luồng ghi dữ liệu gửi về client
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

			// Gửi thông báo chào mừng đến client
			output.writeUTF("Hello! You are connected to the Server.");

			while (true) {
				// Đọc tin nhắn từ client
				String message = input.readUTF();
				System.out.println("Received from client: " + message);

				// Nếu client gửi "exit" thì ngắt kết nối
				if (message.equalsIgnoreCase("exit")) {
					System.out.println("Client disconnected.");
					break;
				}

				// Gửi phản hồi lại client
				output.writeUTF("Server received: " + message);
			}

			// Đóng luồng dữ liệu và socket khi client ngắt kết nối
			input.close();
			output.close();
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Client disconnected abruptly: " + e.getMessage());
		}
	}
}
