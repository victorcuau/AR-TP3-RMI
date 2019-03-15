package ChatServer2;

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
		System.out.println();
		System.out.println("At the moment, you are chatting with :");
		for (int i = 0 ; i < list.length ; i++) {
			System.out.println("\t" + list[i]);
		}
		System.out.println();
	}
	
	public static void displayRoom() throws RemoteException {
		System.out.println();
		System.out.println("At the moment, the chat rooms available are :");
		for (int i = 0 ; i < registry.list().length ; i++) {
			System.out.println("\t" + registry.list()[i]);
		}
		System.out.println();
	}
	
	public static void displayHelp() throws RemoteException {
		System.out.println();
		System.out.println("\t Use /quit or /q to exit the application.");
		System.out.println("\t Use /leave or /l to leave the chat room.");
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
		
		// Connection to a chat room
		displayRoom();
		System.out.print("On which chat room do you want to connect to? ");
		String nameRoom = buffRead.readLine();
		boolean isRoom = false;
		
		do {
			displayRoom();
			System.out.print("On which CHAT room do you want to connect to? ");
			nameRoom = buffRead.readLine();
			System.out.println();
			
			for (int i = 0 ; i < registry.list().length ; i++) {
				if (registry.list()[i].compareTo(nameRoom) == 0) {
					chatRoom = (IChatRoom) registry.lookup(buffRead.readLine());
					isRoom = true;
					break;
				}
			}
			
			if (!isRoom) {
				System.out.print(nameRoom + " does not exist.");
			}
			
		} while(!isRoom);
		
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
			
			else if (msg.compareTo("/leave") == 0  || msg.compareTo("/l") == 0) {
				chatRoom.leave(participant);
				System.out.println(participant.name() + ", you've exited the chat room " + chatRoom.name() + ".");
				
				displayRoom();
				System.out.print("On which chat room do you want to connect to? ");
				chatRoom = (IChatRoom) registry.lookup(buffRead.readLine());
			}
			
			else {
				chatRoom.send(participant, msg);
			}
		}
		chatRoom.leave(participant);
		System.out.println(participant.name() + ", you've exited the chat room " + chatRoom.name() + ".");
		System.out.println("Exiting application. See you later.");
		System.exit(0);
		
	}
	
}
