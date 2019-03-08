package BabyStep;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	
	static String host = "localhost";
	static int port = 1234;

	Client(){

	}
	
	public static void main(String[] args) {

    try {
        Registry registry = LocateRegistry.getRegistry(host, port);
        Printer stub = (Printer) registry.lookup("Printer");
        stub.printLine("Coucou toi");
        
    } catch (Exception e) {
        System.err.println("Client exception: " + e.toString());
        e.printStackTrace();
    }
	}
	
}