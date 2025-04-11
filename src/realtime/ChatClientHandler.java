package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * ChatClientHandler: Lớp xử lý từng client trong một luồng riêng biệt. - Quản
 * lý giao tiếp giữa server và client. - Lắng nghe tin nhắn từ client và gửi tin
 * nhắn đến các client khác.
 */
public class ChatClientHandler implements Runnable {
	private Socket clientSocket; // Socket để giao tiếp với client
	private DataInputStream input; // Luồng đầu vào để nhận dữ liệu từ client
	private DataOutputStream output; // Luồng đầu ra để gửi dữ liệu đến client
	private String clientName; // Tên của client

	/**
	 * Constructor: Khởi tạo handler cho một client.
	 * 
	 * @param socket Socket kết nối với client
	 */
	public ChatClientHandler(Socket socket) {
		this.clientSocket = socket;
		try {
			// Khởi tạo luồng dữ liệu vào/ra
			input = new DataInputStream(clientSocket.getInputStream());
			output = new DataOutputStream(clientSocket.getOutputStream());

			// Yêu cầu client nhập tên
			output.writeUTF("Enter your name: ");
			this.clientName = input.readUTF(); // Đọc tên client từ đầu vào
			System.out.println(clientName + " has joined."); // Hiển thị thông báo trên server

			// Thông báo cho tất cả các client khác rằng có người mới tham gia
			ChatServer.broadcastMessage(clientName + " has joined the chat!", this);
		} catch (IOException e) {
			System.out.println("ClientHandler Error: " + e.getMessage());
		}
	}

	/**
	 * Chạy luồng xử lý client: Lắng nghe và xử lý tin nhắn từ client.
	 */
	@Override
	public void run() {
		try {
			while (true) {
				String message = input.readUTF(); // Đọc tin nhắn từ client

				// Nếu client nhập "exit", ngắt kết nối
				if (message.equalsIgnoreCase("exit")) {
					System.out.println(clientName + " disconnected."); // Thông báo trên server
					ChatServer.broadcastMessage(clientName + " has left the chat.", this);
					break; // Thoát khỏi vòng lặp
				}

				// Hiển thị tin nhắn từ client trên server
				System.out.println(clientName + ": " + message);

				// Phát tin nhắn đến tất cả các client khác
				ChatServer.broadcastMessage(clientName + ": " + message, this);
			}
		} catch (IOException e) {
			System.out.println(clientName + " disconnected abruptly."); // Xử lý khi client thoát đột ngột
		} finally {
			closeConnection(); // Đóng kết nối khi kết thúc
		}	
	}

	/**
	 * Gửi tin nhắn đến client.
	 * 
	 * @param message Tin nhắn cần gửi
	 */
	public void sendMessage(String message) {
		try {
			output.writeUTF(message);
		} catch (IOException e) {
			System.out.println("Error sending message to " + clientName);
		}
	}

	/**
	 * Đóng kết nối của client.
	 */
	private void closeConnection() {
		try {
			input.close();
			output.close();
			clientSocket.close();
			ChatServer.removeClient(this); // Xóa client khỏi danh sách server
		} catch (IOException e) {
			System.out.println("Error closing connection for " + clientName);
		}
	}
}
