package concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * �ź���ʹ�����,ʹ���ź������������޵���Դ User: ketqi Date: 2013-01-27 12:29
 */
public class SemaphoreDemo {
	/** ��������,����Դ�б����ͬ�� */
	private final ReentrantLock lock = new ReentrantLock();
	/** �ź��� */
	private final Semaphore semaphore;
	/** ��ʹ�õ���Դ�б� */
	private final LinkedList<Object> resourceList = new LinkedList<Object>();

	public SemaphoreDemo(Collection<Object> resourceList) {
		this.resourceList.addAll(resourceList);
		this.semaphore = new Semaphore(resourceList.size(), true);
	}

	/**
	 * ��ȡ��Դ
	 *
	 * @return ���õ���Դ
	 * @throws InterruptedException
	 */
	public Object acquireResource() throws InterruptedException {
		semaphore.acquire();

		lock.lock();
		try {
			return resourceList.pollFirst();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * �ͷŻ��߹黹��Դ
	 *
	 * @param resource
	 *            ���ͷŻ�黹����Դ
	 */
	public void releaseResource(Object resource) {
		lock.lock();
		try {
			resourceList.addLast(resource);
		} finally {
			lock.unlock();
		}

		semaphore.release();
	}

	public static void main(String[] args) {
		// ׼��2��������Դ
		List<Object> resourceList = new ArrayList<Object>();
		resourceList.add("Element-1");
		resourceList.add("Element-2");
		resourceList.add("Element-3");

		// ׼����������
		final SemaphoreDemo demo = new SemaphoreDemo(resourceList);
		Runnable worker = new Runnable() {
			@Override
			public void run() {
				Object resource = null;
				try {
					// ��ȡ��Դ
					resource = demo.acquireResource();
					System.out.println(Thread.currentThread().getName() + "\twork   on\t" + resource);
					// ��resource������
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// �黹��Դ
					if (resource != null) {
						demo.releaseResource(resource);
					}
				}
			}
		};

		// ����9������
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 9; i++) {
			service.submit(worker);
		}
		service.shutdown();
	}
}