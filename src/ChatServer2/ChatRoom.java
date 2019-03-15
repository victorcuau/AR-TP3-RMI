package ChatServer2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ChatRoom extends UnicastRemoteObject implements IChatRoom {
	
	private static final long serialVersionUID = 1L;
	
	String name;
	ArrayList<IParticipant> ClientList ;
	
	ChatRoom(String name) throws RemoteException {
		this.name = name;
		this.ClientList = new ArrayList<IParticipant>();
		System.out.println();
		System.out.println("\t Chat room " + this.name() + " has been opened.");
		System.out.println("\t Enemies of the heir, beware.");
		System.out.println();
	}

	public String name() throws RemoteException {
		return name;
	}

	/* Allows a new participant to connect to a chat room */
	public void connect(IParticipant p) throws RemoteException {
		ClientList.add(p);
		String msg = "Participant " + p.name() + " is connected to chat room " + this.name() + ".";
		System.out.println(msg);
		this.send(p, msg); // Ajouter un bot d'acceuil qui s'occupe de ça.
	}

	/* Allows a participant to leave a chat room */
	public void leave(IParticipant p) throws RemoteException {
		ClientList.remove(p);
		String msg = "Participant " + p.name() + " has been petrified in " + this.name + " and is now dead.";
		System.out.println(msg);
		this.send(p, msg);
	}

	/* Allows a participant to get the names of all participants currently connected to a chat room */
	public String[] who() throws RemoteException {
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
