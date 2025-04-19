package project;

import java.io.DataOutputStream;
import java.util.concurrent.Semaphore;

public class CloudUploadThread extends Thread {
	private final String threadName;
	private final SharedSensorQueue sharedQueue;
	private final DataOutputStream output;
	private final Semaphore semaphore;
	private final int interval = 1500; // hoặc nhận từ constructor nếu cần

	public CloudUploadThread(String threadName, SharedSensorQueue sharedQueue, DataOutputStream output,
			Semaphore semaphore) {
		this.threadName = threadName;
		this.sharedQueue = sharedQueue;
		this.output = output;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String data = sharedQueue.getData(); // blocking lấy dữ liệu
				semaphore.acquire();
				try {
					String message = threadName + " gui len server: " + data;
					output.writeUTF(message);
					output.flush();
				} finally {
					semaphore.release();
				}
				Thread.sleep(interval);
			}
		} catch (Exception e) {
			System.err.println(threadName + " bi loi khi gui du lieu:");
			e.printStackTrace();
		}
	}

}
