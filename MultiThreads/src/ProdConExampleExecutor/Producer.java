package ProdConExampleExecutor;

/**
 * Created by root on 19/2/16.
 */
public class Producer implements Runnable{

	private Broker broker;

	public Producer(Broker broker){
		this.broker = broker;
	}

	@Override
	public void run() {
		try {
			for (int i = 1; i < 6; ++i) {
				Thread.sleep(100);
				broker.put(i);
			}
		}catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
