package com.javase.thread;

/**
 * 仓库类
 */
public class Store {

	private final int MAX_SIZE; //仓库最大容量
	private int count;//当前货物数量
	public Store(int n) {
		MAX_SIZE = n;
		count = 0;
	}
	
	public synchronized void add(){
		while(count >= MAX_SIZE){
			System.out.println("已经满了");
			try {
				//如果仓库满了，就进入等待
				this.wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		count++;
		System.out.println(Thread.currentThread().toString() + " put " + count);
		//仓库已经有东西可以取了，则通知所有消费者线程来消费
		this.notifyAll();
	}
	public synchronized void remove(){
		while(count <=0){
			System.out.println("空了");
			try {
				//如果为空，就进入等待
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count--;
		System.out.println(Thread.currentThread().toString() + " get " + count);
		//仓库还没满，通知生成者添加货物
		this.notify();
	}
}
