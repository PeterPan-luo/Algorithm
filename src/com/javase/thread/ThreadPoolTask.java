package com.javase.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTask implements Runnable{

	private static int consumeTaskSleepTime = 2000;
	private String threadPoolTaskData;
	public ThreadPoolTask(String task) {
		threadPoolTaskData = task;
	}
	
	public void run() {

		System.out.println("start.." + threadPoolTaskData);
		try {
			Thread.sleep(consumeTaskSleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		threadPoolTaskData = null;
	}
	

}
