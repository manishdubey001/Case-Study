package ProdConExampleBLockQ;

/**
 * Created by root on 18/2/16.
 */
public class App2 {

	public static void main(String[] args) {

		final Processor2 processor = new Processor2();

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.produce();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					processor.consume();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}
}
