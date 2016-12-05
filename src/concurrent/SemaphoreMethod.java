package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreMethod {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(100);
		// Ïß³Ì³Ø
		ExecutorService exec = Executors.newCachedThreadPool();

		final ReentrantLock lock = new ReentrantLock();

		for (int i = 0; i < 500; i++) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();
						System.out.println("availablePermits: " + semaphore.availablePermits() + " -hasQueuedThreads: "
								+ semaphore.hasQueuedThreads());
						TimeUnit.SECONDS.sleep(3);
						
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

}
