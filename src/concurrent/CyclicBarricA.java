package concurrent;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarricA {

	public static void main(String[] args) throws IOException, InterruptedException {
		// �����������Ϊ4����������ֻ������3��ѡ�֣�����Զ�ȴ���ȥ
		// Waits until all parties have invoked await on this barrier.
		CyclicBarrier barrier = new CyclicBarrier(3);

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new Thread(new RunnerWithBarrier(barrier, "1��ѡ��")));
		executor.submit(new Thread(new RunnerWithBarrier(barrier, "2��ѡ��")));
		executor.submit(new Thread(new RunnerWithBarrier(barrier, "3��ѡ��")));

		executor.shutdown();
	}
}

class RunnerWithBarrier implements Runnable {
	// һ��ͬ�������࣬������һ���̻߳���ȴ���ֱ������ĳ���������ϵ� (common barrier point)
	private CyclicBarrier barrier;

	private String name;

	public RunnerWithBarrier(CyclicBarrier barrier, String name) {
		super();
		this.barrier = barrier;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000 * (new Random()).nextInt(8));
			System.out.println(name + " ׼������...");
			// barrier��await�����������в����߶��Ѿ��ڴ� barrier �ϵ��� await ����֮ǰ����һֱ�ȴ���
			System.out.println("getNumberWaiting==" + barrier.getNumberWaiting());
			barrier.await(); // �����е��̶߳�������ʱ,���ͷ���
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(name + " ���ܣ�");
	}
}