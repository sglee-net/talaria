package net.sglee.websocket;

import net.sglee.websocket.client.StartClient;
import net.sglee.websocket.server.StartServer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(args[0]);
		
		if(args[0].equals("server")) {
			StartServer.start(args);
		} else if (args[0].equals("client")) {
			StartClient.start(args);
		} else {
			System.out.println("undefined type, select server or client");
		}
	}

}
