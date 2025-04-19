package project;

import java.util.LinkedList;
import java.util.Queue;

public class SharedSensorQueue {
	private final Queue<String> queue = new LinkedList<>();

	public synchronized void addData(String data) {
		queue.add(data);
		notify(); // đánh thức thread đang chờ getData
	}

	public synchronized String getData() throws InterruptedException {
		while (queue.isEmpty()) {
			wait(); // chờ cho đến khi có dữ liệu
		}
		return queue.poll();
	}
}
