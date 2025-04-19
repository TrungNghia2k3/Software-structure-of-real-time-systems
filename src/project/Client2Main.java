package project;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Client2Main {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 8888);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			SharedSensorQueue sharedQueue = new SharedSensorQueue();
			Semaphore uploadSemaphore = new Semaphore(1); // 1 permit = gửi tuần tự

			// Sensor Threads for Client 2
			new SensorThread("Light Intensity", 1, 500, 1, sharedQueue, output).start();
			new SensorThread("Water Level", 1, 3000, 1, sharedQueue, output).start();

			// Cloud Upload Thread
			new CloudUploadThread("Cloud Upload 2", sharedQueue, output, uploadSemaphore).start();

			// Đợi mãi (giữ socket mở)
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
