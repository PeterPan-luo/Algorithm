package com.javase.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8989);
			Socket socket = null;
			while ((socket = serverSocket.accept()) != null) {
				new HttpThread(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	
}

//线程类，Http处理线程
class HttpThread extends Thread{
	private Socket socket;
	public HttpThread(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(os);
			//往输出流进行写
			writer.println("<html>");
			writer.println("<body>");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			
			writer.println("hello, this is my web page time now: " + df.format(new Date()));
			writer.println("<body>");
			writer.println("<html>");
			//清空缓存
			writer.flush();
			//关闭
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
