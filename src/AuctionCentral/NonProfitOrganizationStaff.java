/**
 * 
 */
package users;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Indiana
 * Sets up the Non-Profit view of the Auction Central Project.
 */
public class NonProfitOrganizationStaff {

	
	/**
	 * Holds the calendar.
	 */
	private users.Calendar myCalendar;
	
	/**
	 * Holds the NPO name.
	 */
	private String myOrganizationName;

	/**
	 * Gets input from the user.
	 */
	private Scanner myIn;

	/**
	 * Holds the representative name.
	 */
	private String myRepresentativeName;
		
	/**
	 * Holds the auction date.
	 */
	String myDate;
	
	/**
	 * Holds the item.
	 */
	Items myItem;
	
	/**
	 * Holds the month.
	 */
	String myMonth;
	
	/**
	 * Holds the day.
	 */
	int myDay;
	
	/**
	 * Holds the year.
	 */
	int myYear;
	
	/**
	 * Holds the start time.
	 */
	String myStart;
	
	/**
	 * Holds the end time.
	 */
	String myEnd;
	
	/**
	 * Holds the auction title.
	 */
	String title;
	
	/**
	 * Auction editing permission.
	 */
	Boolean myEditAuction;
	
	/**
	 * Auction creation permission.
	 */
	Boolean myAddAuction;
	
	/**
	 * Add item permission.
	 */
	Boolean myAddItem;
	
	/**
	 * Edit item permission.
	 */
	Boolean myEditItem;
	
	/**
	 * A counter for the item id.
	 */
	int myCounter;
	
	/**
	 * Holds the auction.
	 */
	Auction myAuction;
	
	/**
	 * 
	 * @param calendar
	 * @param org
	 * @param rep
	 */
	public NonProfitOrganizationStaff(users.Calendar calendar, String org, String rep) {
		myCalendar = calendar;
		myOrganizationName = org;
		myRepresentativeName = rep;
		myEditAuction = false;
		myAddAuction = true;
		myAddItem = false;
		myEditItem = false;
		myCounter = 0;
	}
	
	/**
	 * The interface for the ACE.
	 */
	public void organizationInterface(){
		int input;
		while(true){
			System.out.println("Welcome " + myRepresentativeName);
			System.out.println("Please enter a command:\n0:log out\n1: add new auction\n2: edit auction.\n3: add new item\n4: edit item\n");
			input = myIn.nextInt();
			if(input == 0){	//break the while loop, end bidderInterface(), return control back to main class.
				break;
			}else if(input == 1 && myAddAuction){
				addNewAuction();
			}else if(input == 2 && myEditAuction){
				editAuction();
			}else if(input == 3 && myAddItem) {
				addNewItem();
			}else if(input == 4 && myEditItem) {
				System.out.println("Please enter the item id number(id number is the items entry number): ");
				int id = myIn.nextInt();
				editItem();
			}else{
				System.out.println("Invalid input");
			}
		}

	}
	
	/**
	 * Adds a new auction to the calendar for this non profit organization.
	 */
	void addNewAuction() {
		System.out.println("Enter the auction month as a number: ");
		int month = myIn.nextInt();
		while(month < 1 || month > 12) {
			System.out.println("Error. Could not set date. Enter the auction month: ");
			month = myIn.nextInt();
		}
		if(month == 1) {
			myMonth = "January";
		} else if(month == 2) {
			myMonth = "February";
		} else if(month == 3) {
			myMonth = "March";
		} else if(month == 4) {
			myMonth = "April";
		} else if(month == 5) {
			myMonth = "May";
		} else if(month == 6) {
			myMonth = "June";
		} else if(month == 7) {
			myMonth = "July";
		} else if(month == 8) {
			myMonth = "August";
		} else if(month == 9) {
			myMonth = "September";
		} else if(month == 10) {
			myMonth = "October";
		} else if(month == 11) {
			myMonth = "November";
		} else {
			myMonth = "December";
		}
		System.out.println("Enter the auction day: ");
		int day = myIn.nextInt();
		while(day < 1 || day > 30) {
			System.out.println("Error. Could not set date. Enter the auction day: ");
			day = myIn.nextInt();
		}
		myDay = day;
		System.out.println("Enter the auction year: ");
		int year = myIn.nextInt();
		while(year < Calendar.YEAR && myYear == year) {
			System.out.println("Error. Could not set date. Enter the auction year: ");
			year = myIn.nextInt();
		}
		myYear = year;
		myDate = (myMonth + myDay + myYear);
		System.out.println("Enter the auction start hour: ");
		int start = myIn.nextInt();
		while(start < 0 || start > 24) {
			System.out.println("Error. Could not set date. Enter the auction hour: ");
			start = myIn.nextInt();
		}
		if(start < 12) {
			myStart = (start + ":00am");
		} else {
			myStart = (start - 12 + ":00pm");
		}
		System.out.println("Enter the auction end hour(0 hour is 12am): ");
		int end = myIn.nextInt();
		while(end < 0 || end > 23) {
			System.out.println("Error. Could not set date. Enter the auction hour: ");
			end = myIn.nextInt();
		}
		if(end < 12) {
			myEnd = (end + ":00am");
		} else {
			myEnd = (end - 12 + ":00pm");
		}
		myAuction = new Auction(myOrganizationName, myDate, myStart, myEnd);
		myEditAuction = true;
		myAddItem = true;
	}
	
