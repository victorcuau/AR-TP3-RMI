package ChatServer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	
	static int port = 1234 ;
	
	static IChatRoom chatRoom ;
	static IParticipant participant ;
	static Registry registry ;
	static BufferedReader buffRead ;
	static String msg ;
	
	Client() {
		
	}
	
	public static void displayWho() {
		String[] list = chatRoom.who();
		System.out.println("At the moment, you are chatting with ");
		for (int i = 0 ; i < list.length ; i++) {
			System.out.println(list[i]);
		}
	}
	
	public static void main(String args[]) throws IOException, NotBoundException {
		// Connection to the server
		registry = LocateRegistry.getRegistry("localhost", port);
		chatRoom = (IChatRoom) registry.lookup("ChatRoom1");
		
		// Creation of a new participant
		buffRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("What is your name? ");
		participant = new Participant(buffRead.readLine());
		
		// Welcome message
		System.out.println(participant.name() + ", you've entered the ChatRoom " + chatRoom.name() + ".");
		System.out.println("Use /quit to leave the ChatRoom.");
		System.out.println("Use /who to see who's in the ChatRoom with you.");
		displayWho();
		
		chatRoom.connect(participant);
		
		while (msg.compareTo("/quit") != 0) {
			msg = buffRead.readLine();
			
			if (msg.compareTo("/who") == 0) {
				displayWho();
			}
			
			else {
				chatRoom.send(participant, msg);
			}
		} 
		
		chatRoom.leave(participant);
		System.exit(0);
		
	}
	
}
