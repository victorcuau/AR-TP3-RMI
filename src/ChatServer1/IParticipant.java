package ChatServer1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IParticipant extends Remote {
	
	String name();
	void receive(String name, String msg) throws RemoteException ;

}