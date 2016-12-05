package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(3);

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			final int index = i;
			executor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " has ready.");
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					latch.countDown();
				}
			});
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("所有线程都已经准备,各就各位...跑");

		executor.shutdown();
	}

}
