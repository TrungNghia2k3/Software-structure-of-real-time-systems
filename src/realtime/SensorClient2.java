// =============================
// SensorClient2.java (Client 2: Water Level, Cloud Upload)
// =============================
package realtime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class SensorClient2 {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 6666)) {
			System.out.println("Client 2 connected to server!");
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			new SensorThread("Water Level", 1, output, 3000, 1, null).start(); // 3 giây
			new SensorThread("Cloud Upload", 1, output, 1500, 1, new Semaphore(1)).start(); // 1.5 giây

			while (true) {
				Thread.sleep(1000); // giữ cho chương trình chạy liên tục
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}