package BabyStep;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author BADAT Leya & CUAU Victor
 *
 */
public interface IPrinter extends Remote {

	/**
	 * Print the string s
	 * @param s
	 * @return nothing
	 */
	public void printLine(String s) throws RemoteException;
	
}