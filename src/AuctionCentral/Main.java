/**
 * 
 */
package users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
/**
 * @author Jason Hall
 */
public class Main {

	// Class variables
	/** Used to store a calendar that holds the different auction.*/
	// private static Calendar myCalendar
	/** Used to store the current user.*/
	// private static Users myCurrentUser;
	/** A map to store the username as a key and their role as a value.*/
	private static Map<String, String> myUserList;
	/** A calendar to hold all the different auctions and used to display them.*/
	private static Calendar myCalendar;
	/** A printstream for a shortcut to print out to the console.*/
	private static PrintStream myOut;
	/** 
	 * A scanner that is used to scan in different files and read from 
	 * the console.
	 * */
	private static Scanner myIn;
	/** Used to tell if the user has entered a correct user name.*/
	private static boolean myChecked;
	
	public static void main(String[] args) {
		myOut = new PrintStream(System.out, true);
		myUserList = new TreeMap<String, String>();
		myChecked = false;
		
		LoadCurrentUsers();
		RunProgram();
	}

	/**
	 * A method to run the program AuctionCentral.
	 */
	private static void RunProgram() {
		myIn = new Scanner(System.in);
		PrintWelcome();
		String input = "";
		while (!myChecked) {
			input = myIn.next();
			CheckInput(input);
		}
		myCalendar = new Calendar();
		myUserList.
	}

	/**
	 * This method checks what the user inputed as there username and checks
	 * to make sure that it is a valid username.
	 * @param theInput
	 */
	private static void CheckInput(String theInput) {
		if ("Q".equals(theInput)) {
			System.exit(0);
		} else {
			if (myUserList.containsKey(theInput)) {
				myChecked = true;
			} else {
				myOut.println("I am sorry, but " + theInput + " is not an "
						+ "approved user.");
				myOut.print("Please enter your username or Q to quit: ");
			}
		}
		
	}

	/**
	 * This method is used to print out the welcome method when the program 
	 * starts or the current user just log-out
	 */
	private static void PrintWelcome() {
		myOut.println("Welcome to AuctionCentral");
		myOut.print("Please enter your username or Q to quit: ");
	}

	/**
	 * Used to load all of the current usernames and the rolls into the map 
	 * myCurrentUsers.
	 */
	private static void LoadCurrentUsers() {
		String user, type;
		try {
			myIn = new Scanner(new File("Users"));
			while (myIn.hasNextLine()) {
				user = myIn.next();
				type = myIn.next();
				user = user.substring(0, user.lastIndexOf(';'));
				myUserList.put(user, type);
			}
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("No months found");
		}
		
	}

}