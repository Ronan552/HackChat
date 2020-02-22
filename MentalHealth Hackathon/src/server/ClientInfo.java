package server;

import java.net.InetAddress;

//store information for client array
public class ClientInfo {

	private InetAddress address;
	
	private int port;
	
	private int id;
	
	private String name;
	
	
	public ClientInfo(String name, int id, InetAddress address, int port) {
		 this.name=name;
		 this.id=id;
		 this.address=address;
		 this.port=port;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
	
	
}
