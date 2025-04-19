package project;

import java.io.DataOutputStream;

public class SensorThread extends Thread {
	private final String sensorType;
	private final int sensorCount;
	private final int interval;
	private final int priority;
	private final SharedSensorQueue sharedQueue;
	private final DataOutputStream output;

	public SensorThread(String sensorType, int sensorCount, int interval, int priority, SharedSensorQueue sharedQueue, DataOutputStream output) {
		this.sensorType = sensorType;
		this.sensorCount = sensorCount;
		this.interval = interval;
		this.priority = priority;
		this.sharedQueue = sharedQueue;
		this.output = output;
	}

	@Override
	public void run() {
		for (int i = 1; i <= sensorCount; i++) {
			String sensorName = sensorType + " " + i;
			new SensorWorkerThread(sensorName, interval, priority, sharedQueue, output).start();
		}
	}
}