	/**
	 * Edits a currently existing auction.
	 */
	void editAuction() {
		System.out.println("Please enter the key of what you want to edit "
				+ "(m for month, d for day, y for year, s for start time, n for end time, and e to exit): ");
		Scanner theScn = null;
		String change = theScn.next();
		while(change != "e") {
			if(change == "m") {
				System.out.println("Enter the auction month: ");
				int month = theScn.nextInt();
				while(month < 1 || month > 12) {
					System.out.println("Error. Could not set date. Enter the auction month: ");
					month = theScn.nextInt();
				}
				if(month == 1) {
					myMonth = "January";
				} else if(month == 2) {
					myMonth = "February";
				} else if(month == 3) {
					myMonth = "March";
				} else if(month == 4) {
					myMonth = "April";
				} else if(month == 5) {
					myMonth = "May";
				} else if(month == 6) {
					myMonth = "June";
				} else if(month == 7) {
					myMonth = "July";
				} else if(month == 8) {
					myMonth = "August";
				} else if(month == 9) {
					myMonth = "September";
				} else if(month == 10) {
					myMonth = "October";
				} else if(month == 11) {
					myMonth = "November";
				} else {
					myMonth = "December";
				}
				myDate = (myMonth + myDay + myYear);
				myAuction.setMyDate(myDate);
			} else if(change == "d") {
				System.out.println("Enter the auction day: ");
				int day = theScn.nextInt();
				while(day < 1 || day > 30) {
					System.out.println("Error. Could not set date. Enter the auction day: ");
					day = theScn.nextInt();
				}
				myDay = day;
				myDate = (myMonth + myDay + myYear);
				myAuction.setMyDate(myDate);
			} else if(change == "y") {
				System.out.println("Enter the auction year: ");
				int year = theScn.nextInt();
				while(year < Calendar.YEAR) {
					System.out.println("Error. Could not set date. Enter the auction year: ");
					year = theScn.nextInt();
				}
				myYear = year;
				myDate = (myMonth + myDay + myYear);
				myAuction.setMyDate(myDate);
			} else if(change == "s") {
				System.out.println("Enter the auction start hour: ");
				int start = myIn.nextInt();
				while(start < 0 || start > 24) {
					System.out.println("Error. Could not set date. Enter the auction hour: ");
					start = myIn.nextInt();
				}
				if(start < 12) {
					myStart = (start + ":00am");
				} else {
					myStart = (start - 12 + ":00pm");
				}
			} else if(change == "n") {
				System.out.println("Enter the auction end hour(0 hour is 12am): ");
				int end = myIn.nextInt();
				while(end < 0 || end > 23) {
					System.out.println("Error. Could not set date. Enter the auction hour: ");
					end = myIn.nextInt();
				}
				if(end < 12) {
					myEnd = (end + ":00am");
				} else {
					myEnd = (end - 12 + ":00pm");
				}
			} else {
				System.out.println("Please enter the key of what you want to edit "
						+ "(m for month, d for day, y for year, s for start time, n for end time, and e to exit): ");
				change = theScn.next();
			}
		}
	}
	
	/**
	 * Adds an item to the current available auction for this non profit organization.
	 */
	void addNewItem() {
		myCounter++;
		System.out.println("Please enter the item name: ");
		String name = myIn.nextLine();
		System.out.println("Please enter the item description: ");
		String description = myIn.nextLine();
		System.out.print("Please enter the minimum bid for this item: ");
		int minimumBid = myIn.nextInt();
		myAuction.addItem(name, description, minimumBid);
		myEditItem = true;
	}
	
	/**
	 * Edits a currently existing item.
	 */
	void editItem() {
		System.out.println("Please enter the id of the item you wish to edit: ");
		int id = myIn.nextInt();
		myItem = myAuction.getItem(id);
		System.out.println("Please enter the key of what you want to edit "
				+ "(n for name, d for description, b for minimum bid, and e to exit): ");
		String change = myIn.next();
		while(change != "e") {
			if(change == "n"){
				System.out.println("Please enter the item name: ");
				myAuction.editItemName(id, myIn.nextLine());
			} else if(change == "d") {
				System.out.println("Please enter the item description: ");
				myAuction.editItemDes(id, myIn.nextLine());
			} else if(change == "b") {
				System.out.print("Please enter the minimum bid for this item: ");
				myAuction.editItemMinBid(id, myIn.nextInt());
			} else {
				System.out.println("Please enter the id of the item you wish to edit: ");
				id = myIn.nextInt();
				myItem = myAuction.getItem(id);
				System.out.println("Please enter the key of what you want to edit "
						+ "(i for id, n for name, d for description, b for minimum bid, and e to exit): ");
				change = myIn.next();
			}
		}
	}
}