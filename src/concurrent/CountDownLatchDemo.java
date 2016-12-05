package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	private static int MAX_THREAD_NUM = 5;

	public static void main(String[] args) throws InterruptedException {
		// 开始信号
		final CountDownLatch beginSignalLatch = new CountDownLatch(1);

		// 结束信号
		final CountDownLatch doneSignalLatch = new CountDownLatch(MAX_THREAD_NUM);

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < MAX_THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("线程: 【" + Thread.currentThread().getId() + "】已经准备好.");
						beginSignalLatch.await();
						System.out.println("线程: 【" + Thread.currentThread().getId() + "】开始起跑()");
						TimeUnit.SECONDS.sleep(2);
						System.out.println("线程: 【" + Thread.currentThread().getId() + "】到达终点[]");

						doneSignalLatch.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		TimeUnit.SECONDS.sleep(2);
		
		System.out.println();
		System.out.println("等到裁判员号令枪响()...");
		System.out.println();
		
		beginSignalLatch.countDown();

		TimeUnit.SECONDS.sleep(3);
		doneSignalLatch.await();

		System.out.println();
		System.out.println("所有选手都已经达到终点.");
		
		executor.shutdown();

	}

}
