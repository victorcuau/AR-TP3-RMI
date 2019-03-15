package ChatServer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	
	static String host;
	static int port;
	
	static IChatRoom chatRoom ;
	static IParticipant participant ;
	static Registry registry ;
	static BufferedReader buffRead ;
	static String msg = "";
	
	Client() {
		
	}
	
	public static void displayWho() throws RemoteException {
		String[] list = chatRoom.who();
		System.out.println("At the moment, you are chatting with :");
		for (int i = 0 ; i < list.length ; i++) {
			System.out.println("\t" + list[i]);
		}
	}
	
	public static void displayHelp() throws RemoteException {
		System.out.println();
		System.out.println("\t Use /quit or /q to leave the chat room.");
		System.out.println("\t Use /who or /w to see who's in the chat room with you.");
		System.out.println("\t Use /help or /h to display this again.");
		System.out.println();
	}
	
	public static void main(String args[]) throws IOException, NotBoundException {
		buffRead = new BufferedReader(new InputStreamReader(System.in));
		
		// Choose the server
		System.out.print("What is the server you want to connect to? ");
		host = buffRead.readLine();
		
		// Choose the port
		System.out.print("On which port do you want to connect to " + host + " ? ");
		port = Integer.parseInt(buffRead.readLine());
		
		// Connection to the server
		registry = LocateRegistry.getRegistry(host, port);
		chatRoom = (IChatRoom) registry.lookup("ChatRoom1");
		
		// Creation of a new participant
		System.out.print("What is your name? ");
		participant = new Participant(buffRead.readLine());
		
		// Welcome message
		System.out.println();
		System.out.println(participant.name() + ", you've entered the chat room " + chatRoom.name() + ".");
		displayHelp();
		
		chatRoom.connect(participant);
		
		displayWho();
		
		while (msg.compareTo("/quit") != 0 && msg.compareTo("/q") != 0) {
			msg = buffRead.readLine();
			
			if (msg.compareTo("/who") == 0  || msg.compareTo("/w") == 0) {
				displayWho();
			}
			
			else if (msg.compareTo("/help") == 0  || msg.compareTo("/h") == 0) {
				displayHelp();
			}
			
			else {
				chatRoom.send(participant, msg);
			}
		} 
		
		chatRoom.leave(participant);
		System.exit(0);
		
	}
	
}
