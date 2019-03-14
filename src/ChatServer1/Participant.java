package ChatServer1;

import java.rmi.RemoteException;

public class Participant implements IParticipant {

	String name;
	
	Participant(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	@Override
	public void receive(String name, String msg) throws RemoteException {
		System.out.println(name + " : " + msg);
	}

}