

package AuctionCentral;

import java.io.PrintStream;
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
	private Calendar myCalendar;

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

	/**
	 * Constructor.
	 */
	public AuctionCentralEmployee(Calendar calendar, String name) {
		myCalendar = calendar;
		employeeName = name;
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
				myOut.println("Please enter the month you wish to look at.\n ");
				int month = myIn.nextInt();
				myOut.println("Please enter the year.");
				int year = myIn.nextInt();				
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
		String input;
		int choice = 0;
		while(!back){	//Auction list
			myOut.println("Current auctions list\n\n");
			myOut.println(myCalendar.viewCurrentAuctions());

			boolean validInput = false;
			while(!validInput){
				myOut.println("Please enter your number of selection or 0 to go back to main menu.\n");
				input = myIn.next();
				try{
					choice = Integer.parseInt(input);
					validInput = true;
				}catch(Exception e){
					myOut.println("Invalid input");
				}
			}
			if(choice == 0){	
				back = true;
			}else if(choice <= myCalendar.getMyCurrentAuctions().size()){
				int auction = choice -1;	//save selected auction.

				//sub menu, view list of items and ask user to select item to bid.
				viewItemList(auction);
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
	 * @param auction, the auction where the items belong.
	 */
	public void viewItemList(int auction)
	{
		boolean back = false;
		String input;
		int choice = 0;
		while(!back){	//items list of the selected auction.
			myOut.println("List of items for auction " + myCalendar.getMyCurrentAuctions().get(auction).getName() + "\n");
			myOut.println(myCalendar.getMyCurrentAuctions().get(auction).retrieveItemList());

			boolean validInput = false;
			while(!validInput){
				myOut.println("\nPlease enter 0 to go back to the auctions list.\n");
				input = myIn.next();
				try{
					choice = Integer.parseInt(input);
					validInput = true;
				}catch(Exception e){
					myOut.println("Invalid input");
				}
			}
			if(choice == 0){	//break while loop go back to auction list.
				back = true;
			}
		}
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
		myOut.println(myCalendar.viewAuctionByMonth(ym));
	}
}