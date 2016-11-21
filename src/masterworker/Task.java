package masterworker;

import java.io.Serializable;

public class Task implements Serializable {

	private String taskName;

	private String result;

	public Task(String taskName, String result) {
		super();
		this.taskName = taskName;
		this.result = result;
	}

	public Task() {
		super();

	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void show() {
		for (int i = 0; i < 3; i++) {
			final int index = i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("index: " + index);
				}
			});
		}
	}

	@Override
	public String toString() {
		return "Task [taskName=" + taskName + ", result=" + result + "]";
	}

	public class TaskInner {
		private Long id;
	}

}
