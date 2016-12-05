package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
	private static int MAX_THREAD_NUM = 5;

	public static void main(String[] args) throws InterruptedException {
		// ��ʼ�ź�
		final CountDownLatch beginSignalLatch = new CountDownLatch(1);

		// �����ź�
		final CountDownLatch doneSignalLatch = new CountDownLatch(MAX_THREAD_NUM);

		ExecutorService executor = Executors.newCachedThreadPool();

		for (int i = 0; i < MAX_THREAD_NUM; i++) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("�߳�: ��" + Thread.currentThread().getId() + "���Ѿ�׼����.");
						beginSignalLatch.await();
						System.out.println("�߳�: ��" + Thread.currentThread().getId() + "����ʼ����()");
						TimeUnit.SECONDS.sleep(2);
						System.out.println("�߳�: ��" + Thread.currentThread().getId() + "�������յ�[]");

						doneSignalLatch.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}

		TimeUnit.SECONDS.sleep(2);
		
		System.out.println();
		System.out.println("�ȵ�����Ա����ǹ��()...");
		System.out.println();
		
		beginSignalLatch.countDown();

		TimeUnit.SECONDS.sleep(3);
		doneSignalLatch.await();

		System.out.println();
		System.out.println("����ѡ�ֶ��Ѿ��ﵽ�յ�.");
		
		executor.shutdown();

	}

}
