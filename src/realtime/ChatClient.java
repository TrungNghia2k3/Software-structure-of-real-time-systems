package realtime;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 6666)) { // Kết nối tới server chạy trên localhost cổng 6666
			System.out.println("Connected to server!"); // Thông báo kết nối thành công

			// Tạo luồng đọc dữ liệu từ server
			DataInputStream input = new DataInputStream(socket.getInputStream());
			// Tạo luồng gửi dữ liệu tới server
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			// Scanner để đọc dữ liệu từ bàn phím
			Scanner scanner = new Scanner(System.in);

			System.out.print(input.readUTF()); // Nhận thông báo từ server (ví dụ: "Enter your name:")
			String name = scanner.nextLine(); // Người dùng nhập tên
			output.writeUTF(name); // Gửi tên người dùng lên server

			// Tạo một luồng riêng để nhận tin nhắn từ server
			Thread messageListenerThread = new Thread(new MessageListener(input));
			messageListenerThread.start();

			// Đọc dữ liệu từ người dùng và gửi đến server
			while (true) {
				String message = scanner.nextLine(); // Đọc tin nhắn từ bàn phím
				output.writeUTF(message); // Gửi tin nhắn đến server
				if (message.equalsIgnoreCase("exit")) { // Nếu người dùng nhập "exit", thoát vòng lặp
					break;
				}
			}

			scanner.close(); // Đóng scanner sau khi thoát
		} catch (IOException e) {
			System.out.println("Client Error: " + e.getMessage()); // Xử lý lỗi kết nối
		}
	}
}
