package ChatServer2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Server {
	
	int port ;
	
	ChatRoom chatRoom ;
	ArrayList<IChatRoom> chatRoomList = new ArrayList<IChatRoom>();
	Registry register ;
	BufferedReader buffRead ;
	String cmd = "";
	
	Server() {
		
	}
	
	public void displayHelp() throws RemoteException {
		System.out.println();
		System.out.println("\t Use /shutdown to stop the server.");
		System.out.println("\t Use /list or /l to see the chat rooms.");
		System.out.println("\t Use /new or /n to create a new chat room.");
		System.out.println("\t Use /help or /h to display this again.");
		System.out.println();
	}
	
	public void displayRoom() throws RemoteException {
		System.out.println();
		
		if(chatRoomList.size() == 0) {
			System.out.println("At the moment, there is no chatroom available.");
		}
		else {
			System.out.println("At the moment, the chat rooms available are :");
			for (int i = 0 ; i < chatRoomList.size() ; i++) {
				System.out.println("\t" + chatRoomList.get(i).name());
			}
		}
		
		System.out.println();
	}
	
	public void newRoom() throws IOException, AlreadyBoundException {
		System.out.println();
		System.out.print("Please choose a name for this new chat room : ");
		chatRoom = new ChatRoom(buffRead.readLine());
		
		register.bind(chatRoom.name, chatRoom);
		chatRoomList.add(chatRoom);
		System.out.println();
	}
	
	public void run() throws IOException, AlreadyBoundException {
		buffRead = new BufferedReader(new InputStreamReader(System.in));
		
		// Choose the port
		System.out.print("On which port do you want to connect? ");
		port = Integer.parseInt(buffRead.readLine());
		
		// Creation of the registry
		register = LocateRegistry.createRegistry(port);
		
		System.out.println("SERVER READY");
		displayHelp();
		
		while (true) {
			cmd = buffRead.readLine();
			
			if (cmd.compareTo("/list") == 0  || cmd.compareTo("/l") == 0) {
				displayRoom();
			}
			
			else if (cmd.compareTo("/new") == 0  || cmd.compareTo("/n") == 0) {
				newRoom();
			}
			
			else if (cmd.compareTo("/help") == 0  || cmd.compareTo("/h") == 0) {
				displayHelp();
			}
			
			else if (cmd.compareTo("/shutdown") == 0) {
				break;
			}
			
			else {
				System.out.println("This is not a valid command.");
			}
		} 
		System.out.println("SERVER STOPPED");
		System.exit(0);

	}
	
	public static void main(String args[]) throws IOException, AlreadyBoundException {
		System.out.println("CHAT SERVER");
		Server server = new Server();
		server.run();
	}

}