package realtime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class SensorWorkerThread extends Thread {
	private final String sensorName;
	private final DataOutputStream output;
	private final int interval;
	private final Random random = new Random();

	public SensorWorkerThread(String sensorName, DataOutputStream output, int interval, int priority) {
		this.sensorName = sensorName;
		this.output = output;
		this.interval = interval;
		setPriority(priority);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String value = generateSensorValue();
				synchronized (output) {
					output.writeUTF(sensorName + ": " + value);
					output.flush();
				}
				Thread.sleep(interval);
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(sensorName + " stopped.");
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
