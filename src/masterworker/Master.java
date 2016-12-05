package masterworker;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;

public class Master {

	private LinkedBlockingQueue<Task> workQueue = new LinkedBlockingQueue<Task>();

	private Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

	private Set<Thread> workers = new HashSet<Thread>();

	private final CyclicBarrier barrier;

	public void submitTask(Task task) throws InterruptedException {
		workQueue.put(task);
	}

	public Master(Worker worker, int workerCount) {
		barrier = new CyclicBarrier(workerCount);
		worker.setWorkQueue(workQueue);
		worker.setResultMap(resultMap);
		worker.setBarrier(barrier);

		for (int i = 0; i < workerCount; i++) {
			workers.add(new Thread(worker, "Thread-" + Integer.toString(i)));
		}
	}

	public void execute() {
		for (Thread thread : workers) {
			thread.start();
		}
	}

	public int workerCount() {
		return workers.size();
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public int taskCount() {
		return workQueue.size();
	}

	public boolean isCompletely() {
		for (Thread thread : workers) {
			if (thread.getState() != Thread.State.TERMINATED) {
				return false;
			}
		}
		return true;
	}
	

}
