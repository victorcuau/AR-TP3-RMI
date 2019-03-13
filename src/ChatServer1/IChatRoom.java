package ChatServer1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatRoom extends Remote {
	
	String name();
	void connect(IParticipant p) throws RemoteException;
	void leave(IParticipant p) throws RemoteException;
	String[] who();
	void send(IParticipant p, String msg) throws RemoteException;
	
}