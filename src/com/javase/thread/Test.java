package com.javase.thread;

public class Test {

	//对生产者 消费者进行测试
	public static void main(String[] args) {
		Store store = new Store(5);
		//创建生产者 消费者
		Thread pro = new Thread(new Producer(store));
		Thread cus = new Thread(new Customer(store));
		Thread pro2 = new Thread(new Producer(store));
		Thread cus2 = new Thread(new Customer(store));
		
		pro.setName("Producer");
		pro2.setName("Producer 2");
		cus.setName("Customer");
		cus.setName("Customer 2");
		
		//启动各个线程
		pro.start();
		pro2.start();
		cus.start();
		cus2.start();
	}
}
