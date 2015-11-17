/**
 * 
 */
package users;

import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Indiana
 * Sets up the Auction Central Employee view for the Auction Central Project.
 */
public class AuctionCentralEmployee {
	
	/**
	 * Holds the calendar for use by ACE.
	 */
	private users.Calendar myCalendar;
	
	/**
	 * Holds the name for the employee.
	 */
	private String employeeName;

	/**
	 * Gets input from the user.
	 */
	private static Scanner myIn;
	
	/**
	 * 
	 */
	public AuctionCentralEmployee(users.Calendar calendar, String name) {
		myCalendar = calendar;
		employeeName = name;
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
		int input;
		while(true){
			System.out.println("Welcome " + employeeName);
			System.out.println("Please enter a command:\n0:log out\n1: view current calendar date\n2: view selected calendar date.\n3: view selected auction\n");
			input = myIn.nextInt();
			if(input == 0){	//break the while loop, end bidderInterface(), return control back to main class.
				break;
			}else if(input == 1){
				viewCurrentCalendar();
			}else if(input == 2){
				System.out.println("Please enter the date you wish to look at in the form monthdayyear: ");
				int date = myIn.nextInt();
				viewCalendar(date);
			}else{
				System.out.println("Invalid input");
			}
		}

	}
	
	/**
	 * Views the calendar of upcoming auctions.
	 */
	void viewCurrentCalendar() {
		myCalendar.toString();
	}
	
	/**
	 * Views auction details.
	 */
	void viewCalendar(int date) {
		myCalendar.toString(date);
	}
}