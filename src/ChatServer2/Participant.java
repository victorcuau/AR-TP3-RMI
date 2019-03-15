package ChatServer2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Participant extends UnicastRemoteObject implements IParticipant {

	private static final long serialVersionUID = 1L;
	
	String name;
	
	Participant(String name) throws RemoteException {
		this.name = name;
	}

	public String name() throws RemoteException {
		return name;
	}

	@Override
	public void receive(String name, String msg) throws RemoteException {
		if (name.compareTo(this.name) != 0) {
			System.out.println(name + " : " + msg);
		}
	}

}