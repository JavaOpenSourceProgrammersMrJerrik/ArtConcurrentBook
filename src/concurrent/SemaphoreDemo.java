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
 * 信号量使用详解,使用信号量来管理有限的资源 User: ketqi Date: 2013-01-27 12:29
 */
public class SemaphoreDemo {
	/** 可重入锁,对资源列表进行同步 */
	private final ReentrantLock lock = new ReentrantLock();
	/** 信号量 */
	private final Semaphore semaphore;
	/** 可使用的资源列表 */
	private final LinkedList<Object> resourceList = new LinkedList<Object>();

	public SemaphoreDemo(Collection<Object> resourceList) {
		this.resourceList.addAll(resourceList);
		this.semaphore = new Semaphore(resourceList.size(), true);
	}

	/**
	 * 获取资源
	 *
	 * @return 可用的资源
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
	 * 释放或者归还资源
	 *
	 * @param resource
	 *            待释放或归还的资源
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
		// 准备2个可用资源
		List<Object> resourceList = new ArrayList<Object>();
		resourceList.add("Element-1");
		resourceList.add("Element-2");
		resourceList.add("Element-3");

		// 准备工作任务
		final SemaphoreDemo demo = new SemaphoreDemo(resourceList);
		Runnable worker = new Runnable() {
			@Override
			public void run() {
				Object resource = null;
				try {
					// 获取资源
					resource = demo.acquireResource();
					System.out.println(Thread.currentThread().getName() + "\twork   on\t" + resource);
					// 用resource做工作
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					// 归还资源
					if (resource != null) {
						demo.releaseResource(resource);
					}
				}
			}
		};

		// 启动9个任务
		ExecutorService service = Executors.newCachedThreadPool();
		for (int i = 0; i < 9; i++) {
			service.submit(worker);
		}
		service.shutdown();
	}
}