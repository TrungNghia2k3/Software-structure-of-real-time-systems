package realtime;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * ChatServer: Một máy chủ hỗ trợ nhiều client giao tiếp với nhau. - Mỗi client
 * khi kết nối sẽ được xử lý trong một luồng riêng. - Server có khả năng gửi tin
 * nhắn đến tất cả client đang kết nối.
 */
public class ChatServer {
	// Danh sách lưu trữ các client đã kết nối
	private static Set<ChatClientHandler> clientHandlers = new HashSet<>();

	public static void main(String[] args) {
		try (ServerSocket serverSocket = new ServerSocket(6666)) { // Mở cổng 6666 để lắng nghe kết nối
			System.out.println("Server is running on port 6666...");

			while (true) {
				// Chờ và chấp nhận kết nối từ client
				Socket clientSocket = serverSocket.accept();
				System.out.println("New client connected: " + clientSocket);

				// Tạo một luồng xử lý riêng cho client
				ChatClientHandler clientHandler = new ChatClientHandler(clientSocket);

				// Thêm client vào danh sách các client đang hoạt động
				clientHandlers.add(clientHandler);

				// Chạy client trên một luồng mới
				new Thread(clientHandler).start();
			}
		} catch (IOException e) {
			System.out.println("Server Error: " + e.getMessage());
		}
	}

	/**
	 * Gửi tin nhắn đến tất cả client ngoại trừ client gửi tin.
	 *
	 * @param message Tin nhắn cần gửi
	 * @param sender  Client gửi tin
	 */
	public static synchronized void broadcastMessage(String message, ChatClientHandler sender) {
		for (ChatClientHandler client : clientHandlers) {
			if (client != sender) { // Không gửi tin nhắn lại cho chính người gửi
				client.sendMessage(message);
			}
		}
	}

	/**
	 * Xóa client khỏi danh sách khi client ngắt kết nối.
	 *
	 * @param clientHandler Client cần xóa
	 */
	public static synchronized void removeClient(ChatClientHandler clientHandler) {
		clientHandlers.remove(clientHandler);
	}
}
