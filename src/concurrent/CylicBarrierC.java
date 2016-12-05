package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CylicBarrierC {

	public static void main(String[] args) throws InterruptedException {
		final CyclicBarrier barrier = new CyclicBarrier(3);

		ExecutorService executor = Executors.newCachedThreadPool();

		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("first thread");
			}
		});

		// TimeUnit.SECONDS.sleep(2);

		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					barrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("second thread");
			}
		});

		// TimeUnit.SECONDS.sleep(2);

		executor.execute(new Runnable() {

			@Override
			public void run() {
				/*
				 * try { barrier.await(); } catch (InterruptedException e) {
				 * e.printStackTrace(); } catch (BrokenBarrierException e) {
				 * e.printStackTrace(); }
				 */
				System.out.println("third thread");
			}
		});

		TimeUnit.SECONDS.sleep(5);
		try {
			barrier.await();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		System.out.println("所有线程结束()");

		executor.shutdown();
	}

}
