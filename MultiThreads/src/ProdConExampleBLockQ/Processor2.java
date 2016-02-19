package ProdConExampleBLockQ;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by root on 18/2/16.
 */
public class Processor2 {

	private BlockingQueue<Integer> queue = new ArrayBlockingQueue(10);

	public void produce() throws InterruptedException {
		Random random = new Random();

		while (true){
			queue.put(random.nextInt(10));
		}
	}

	public void consume() throws InterruptedException {
		Random random = new Random();

		while (true){
			Thread.sleep(100);
			if (random.nextInt(10)==0) {
				Integer value = queue.take();
				System.out.println("Taken value : " + value + " Queue size is : "+ queue.size());
			}
		}
	}
}
