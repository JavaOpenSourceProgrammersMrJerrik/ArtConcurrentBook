package queue;

import java.util.concurrent.TimeUnit;

public class LocalQueueTest {

	public static void main(String[] args) throws InterruptedException {
		final LocalQueue localQueue = new LocalQueue(5L);

		Thread putThread = new Thread(new Runnable() {
			@Override
			public void run() {
				localQueue.put("a");
				localQueue.put("b");
				localQueue.put("c");
				localQueue.put("d");
				localQueue.put("e");
				localQueue.put("f");
				localQueue.put("h");
			}
		}, "putThread");

		putThread.start();

		System.out.println("开始休眠...");
		TimeUnit.SECONDS.sleep(3);

		Thread takeThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Object obj = localQueue.take();
				System.out.println("取出一个元素: " + obj);
				Object obj2 = localQueue.take();
				System.out.println("取出一个元素: " + obj2);
			}
		}, "takeThread");

		takeThread.start();
	}

}
