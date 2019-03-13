package BabyStep;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	
	static String host = "localhost";
	static int port = 1234;
	IPrinter printer;
	
	Server() {
		printer = new Printer();
	}
	
	public void run() {
    
    try {
        IPrinter stub = (IPrinter) UnicastRemoteObject.exportObject(printer, 0);

        // Bind the remote object's stub in the registry
        Registry registry = LocateRegistry.createRegistry(port);
        registry.bind("Printer", stub);
        //Naming.rebind("//" + host + ":" + port + "/" + "LinePrinter", printer);

        System.err.println("Server ready");
        
    } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
    }
	}
	
	public static void main(String args[]) {
		Server server = new Server();
		server.run();
	}

}
