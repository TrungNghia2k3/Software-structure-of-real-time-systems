package realtime;

class MyRunnable implements Runnable {

	private String threadName;
	private Integer sleepTime;

	public MyRunnable(String name, Integer sleepTime) {
		this.threadName = name;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println(threadName + " is running... iteration " + i);
			try {
				Thread.sleep(sleepTime); // Simulate work with different sleep times
			} catch (InterruptedException e) {
				System.out.println(threadName + " interrupted.");
			}
		}
		System.out.println(threadName + " has finished execution.");
	}
}

public class Main {
	public static void main(String[] args) {
		// Creating Runnable instances with different sleep times
		MyRunnable runnable1 = new MyRunnable("Runnable 1", 200);
		MyRunnable runnable2 = new MyRunnable("Runnable 2", 400);
		MyRunnable runnable3 = new MyRunnable("Runnable 3", 600);

		// Creating Thread instances and passing Runnable objects
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		Thread thread3 = new Thread(runnable3);

		// Start the threads
		thread1.start();
		thread2.start();
		thread3.start();

		System.out.println("Main thread execution continues...");
	}
}

//class GreetingRunnable implements Runnable {
//	private String person;
//	private Integer sleepTime;
//
//	public GreetingRunnable(String person, Integer sleepTime) {
//		this.person = person;
//		this.sleepTime = sleepTime;
//	}
//
//	@Override
//	public void run() {
//		for (int i = 1; i <= 5; i++) {
//			System.out.println(person + " đang chào... Lần " + i);
//			try {
//				Thread.sleep(sleepTime); // Tạm dừng để mô phỏng thời gian phản hồi khác nhau
//			} catch (InterruptedException e) {
//				System.out.println(person + " bị gián đoạn.");
//			}
//		}
//		System.out.println(person + " đã hoàn thành việc chào hỏi.");
//	}
//}
//
//public class Main {
//	public static void main(String[] args) {
//		// Tạo các đối tượng Runnable với thời gian ngủ khác nhau
//		GreetingRunnable greetOng = new GreetingRunnable("Ông", 2000);
//		GreetingRunnable greetBa = new GreetingRunnable("Bà", 4000);
//		GreetingRunnable greetChi = new GreetingRunnable("Chị", 6000);
//
//		// Tạo luồng và truyền các đối tượng Runnable
//		Thread thread1 = new Thread(greetOng);
//		Thread thread2 = new Thread(greetBa);
//		Thread thread3 = new Thread(greetChi);
//
//		// Bắt đầu các luồng
//		thread1.start();
//		thread2.start();
//		thread3.start();
//
//		System.out.println("Luồng chính tiếp tục thực thi...");
//	}
//}
