package com.javase.socket;

public class ChatTest {

	public static void main(String[] args) {
		//args format: 接收端口、发送端口、对方接收端口
		//启动接收线程
		new ReceiveThread(Integer.parseInt(args[0])).start();
		//启动发送线程
		new SendThread(Integer.parseInt(args[1]), Integer.parseInt(args[2])).start();
	}
}
