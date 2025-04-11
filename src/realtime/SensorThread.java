// =============================
// SensorThread.java (Thread chung cho các cảm biến)
// =============================
package realtime;

import java.io.DataOutputStream;

public class SensorThread extends Thread {
	private final String sensorType;
	private final int sensorCount;
	private final DataOutputStream output;
	private final int interval;
	private final int priority;

	public SensorThread(String sensorType, int sensorCount, DataOutputStream output, int interval, int priority) {
		this.sensorType = sensorType;
		this.sensorCount = sensorCount;
		this.output = output;
		this.interval = interval;
		this.priority = priority;
	}

	@Override
	public void run() {
		for (int i = 1; i <= sensorCount; i++) {
			String sensorName = sensorType + " " + i;
			new SensorWorkerThread(sensorName, output, interval, priority).start();
		}
	}
}
