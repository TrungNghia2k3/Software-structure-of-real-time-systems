package project;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class ClientMain {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			// Kết nối với server
			socket = new Socket("localhost", 8888);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			SharedSensorQueue sharedQueue = new SharedSensorQueue();
			Semaphore uploadSemaphore = new Semaphore(1); // 1 permit = gửi tuần tự

			// Tạo các thread cảm biến
			new SensorThread("Soil Moisture", 3, 1000, 3, sharedQueue, output).start();
			new SensorThread("Temperature & Humidity", 2, 2000, 2, sharedQueue, output).start();

			// Tạo thread tải dữ liệu lên cloud
			new CloudUploadThread("Cloud Upload 1", sharedQueue, output, uploadSemaphore).start();

			// Đảm bảo chương trình vẫn chạy và kết nối không bị đóng
			while (true) {
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
