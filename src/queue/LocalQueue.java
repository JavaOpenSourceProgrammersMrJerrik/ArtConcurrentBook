package queue;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class LocalQueue {
	private LinkedList<Object> container = new LinkedList<Object>();

	private AtomicLong counter = new AtomicLong(0);

	private Long minSize = 0L;

	private Long maxSize;
	
	private Object lock = new Object();

	public LocalQueue() {
		this.maxSize = Long.MAX_VALUE;
	}

	public LocalQueue(Long minSize, Long maxSize) {
		super();
		this.minSize = minSize;
		this.maxSize = maxSize;
	}

	public LocalQueue(Long maxSize) {
		super();
		this.maxSize = maxSize;
	}
	
	//step 1 如果obj为Null,则停止插入
	//step 2 如果当前队列已满,则阻塞 否则添加新元素 唤醒take线程
	public void put(Object obj){
		if(null == obj) return;
		synchronized (lock) {
			if(counter.get() == maxSize){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.container.add(obj);
			counter.getAndIncrement();
			this.lock.notify();
		}
	}
	
	//step 1 如果当前队列不为空,则取出第一个元素 否则阻塞直到有元素添加
	public Object take(){
		Object result = null;
		synchronized (lock) {
			if(counter.get() == 0L){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			result = this.container.removeFirst();
			counter.getAndDecrement();
			this.lock.notify();
		}
		return result;
	}

	public Long getMinSize() {
		return minSize;
	}

	public void setMinSize(Long minSize) {
		this.minSize = minSize;
	}

	public Long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(Long maxSize) {
		this.maxSize = maxSize;
	}

	public AtomicLong getCounter() {
		return counter;
	}
}
