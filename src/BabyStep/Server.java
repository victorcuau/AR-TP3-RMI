package BabyStep;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
	
	static String host = "localhost";
	static int port = 1234;
	
	Server() {

	}
	
	public static void main(String args[]) throws RemoteException {
    
    try {
        Printer printer = new Printer();
        Printer stub = (Printer) UnicastRemoteObject.exportObject(printer, port);

        // Bind the remote object's stub in the registry
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("Printer", stub);
        //Naming.rebind("//" + host + ":" + port + "/" + "LinePrinter", printer);

        System.err.println("Server ready");
        
    } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
    }
	}

}
