package project;

import java.io.DataOutputStream;
import java.util.Random;

public class SensorWorkerThread extends Thread {
	private final String sensorName;
	private final int interval;
	private final int priority;
	private final SharedSensorQueue sharedQueue;
	private final DataOutputStream output;
	private final Random random = new Random();

	public SensorWorkerThread(String sensorName, int interval, int priority, SharedSensorQueue sharedQueue, DataOutputStream output) {
		this.sensorName = sensorName;
		this.interval = interval;
		this.priority = priority;
		this.sharedQueue = sharedQueue;
		this.output = output;
		setPriority(priority);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String value = generateSensorValue();
				String message = sensorName + ": " + value;
				System.out.println(message);

				// Chỉ push vào hàng đợi nếu là cảm biến thật, không phải cloud
				if (!sensorName.startsWith("Cloud Upload") && sharedQueue != null) {
					sharedQueue.addData(message);
				}

				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			System.out.println(sensorName + " bi dung.");
		}
	}

	private String generateSensorValue() {
		if (sensorName.startsWith("Soil Moisture"))
			return random.nextInt(100) + "%";
		if (sensorName.startsWith("Light Intensity"))
			return random.nextInt(100) + "%";
		if (sensorName.startsWith("Temperature & Humidity"))
			return (20 + random.nextInt(10)) + "C, Humidity: " + (40 + random.nextInt(30)) + "%";
		if (sensorName.startsWith("Water Level"))
			return random.nextBoolean() ? "Low" : "Normal";
		if (sensorName.startsWith("Cloud Upload"))
			return "Sending data to cloud...";
		return "Unknown";
	}
}
