package BabyStep;

import java.rmi.Remote;

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
	public void printLine(String s);
	
}