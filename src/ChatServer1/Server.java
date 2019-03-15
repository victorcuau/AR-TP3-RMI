package ChatServer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	
	int port ;
	
	ChatRoom chatRoom ;
	Registry register ;
	BufferedReader buffRead ;
	
	Server() {
		
	}
	
	public void run() throws IOException, AlreadyBoundException {
		buffRead = new BufferedReader(new InputStreamReader(System.in));
		
		// Choose the port
		System.out.print("On which port do you want to connect? ");
		port = Integer.parseInt(buffRead.readLine());
		
		// Choose the chat room name
		System.out.print("Please choose a name for this new chat room : ");
		chatRoom = new ChatRoom(buffRead.readLine());
		
		register = LocateRegistry.createRegistry(port);
		register.bind("ChatRoom1", chatRoom);
	}
	
	public static void main(String args[]) throws IOException, AlreadyBoundException {
		Server server = new Server();
		server.run();
	}

}