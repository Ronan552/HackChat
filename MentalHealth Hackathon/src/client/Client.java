package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.ClientInfo;

public class Client {

	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private boolean running;
	private String name;

	public Client(String name, String address, int port) {

		try {
			this.name=name;
			this.address = InetAddress.getByName(address);
			this.port = port;

			socket = new DatagramSocket();
			running = true;
			listen();
			send("\\con:" + name);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void send(String message) {
		try {
			
			if(!message.startsWith("\\")) {
				message=name+":"+message;
			}
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

	private void listen() {

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
							// Print message
							ClientWindow.printToConsole(message);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		listenThread.start();

	}

	private static boolean isCommand(String message, DatagramPacket packet) {

		if (message.startsWith("\\con:")) {
			// Run Connection Code

			return true;
		}

		return false;
	}
}
