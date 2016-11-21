package chapter01;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DealLockDemo2 {
	private static Object lockA = new Object();
	private static Object lockB = new Object();

	private static Lock timeoutLock = new ReentrantLock();

	public static void main(String[] args) {
		ThreadFactory factory = new MyThreadFactory();
		Thread threadA = factory.newThread(new Runnable() {
			@Override
			public void run() {
				synchronized (lockA) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (lockB) {
						System.out.println("我是线程A");
					}

				}
			}
		});

		Thread threadB = factory.newThread(new Runnable() {
			@Override
			public void run() {
				synchronized (lockB) {
					synchronized (lockA) {
						System.out.println("我是线程B");
					}
				}
			}
		});

		threadA.start();
		threadB.start();
	}

}
