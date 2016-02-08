package com.caseStudy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 5/2/16.
 */
public class ThreadPool {

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(10);
//		service.execute();
		for (int i =0; i<10 ; i++)
			service.submit(new Task(i));
	}
}

class Task implements Runnable{

	private int taskId;

	public Task(int id){
		this.taskId = id;
	}

	@Override
	public void run() {
		System.out.println("Task Id : "+ this.taskId + " performed by "+ Thread.currentThread().getName());
	}
}
