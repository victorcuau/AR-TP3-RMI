package ChatServer1;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ChatRoom implements IChatRoom {
	
	ArrayList<IParticipant> ClientList = new ArrayList<IParticipant>();

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connect(IParticipant p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leave(IParticipant p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] who() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void send(IParticipant p, String msg) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
