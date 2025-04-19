// =============================
// SensorClient1.java (Client 1: Soil Moisture, Temp & Humidity, Light)
// =============================
package realtime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SensorClient1 {
	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 6666)) {
			System.out.println("Client 1 connected to server!");
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			new SensorThread("Soil Moisture", 3, output, 1000, 3, null).start(); // 1 giây
			new SensorThread("Temperature & Humidity", 2, output, 2000, 2, null).start(); // 2 giây
			new SensorThread("Light Intensity", 1, output, 500, 1, null).start(); // 0.5 giây

			while (true) {
				Thread.sleep(1000); // giữ cho chương trình chạy liên tục
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}