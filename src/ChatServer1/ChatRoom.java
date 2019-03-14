package ChatServer1;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatRoom implements IChatRoom {
	
	String name;
	ArrayList<IParticipant> ClientList = new ArrayList<IParticipant>();
	
	ChatRoom(String name){
		this.name = name;
		System.out.println("ChatRoom " + this.name() + " has been opened.");
		System.out.println("Enemies of the heir, beware.");
	}

	public String name() {
		return name;
	}

	/* Allows a new participant to connect to a chat room */
	public void connect(IParticipant p) throws RemoteException {
		ClientList.add(p);
		String msg = "Participant " + p.name() + " is connected to ChatRoom " + this.name() + ".";
		System.out.println(msg);
		this.send(p, msg); // Ajouter un bot d'acceuil qui s'occupe de ça.
	}

	/* Allows a participant to leave a chat room */
	public void leave(IParticipant p) throws RemoteException {
		ClientList.remove(p);
		String msg = "Participant " + p.name() + " has been petrified and is now dead." + this.name() + ".";
		System.out.println(msg);
		this.send(p, msg);
	}

	/* Allows a participant to get the names of all participants currently connected to a chat room */
	public String[] who() {
		String[] list = new String[ClientList.size()];
		for (int i = 0 ; i < ClientList.size() ; i++) {
			list[i] = ClientList.get(i).name();
		}
		return list;
	}

	/* Allows a participant to send a message to a given chat room */
	public void send(IParticipant p, String msg) throws RemoteException {
		for (int i = 0; i < ClientList.size(); i++) {
			ClientList.get(i).receive(p.name(), msg);
		}
	}

}
