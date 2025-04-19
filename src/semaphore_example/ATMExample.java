package semaphore_example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class ATMExample {
	public static void main(String[] args) {
		final Semaphore cayATM = new Semaphore(4); // 4 ATM
		final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

		System.out.println("Tổng số lượng cây ATM hiện có: " + cayATM.availablePermits());

		for (int i = 1; i <= 6; i++) {
			String userName = "Người dùng " + i;
			queue.add(userName); // thêm vào hàng đợi
			new NguoiDungATM(cayATM, queue, userName).start();
		}
	}
}
