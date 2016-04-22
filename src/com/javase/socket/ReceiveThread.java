package com.javase.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ReceiveThread extends Thread{
	DatagramSocket ds;
	
	public ReceiveThread(int port) {
		super();
		try {
			ds = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			byte[] buff = new byte[1024];
			//创建一个数据包对象
			DatagramPacket packet = new DatagramPacket(buff, 1024);
			//一直循环
			while (true) {
				//接收消息
				ds.receive(packet);
				String str = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Receive: " + str);
	
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			ds.close();
		}
		
	}
}
