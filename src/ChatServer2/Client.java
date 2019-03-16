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
		System.out.print("At the moment, you are chatting with ");
		if (list.length < 2) {
			System.out.println("nobody... \n I'm sorry, you're all alone there.");
		} else {
			System.out.println(" :");
			for (int i = 0 ; i < list.length ; i++) {
				if (!list[i].equals(participant.name()))
				System.out.println("\t" + list[i]);
			}
		}
		System.out.println();
	}
	
	public static void displayRoom() throws RemoteException {
		System.out.println();
		int numChatRoom = registry.list().length;
		if(numChatRoom < 1) {
			System.out.println("There is currently no chatroom available on " + host);
			System.out.println("Please, come back again.");
			System.out.println("Exiting application. See you later.");
			System.exit(-1);
		} else if (numChatRoom == 1) {
			System.out.println("At the moment, the only chatroom available is " + registry.list()[0]);
		} else {
			System.out.println("At the moment, the chatrooms available are :");
			for (int i = 0 ; i < registry.list().length ; i++) {
				System.out.println("\t" + registry.list()[i]);
			}
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
	
	public static void chooseRoom() throws IOException, NotBoundException {
		boolean isRoom = false;
		String nameRoom;
		do {
			displayRoom();
			System.out.print("On which chatroom do you want to connect to? ");
			nameRoom = buffRead.readLine();
			System.out.println();
			for (int i = 0 ; i < registry.list().length ; i++) {
				if (registry.list()[i].compareTo(nameRoom) == 0) {
					chatRoom = (IChatRoom) registry.lookup(nameRoom);
					isRoom = true;
					break;
				}
			}
			if (!isRoom) {
				System.out.println(nameRoom + " does not exist.");
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
	}
	
	public static void main(String args[]) throws IOException, NotBoundException {
		System.out.println("CHAT CLIENT");
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
		chooseRoom();
		
		while (msg.compareTo("/quit") != 0 && msg.compareTo("/q") != 0) {
			msg = buffRead.readLine();
			
			if (msg.compareTo("/who") == 0 || msg.compareTo("/w") == 0) {
				displayWho();
			}
			
			else if (msg.compareTo("/help") == 0 || msg.compareTo("/h") == 0) {
				displayHelp();
			}
			
			else if (msg.compareTo("/leave") == 0 || msg.compareTo("/l") == 0) {
				chatRoom.leave(participant);
				System.out.println(participant.name() + ", you've exited the chat room " + chatRoom.name() + ".");
				
				chooseRoom();
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