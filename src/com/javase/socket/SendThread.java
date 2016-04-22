package com.javase.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

//发送线程类
public class SendThread extends Thread{

	private DatagramSocket ds;
	private int sendPort;
	public SendThread(int port, int sendPort) {
		this.sendPort = sendPort;
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
			//循环接收用户输入的信息
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String str = null;
			while ((str = reader.readLine()) != null) {
				//创建Packet
				DatagramPacket packet = new DatagramPacket(str.getBytes(), str.length(), 
						InetAddress.getByName("localhost"), sendPort);
				ds.send(packet);
				System.out.println("Send: " + str);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			ds.close();
		}
	}
}
