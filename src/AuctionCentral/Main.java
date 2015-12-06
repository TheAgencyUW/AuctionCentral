/**
 * 
 */
package AuctionCentral;

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

	/** Used to store the current user.*/
	private static String myCurrentUser;
	private static String myCurrentUserRole;
	/** A map to store the username as a key and their role as a value.*/
	private static Map<String, String> myUserList;
	/** A calendar to hold all the different auction and used to display them.*/
	private static MyCalendar myCalendar;
	/** A print stream for a shortcut to print out to the console.*/
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
		myCalendar = new MyCalendar("calendar.txt");
		myIn = new Scanner(System.in);
		while(true){	
			PrintWelcome();

			while (!myChecked) {
				String input = myIn.next();
				CheckInput(input);
			}

			createUser();

		}
	}

	/**
	 * This method checks what the user inputed as there username and checks
	 * to make sure that it is a valid username.
	 * @param theInput
	 */
	private static void CheckInput(String theInput) {
		if ("Q".equals(theInput)) {
			myCalendar.logout();
			System.exit(0);
		} else {
			if (myUserList.containsKey(theInput)) {
				myCurrentUser = theInput;
				myCurrentUserRole = myUserList.get(myCurrentUser);
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
		myOut.print("Please enter your username or Q to quit: \n");
	}


	public static void createUser(){
		myChecked = false;
		switch(myCurrentUserRole){
		case "bidder" : BidderInterface bidder = new BidderInterface(myCalendar, myCurrentUser);
						bidder.userInterface();
						break;
		case "npo" 	  : NonProfitOrganizationStaff npo = new NonProfitOrganizationStaff(myCalendar, myCurrentUser);
						npo.staffInterface();
						break;
		case "ace"    : AuctionCentralEmployee ace = new AuctionCentralEmployee(myCalendar, myCurrentUser);
						ace.employeeInterface();
			break;
		}
	}

	/**
	 * Used to load all of the current usernames and the rolls into the map 
	 * myCurrentUsers.
	 */
	private static void LoadCurrentUsers() {
		String user, type;
		try {
			myIn = new Scanner(new File("Users.txt"));
			while (myIn.hasNext()) {
				user = myIn.next();
				type = myIn.next();
				user = user.substring(0, user.lastIndexOf(";"));
				myUserList.put(user, type);
			}
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("No user found");
		}

	}

}