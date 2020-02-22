package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

	private static DatagramSocket socket;
	// server running?
	private static boolean running;
	private static int clientId;
	private static ArrayList<ClientInfo> clients = new ArrayList<ClientInfo>();

	
	// Start server and initialise resources
	public static void start(int port) {

		try {
			// initialise the socket
			socket = new DatagramSocket(port);

			running = true;
			// call the listen method
			listen();

			System.out.println("Server started on port : " + port);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// send message to every client connected to server
	private static void broadcast(String message) {
		
		for(ClientInfo info : clients) {
			send(message, info.getAddress(),info.getPort());
		}
	}

	// sending messages to individual clients on client
	private static void send(String message, InetAddress address, int port) {

		try {
			// end of message
			message += "\\e";
			// putting message into byte array of size message
			byte[] data = message.getBytes();
			// taking in byte array, byte array length, client address, client port
			DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
			// tell socket to send packet
			socket.send(packet);
			System.out.println("Sent message to " + address.getHostAddress() + " : " + port);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// thread that runs the entire time to wait for messages
	private static void listen() {

		Thread listenThread = new Thread("Chatprogram Listener") {

			public void run() {
				try {
					while (running) {
						// recieving messages
						byte[] data = new byte[1024];
						// any information in the packet is written to the data array
						DatagramPacket packet = new DatagramPacket(data, data.length);
						// Waits for message- into the packet object
						socket.receive(packet);

						String message = new String(data);
						// \e to end message
						message = message.substring(0, message.indexOf("\\e"));

						// manage message
						// Broadcast new client/client disconnected
						if (!isCommand(message, packet)) {
							broadcast(message);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		listenThread.start();

	}

	/*
	 * SERVER COMMAND LIST 
	 * \con[name] --> Connects client to Server 
	 * \dis[id] --> Disconnects
	 * client from Server
	 */
	
	
	private static boolean isCommand(String message, DatagramPacket packet) {
		
		if (message.startsWith("\\con:")) {
			//Run Connection Code
			
			//Take name out of message, specified after colon
			String name = message.substring(message.indexOf(":")+1);
			//Add client into clients arraylist
			
			//by adding 1 to clientId we avoid duplicates in the array
			clients.add(new ClientInfo(name, clientId++, packet.getAddress(),packet.getPort()));
			
			broadcast("User: "+name+" Connected!");
			return true;
		}

		return false;
	}

	// stop server without closing the pgroam
	public static void stop() {

		running =false;
	}
}
