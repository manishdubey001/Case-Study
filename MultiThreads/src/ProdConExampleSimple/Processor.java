package ProdConExampleSimple;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by root on 18/2/16.
 */
public class Processor {

	private LinkedList<Integer> list = new LinkedList();
	private final int LIMIT = 10;
	private Object lock = new Object();

	public void produce() throws InterruptedException {

		int value = 0;
		while (true) {
			synchronized (lock) {
				while (list.size() == LIMIT) {
					lock.wait();
				}
				list.add(value++);
			}
		}
	}

	public void consume() throws InterruptedException {
		while (true) {
			synchronized (lock) {
				while (list.size() == 0) {
					lock.wait();
				}
				System.out.println("List size is " + list.size());
				int value = list.removeFirst();
				System.out.println("Value is " + value);
				lock.notify();
			}
			Thread.sleep(new Random().nextInt(1000));
		}
	}
}
