// =============================
// SensorThread.java (Thread chung cho các cảm biến)
// =============================
package realtime;

import java.io.DataOutputStream;
import java.util.concurrent.Semaphore;

public class SensorThread extends Thread {
	private final String sensorType;
	private final int sensorCount;
	private final DataOutputStream output;
	private final int interval;
	private final int priority;
	private final Semaphore semaphore;

	public SensorThread(String sensorType, int sensorCount, DataOutputStream output, int interval, int priority,
			Semaphore semaphore) {
		this.sensorType = sensorType;
		this.sensorCount = sensorCount;
		this.output = output;
		this.interval = interval;
		this.priority = priority;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		for (int i = 1; i <= sensorCount; i++) {
			String sensorName = sensorType + " " + i;
			new SensorWorkerThread(sensorName, output, interval, priority, semaphore).start();
		}
	}
}
