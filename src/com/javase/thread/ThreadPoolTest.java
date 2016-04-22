package com.javase.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	private static int produceTaskSleepTime = 2000;
	
	public static void main(String[] args) {
		
		//构造一个线程池
		ThreadPoolExecutor producerPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(3), new ThreadPoolExecutor.DiscardOldestPolicy());
		//每隔produceTaskSleepTime时间向线程派送一个任务
		int i = 0;
		while(true){
			try {
				Thread.sleep(produceTaskSleepTime);
				String task = "Task@" + i;
				System.out.println("put " + task);
				//用execute启动一个线程
				producerPool.execute(new ThreadPoolTask(task));
				i++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
