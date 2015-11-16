package AuctionCentral;

/**
 * 
 */
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

	private static User myCurrentUser;
	private static Map<String, String> myUserList;
	private static PrintStream myOut;
	private static Scanner myIn;
	private static boolean myChecked; 
	
	public static void main(String[] args) {
		myOut = new PrintStream(System.out, true);
		myUserList = new TreeMap<String, String>();
		myChecked = false;
		
		LoadCurrentUsers();
		myIn = new Scanner(System.in);
		PrintWelcome();
		while (!myChecked) {
			CheckInput(myIn.next());
		}
	}

	private static void CheckInput(String theInput) {
		if ("Q".equals(theInput)) {
			System.exit(0);
		} else {
			if (myUserList.containsKey(theInput)) {
				myChecked = true;
			} else {
				myOut.println("I am sorry, but " + theInput + " is not an approved user.");
				myOut.print("Please enter your username or Q to quit: ");
			}
		}
		
	}

	private static void PrintWelcome() {
		myOut.println("Welcome to _______");
		myOut.print("Please enter your username or Q to quit: ");
	}

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
