package thread_runnable_example;

public class RunnableExample implements Runnable {
	public static void main(String[] args) {
		RunnableExample obj = new RunnableExample();
		Thread thread = new Thread(obj);
		thread.start();
		System.out.println("This code is outside of the thread");
	}

	public void run() {
		System.out.println("This code is running in a thread");
	}

}
