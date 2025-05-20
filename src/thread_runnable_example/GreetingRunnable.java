package thread_runnable_example;

class GreetingRunnable implements Runnable {
	private String person;
	private Integer sleepTime;

	public GreetingRunnable(String person, Integer sleepTime) {
		this.person = person;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(person + " đang chào... Lần " + i);
			try {
				Thread.sleep(sleepTime); // Tạm dừng để mô phỏng thời gian phản hồi khác nhau
			} catch (InterruptedException e) {
				System.out.println(person + " bị gián đoạn.");
			}
		}
		System.out.println(person + " đã hoàn thành việc chào hỏi.");
	}

	public static void main(String[] args) {
		// Tạo các đối tượng Runnable với thời gian ngủ khác nhau
		GreetingRunnable greetOng = new GreetingRunnable("Ông", 2000);
		GreetingRunnable greetBa = new GreetingRunnable("Bà", 4000);
		GreetingRunnable greetChi = new GreetingRunnable("Chị", 6000);

		// Tạo luồng và truyền các đối tượng Runnable
		Thread thread1 = new Thread(greetOng);
		Thread thread2 = new Thread(greetBa);
		Thread thread3 = new Thread(greetChi);

		// Bắt đầu các luồng
		thread1.start();
		thread2.start();
		thread3.start();

		System.out.println("Luồng chính tiếp tục thực thi...");
	}
}
