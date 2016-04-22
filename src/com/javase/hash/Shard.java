package com.javase.hash;

public class Shard {

	private String name;
	private String password;
	private String ip;
	private int port;
	public Shard() {
		// TODO Auto-generated constructor stub
	}
	public Shard(String name, String password, String ip, int port) {
		super();
		this.name = name;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}
	public Shard(String ip, int port) {
		super();
		
		this.ip = ip;
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "Shard [name=" + name + ", password=" + password + ", ip=" + ip
				+ ", port=" + port + "]";
	}
}
