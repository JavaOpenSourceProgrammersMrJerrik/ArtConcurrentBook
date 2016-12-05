package concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreA {

	public static void main(String[] args) {
		final Semaphore semaphore = new Semaphore(1);

		final ReentrantLock lock = new ReentrantLock();
		final ReentrantLock lock2 = new ReentrantLock();

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						try {
							lock.lock();
							semaphore.acquire();
							System.out.println("---acquire 线程: " + Thread.currentThread().getName() + "获取资源");
							Thread.sleep(2000);
						} finally {
							lock.unlock();
						}
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						try {
							lock2.lock();
							System.out.println("+++realease 线程: " + Thread.currentThread().getName() + "释放资源");
							semaphore.release();
							println(2);
						} finally {
							lock2.unlock();
						}
					}
				}
			});
		}

		executor.shutdown();
	}

	public static void println(int cycle) {
		for (int i = 0; i < cycle; i++) {
			System.out.println();
		}
	}
}
