

package AuctionCentral;

import java.io.PrintStream;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.Scanner;

/**
 * @author Indiana, Tan Pham
 *
 * Sets up the Auction Central Employee view for the Auction Central Project.
 */
public class AuctionCentralEmployee {

	/**
	 * Holds the calendar for use by ACE.
	 */
	private MyCalendar myCalendar;

	/**
	 * Holds the name for the employee.
	 */
	private String employeeName;

	/**
	 * Gets input from the user.
	 */
	private static Scanner myIn;

	/** A printstream for a shortcut to print out to the console.*/
	private static PrintStream myOut ;
	
	/** */
	private Auction myCurrentAuction;
	
	/**
	 * Constructor.
	 */
	public AuctionCentralEmployee(MyCalendar calendar, String name) {
		myCalendar = calendar;
		employeeName = name;
		myCurrentAuction = null;
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
	}

	/**
	 * Returns the employee name.
	 * @return the bidderName
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * The interface for the ACE.
	 */
	public void employeeInterface(){
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Welcome " + employeeName);
			myOut.println("Please enter a command:\n0:log out\n1: view a monthly calendar.\n"
					+ "2: view the list of auctions\n");
			input = myIn.next();
			if(input.equals("0")){	//break the while loop, end ACE_Interface(), return control back to main class.
				back = true;
			}else if(input.equals("1")){
				myOut.println("Please enter the month you wish to look at (number between 1 to 12).\n ");
				int month = getInt();
				myOut.println("Please enter the year.");
				int year = getInt();				
				YearMonth  ym  = YearMonth.of(year, month);			
				viewCalendar(ym);
			}else if(input.equals("2")){
				auctionList();
			}else{
				myOut.println("Invalid input");
			}
		}

	}
	
	
	/**
	 * I/O only
	 * No need Junit test
	 * 
	 * Print to the console the list of the current available auction
	 * Ask user to pick an auction to find its selling items.
	 */
	public void auctionList(){
		boolean back = false;
		int choice = 0;
		while(!back){	//Auction list
			myOut.println("Current auctions list\n\n");
			myOut.println(myCalendar.getListOfCurrentAuctions());

			myOut.println("Please enter your number of selection or 0 to go back to main menu.\n");
			choice = getInt();
			
			if(choice == 0){	
				back = true;
			}else if(choice <= myCalendar.getSizeOfCurrentAuction()){
				myCurrentAuction = myCalendar.getAuctionFromCurrentAuctions(choice);
				//sub menu, view list of items and ask user to select item to bid.
				viewItemList();
			}else{
				myOut.print("Cannot find your selection, please try again.\n");
			}

		}
	}


	/**
	 *  I/O only
	 *  NO need Junit test for this method.
	 * 
	 * 
	 * print to console the list of the items of the auction.
	 * ask the bidder to select an item by item ID to view the item's detail and place bid,
	 */
	public void viewItemList(){
		//Auction currentAuction = myCalendar.getAuctionFromCurrentAuctions(auction);
		
		myOut.println("List of items for auction " + myCurrentAuction.getName() + "\n");
		myOut.println(myCurrentAuction.retrieveItemList());
		
		myOut.println("\nPlease enter any key to go back to the auctions list.\n");
		myIn.next();			
	}

	/**
	 * Views the calendar of upcoming auctions.
	 *
	void viewCurrentCalendar() {
		myCalendar.viewAuctionByMonth(month)
	}
	*/

	/**
	 * Views auction details.
	 */
	void viewCalendar(YearMonth ym) {
		try {
			myOut.println(myCalendar.getAnyMonthCalendarView(ym));
		} catch (ParseException e) {
			myOut.print(e.getMessage());
		}
	}
	
	/**
	 * get an integer from console input
	 * @return an integer.
	 */
	private int getInt(){
		int toreturn = 0;
		String input;
		boolean validInput = false;
		while(!validInput){
			input = myIn.next();
			try{
				toreturn = Integer.parseInt(input);
				validInput = true;
			}catch(Exception e){
				myOut.print("Invalid input. Please try again");
			}		
		}
		return toreturn;
	}
}