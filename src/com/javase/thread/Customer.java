package com.javase.thread;

public class Customer implements Runnable{

	private Store store;
	public Customer(Store store) {
		this.store = store;
	}
	@Override
	public void run() {
		while (true) {
			//往仓库取走货物
			store.remove();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
