package AuctionCentral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * @author Indiana, Tan Pham
 * Last edit by Tan Pham
 * Sets up the Non-Profit view of the Auction Central Project.
 */
public class NonProfitOrganizationStaff {
	/**
	 * Gets input from the user.
	 */
	private static Scanner myIn;


	/**
	 * a buffer to read in a complete line of input.
	 */
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	/** A print stream for a shortcut to print out to the console.*/
	private static PrintStream myOut ;

	/**
	 * Holds the calendar for use by ACE.
	 */
	private MyCalendar myCalendar;

	/**
	 * Holds the name for the employee.
	 */
	private String staffName;

	/**
	 * the name of the organization which the staff is representing for.
	 */
	private String myOrganizationName;

	/**
	 * Holds the current auction for this non-profit.
	 */
	private Auction currentAuction;

	/**
	 * Constructor.
	 */
	public NonProfitOrganizationStaff(MyCalendar calendar, String name) {
		myCalendar = calendar;
		staffName = name;
		loadStaff();
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
		staffInterface();
	}

	private void loadStaff(){
		String date = "";
		try {
			myIn = new Scanner(new File(staffName + ".txt"));			
			myOrganizationName = myIn.next();
			if(myIn.hasNext()) date = myIn.next();
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("staff not found, login as new staff");
		}
		for(int i = 0; i < myCalendar.getCurrentAuctions().size(); i++){
			if(myCalendar.getCurrentAuctions().get(i).getName().equals(myOrganizationName + " - " + date)){
				currentAuction = myCalendar.getCurrentAuctions().get(i);
			}
		}

	}

