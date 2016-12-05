package chapter01;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r,"我的线程工厂");
	}

}
