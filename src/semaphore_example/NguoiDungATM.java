package semaphore_example;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

class NguoiDungATM extends Thread {
	private final Semaphore semaphore;
	private final BlockingQueue<String> queue;
	private final String name;

	public NguoiDungATM(Semaphore semaphore, BlockingQueue<String> queue, String name) {
		this.semaphore = semaphore;
		this.queue = queue;
		this.name = name;
	}

	public void run() {
		try {
			// Chờ đến lượt của mình trong hàng đợi
			while (!queue.peek().equals(name)) {
				Thread.sleep(10); // chờ một chút rồi kiểm tra lại
			}

			System.out.println(name + ": Đang chờ để sử dụng ATM...");

			semaphore.acquire(); // Xin phép sử dụng ATM

			try {
				queue.remove(); // Đến lượt mình => ra khỏi hàng đợi
				System.out.println(name + ": Đã vào ATM! Bắt đầu giao dịch...");
				System.out.println(name + ": Số lượng ATM còn lại: " + semaphore.availablePermits());

				Thread.sleep(100 + new Random().nextInt(200)); // Giao dịch
			} finally {
				System.out.println(name + ": Giao dịch xong. Rời khỏi ATM...");
				System.out.println(name + ": ATM hiện còn: " + (semaphore.availablePermits()+1));
				semaphore.release(); // Nhường lại ATM
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