	public void staffInterface(){
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Welcome " + staffName);
			myOut.println("Please enter a command:\n0:log out\n1: schedule new auction.\n"
					+ "2: view the current auction.\n");
			input = myIn.next();
			if(input.equals("0")){	//break the while loop, end NPO_Interface(), return control back to main class.
				back = true;
			}else if(input.equals("1")){
				addNewAuction();
			}else if(input.equals("2")){
				viewCurrentAuction();
			}else{
				myOut.println("Invalid input");
			}
		}

	}


	/**
	 * Adds a new auction to the calendar for this non profit organization.
	 */
	private void addNewAuction() {
		Date date;
		int startTime = 0;
		int endTime = 0;

		myOut.println("Please enter the date for the new auction.");
		date = getDate();

		myOut.println("Please enter the start time (must be a number between 0 and 23, inclusive)");
		startTime = getTime();

		myOut.println("Please enter the end time (must be a number between 0 and 23, inclusive)");
		endTime = getTime();

		Auction auc = new Auction(myOrganizationName, date, startTime, endTime);

		try {
			myCalendar.addAuction(auc);
			currentAuction = auc;
			myOut.println("You have successfully added a new acution.\n");
			viewCurrentAuction();
		} catch (HasMaxAuctionsExceptions | MinDaysNotPassedException
				| MaxPer7DaysException | MaxPerDayException
				| MoreTimeBetweenAuctionsException | MaxDaysPassedException e) {
			// TODO Auto-generated catch block
			myOut.print(e.getMessage());
		}

	}

	/**
	 * create a Date object by asking user to enter the year, month and day.
	 * @return
	 */
	private Date getDate(){		
		boolean validInput = false;
		int year = 0, month = 0, day = 0;
		Date date;

		myOut.println("Enter year: ");
		year = getInt();

		myOut.println("Enter month as a number from 1 to 12: ");			
		while(!validInput){
			month = getInt() - 1 ;
			validInput = true;
			if(month < 0 || month > 11){
				myOut.println("Invalid month.");
				validInput = false;
			}
		}

		validInput = false;
		myOut.println("Enter day as an integer from 1 to 31: ");
		while(!validInput){
			day = getInt();
			validInput = true;
			if(day < 1 || day > 31){
				myOut.println("Invalid day.");
				validInput = false;
			}
		}

		date = new GregorianCalendar(year, month, day).getTime();
		return date;
	}

	/**
	 * 
	 * @return an integer between 0 and 23.
	 */
	private int getTime(){
		int time = 0;
		boolean validTime = false;
		while(!validTime){
			time = getInt();
			validTime = true;
			if(time < 0 || time > 23){
				myOut.println("Invalid time.");
				validTime = false;
			}
		}
		return time;
	}

	/**
	 * list the current auction details.
	 */
	private void viewCurrentAuction(){
		if(currentAuction == null){
			myOut.println("You currently have no scheduled auction.\n");
		}else{
			myOut.println("Current auction details:");
			myOut.println("Auction name: " + currentAuction.getName());
			myOut.println("Auction date: " + currentAuction.getMyDate());
			myOut.println("Auction start time: " + currentAuction.getMyStartTime());
			myOut.println("Auction end time: " + currentAuction.getMyEndTime());

			String input;
			boolean back = false;
			while(!back){
				myOut.println("Please enter a command:\n0: go back.\n1: edit auction infomation.\n"
						+ "2: add new item.\n3: view current items");
				input = myIn.next();
				if(input.equals("0")){	
					back = true;
				}else if(input.equals("1")){
					editAuction();
				}else if(input.equals("2")){
					addNewItem();
				}else if(input.equals("3")){
					viewItems();
				}else{
					myOut.println("Invalid input");
				}
			}
		}
	}

	/**
	 * Edits a currently existing auction.
	 */
	private void editAuction() {
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Please enter a command:\n0: go back.\n1: edit auction date.\n"
					+ "2: edit auction start time.\n3: edit auction endtime");
			input = myIn.next();
			if(input.equals("0")){	
				back = true;
			}else if(input.equals("1")){
				myOut.println("Please enter new date.");			
				Date newDate = getDate();
				currentAuction.setMyDate(newDate);
				myOut.println("edit date successful.");
				break;
			}else if(input.equals("2")){
				myOut.println("Please enter the start time (must be a number between 0 and 23, inclusive");
				int newTime = getTime();
				currentAuction.setMyStartTime(newTime);
				myOut.println("edit start time successful.");
				break;
			}else if(input.equals("3")){
				myOut.println("Please enter the start time (must be a number between 0 and 23, inclusive");
				int newTime = getTime();
				currentAuction.setMyEndTime(newTime);
				myOut.println("edit end time successful.");
				break;
			}else{
				myOut.println("Invalid input");
			}
		}
	}

	/**
	 * Adds an item to the current available auction for this non profit organization.
	 */
	private void addNewItem() {
		String itemName = null;
		String description = "";
		double min = 0;

		myOut.println("Please enter item name");
		itemName = getLine();


		myOut.println("Please enter item description.");		
		description = getLine();

		myOut.println("Please enter item min bid");
		min = getDouble();

		currentAuction.addItem(itemName, description, min);
		myOut.println("add item successful");
	}

	/**
	 * get a complete line of input from console
	 * @return a string
	 */
	private String getLine(){
		String toReturn = "";
		try {
			toReturn = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	/**
	 * get a double number from console input.
	 * @return a double
	 */
	private double getDouble(){
		double toreturn = 0;
		String input;
		boolean validInput = false;
		while(!validInput){
			input = myIn.next();
			try{
				toreturn = Double.parseDouble(input);
				validInput = true;
			}catch(Exception e){
				myOut.print("Invalid input. Please try again");
			}		
		}
		return toreturn;
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


	/**
	 * List the items of the current auction.
	 */
	private void viewItems(){
		boolean back = false;
		int choice = 0;
		while(!back){	//items list of the selected auction.
			myOut.println("List of items:\n");
			myOut.println(currentAuction.retrieveItemList());

			myOut.println("\nPlease enter an item ID number or 0 to go back.\n");
			choice = getInt();

			if(choice == 0){	//break while loop go back.
				back = true;
			}else {
				Items selectedItem = currentAuction.getItem(choice);
				if(selectedItem == null){
					myOut.print("Cannot find your selection, please try again.\n");
				}else{
					//sub menu, view item details and place bid.
					editItem(choice);						
				}
			}
		}
	}

	/**
	 * Edits a currently existing item.
	 */
	private void editItem(int itemID) {
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Please enter a command:\n0: go back.\n1: edit item name.\n"
					+ "2: edit item description.\n3: edit item min bid");
			input = myIn.next();
			if(input.equals("0")){	
				back = true;
			}else if(input.equals("1")){
				myOut.println("Please enter new item name.");
				String newName = getLine();
				currentAuction.getItem(itemID).setMyName(newName);
				myOut.println("edit item name successfull");
				break;
			}else if(input.equals("2")){
				myOut.println("Please enter new item description.");
				String newdes = getLine();
				currentAuction.getItem(itemID).setMyDescription(newdes);
				myOut.println("edit description successful");
				break;
			}else if(input.equals("3")){
				myOut.println("Please enter new min bid value.");
				double newbid = getDouble();
				currentAuction.getItem(itemID).setMinBid(newbid);
				myOut.println("edit min bid success");
				break;
			}else{
				myOut.println("Invalid input");
			}
		}
	}
}