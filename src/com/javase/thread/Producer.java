package com.javase.thread;

public class Producer implements Runnable{

	private Store store;
	public Producer(Store store) {
		this.store = store;
	}
	@Override
	public void run() {
		while (true) {
			//往仓库添加货物
			store.add();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
