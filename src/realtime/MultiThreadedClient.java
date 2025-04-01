package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * MultiThreadedClient: Một client có thể kết nối đến server, gửi tin nhắn và
 * nhận phản hồi từ server.
 */
public class MultiThreadedClient {
	public static void main(String[] args) {
		try {
			// Kết nối đến server đang chạy trên localhost và cổng 6666
			Socket socket = new Socket("localhost", 6666);
			System.out.println("Connected to server!");

			// Tạo luồng đọc dữ liệu từ server
			DataInputStream input = new DataInputStream(socket.getInputStream());

			// Tạo luồng ghi dữ liệu gửi đến server
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			// Scanner để nhận input từ người dùng
			Scanner scanner = new Scanner(System.in);

			// Đọc tin nhắn chào mừng từ server
			System.out.println("Server: " + input.readUTF());

			while (true) {
				// Nhập tin nhắn từ bàn phím
				System.out.print("Enter message: ");

				// Gửi tin nhắn đến server
				String message = scanner.nextLine();

				// Nếu nhập "exit", client sẽ ngắt kết nối
				output.writeUTF(message);
				if (message.equalsIgnoreCase("exit"))
					break;

				// Nhận phản hồi từ server và hiển thị ra màn hình
				System.out.println("Server: " + input.readUTF());
			}

			// Đóng tất cả tài nguyên khi client kết thúc
			scanner.close(); // Đóng Scanner để tránh rò rỉ bộ nhớ
			input.close(); // Đóng luồng đầu vào
			output.close(); // Đóng luồng đầu ra
			socket.close(); // Đóng socket để ngắt kết nối với server
		} catch (IOException e) {
			System.out.println("Client Error: " + e.getMessage());
		}
	}
}
