package thread_runnable_example;

public class ThreadExample extends Thread {
	public static void main(String[] args) {
		ThreadExample thread = new ThreadExample();
		thread.start();
		System.out.println("This code is outside of the thread");
	}

	public void run() {
		System.out.println("This code is running in a thread");
	}
}
