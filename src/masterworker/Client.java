package masterworker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Client {

	public static void main(String[] args) throws InterruptedException {
		final Worker worker = new Worker();

		final Master master = new Master(worker, 5);

		for (int i = 0; i < 100; i++) {
			Task task = new Task();
			task.setTaskName("task-" + i);
			task.setTaskName("name-" + UUID.randomUUID());
			master.submitTask(task);
		}

		System.out.println("总共workers数量: " + master.workerCount());
		System.out.println("队列总共任务数: " + master.taskCount());
		master.execute();

		TimeUnit.SECONDS.sleep(2);
		System.out.println("ResultMap: " + master.getResultMap().size());
	}

}
