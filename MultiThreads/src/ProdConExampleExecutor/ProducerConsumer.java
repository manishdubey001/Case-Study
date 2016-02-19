package ProdConExampleExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by root on 19/2/16.
 */
public class ProducerConsumer {
	public static void main(String[] args) {
		try {
			Broker broker = new Broker();
			ExecutorService executorService = Executors.newFixedThreadPool(3);

			executorService.execute(new Consumer("1", broker));
			executorService.execute(new Consumer("2", broker));

			Future prodStatus = executorService.submit(new Producer(broker));

			prodStatus.get();

			executorService.shutdown();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
