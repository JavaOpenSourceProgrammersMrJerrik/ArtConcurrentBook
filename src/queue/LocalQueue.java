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
	
	//step 1 ���objΪNull,��ֹͣ����
	//step 2 �����ǰ��������,������ ���������Ԫ�� ����take�߳�
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
	
	//step 1 �����ǰ���в�Ϊ��,��ȡ����һ��Ԫ�� ��������ֱ����Ԫ�����
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
