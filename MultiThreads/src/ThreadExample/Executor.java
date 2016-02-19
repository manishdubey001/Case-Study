package ThreadExample;

import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 17/2/16.
 */
public class Executor {

	public static Queue<Integer> queue;

	public static void main(String[] args) {


		queue.add(1);

		System.out.println("Start");

//		ExecutorService executorService = Executors.newFixedThreadPool(2);
//		ExecutorService executorService = Executors.newCachedThreadPool();
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		executorService.execute(new Task("Evans"));
		executorService.execute(new Task("Leroy"));
		executorService.execute(new Task("Sweta"));

		executorService.shutdown();

		System.out.println("Stop");
	}
}
