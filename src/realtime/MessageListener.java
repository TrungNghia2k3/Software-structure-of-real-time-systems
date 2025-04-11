package realtime;

import java.io.DataInputStream;
import java.io.IOException;

public class MessageListener implements Runnable {
	private final DataInputStream input;

	public MessageListener(DataInputStream input) {
		this.input = input;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String receivedMessage = input.readUTF(); // Nhận tin nhắn từ server
				System.out.println(receivedMessage); // Hiển thị tin nhắn lên giao diện console
			}
		} catch (IOException e) {
			System.out.println("Mất kết nối với server. Đang dừng lắng nghe tin nhắn...");
		}
	}
}
