package masterworker;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Worker implements Runnable {
	private LinkedBlockingQueue<Task> workQueue;

	private Map<String, Object> resultMap;

	private CyclicBarrier barrier;

	@Override
	public void run() {
		while (true) {
			Task task;
			try {
				task = workQueue.take();
				task = handle(task);
				resultMap.put("处理后的任务: " + task.getTaskName(), task);
				barrier.await();
			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			}

		}
	}

	private Task handle(Task task) {
		System.out.println("handle task: " + task);
		return task;
	}

	public void setWorkQueue(LinkedBlockingQueue<Task> workQueue) {
		this.workQueue = workQueue;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public void setBarrier(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
}
