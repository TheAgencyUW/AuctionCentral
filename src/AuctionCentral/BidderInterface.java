package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * BidderInterface.java
 * 
 * This bidder interface class takes the I/O out of the Bidder class.
 * 
 * @author Phil
 *
 */

public class BidderInterface 
{
	/** A printstream for a shortcut to print out to the console.*/
	private static PrintStream myOut;

	/** 
	 * A scanner that is used to scan in different files and read from 
	 * the console.
	 * */
	private static Scanner myIn;

	//To store the calendar which contents all the auction passing from main.
	private MyCalendar myCalendar;

	//the current available auction from the current calendar.
	private Auction myCurrentAuction;

	//the current bidder.
	private Bidder bidder;// = new Bidder(myCalendar, null);



	/**
	 * Constructor.
	 * @param myCalendar
	 * @param bidderName
	 */
	public BidderInterface(MyCalendar myCalendar, String bidderName)
	{
		this.myCalendar = myCalendar;
		myCurrentAuction = null;
		bidder = new Bidder(bidderName);		
		loadBidder();
		
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
		
	}

	/**
	 * load the bidder information from the previous activities.
	 */
	private void loadBidder(){
		int itemID;
		String auction;
		double minBid;
		double bidPrice;
		try {
			myIn = new Scanner(new File(bidder.getBidderName() + ".txt"));
			while (myIn.hasNext()) {
				itemID = myIn.nextInt();
				auction = myIn.next();
				minBid = myIn.nextDouble();
				bidPrice = myIn.nextDouble();
				bidder.enterBid(itemID, auction, minBid, bidPrice);
			}
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("bidder not found, login as new bidder");
		}
	}

	/**
	 * the bidder interface.
	 */
	public void userInterface(){
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Welcome " + bidder.getBidderName());
			myOut.println("Please enter a command:\n0:log out\n1: view bided items and make change\n2: view current auctions.\n");
			input = myIn.next();
			if(input.equals("0"))
			{	//break the while loop, end bidderInterface(), return control back to main class.
				logout(bidder.getBidderName());
				back = true;
			}
			else if(input.equals("1"))
			{
				viewCurrentBids();
			}
			else if(input.equals("2"))
			{
				//print the current auction.
				auctionList();
			}
			else
			{
				myOut.println("Invalid input");
			}
		}
	}


	/**
	 *
	 * save the bidder info to a file for next time use
	 * logout and return control the main class.
	 */
	private void logout(String bidderName)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(bidderName + ".txt");
			for(int i = 0; i < bidder.getBidList().size(); i ++){
				writer.print(bidder.getBidList().get(i).itemID + " ");
				writer.print(bidder.getBidList().get(i).auctionName + " ");
				writer.println(bidder.getBidList().get(i).bidPrice);
			}		
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 *  List all the items that the bidder has bided on.
	 * 
	 */
	private void viewCurrentBids(){	
		int choice = 0;
		boolean back = false;
		while(!back){
			for(int i = 0; i < bidder.getBidList().size(); i++){
				BidedItem temp = bidder.getBidList().get(i);
				myOut.println((i+1) + " -> " + temp.toString());
			}

			myOut.print("Please enter your selection to change the bid value of the item.\n Enter 0 to go back to the main menu.\n");
			choice = getInt();

			if(choice == 0 ){
				back = true;
			}else if(choice <=  bidder.getBidList().size()){
				String auction =  bidder.getBidList().get(choice-1).auctionName;
				int itemID =  bidder.getBidList().get(choice-1).itemID;

				myOut.println("Please enter new bid value for item ID: " + itemID + " from auction " + auction);
				myOut.println("Enter 0 to cancel bid on the item");
				double newBid = getDouble();

				if(newBid == 0.0){
					bidder.cancelBid(itemID, auction);
				}else{
					bidder.changeBid(itemID, auction, newBid);
				}
				myOut.println("Your item has been changed successful as below.\n");
			}else{
				myOut.println("Invalid input.\n");
			}
		}
	}


	/**
	 * Print to the console the list of the current available auction
	 * Ask user to pick an auction to find its selling items.
	 */
	private void auctionList(){
		boolean back = false;
		int choice = 0;
		while(!back){	//Auction list
			myOut.println("Current auctions list.\n");
			myOut.println(myCalendar.getListOfCurrentAuctions());

			myOut.println("Please enter your number of selection or 0 to go back to main menu.\n");
			choice = getInt();

			if(choice == 0){	
				back = true;
			}else if(choice <= myCalendar.getSizeOfCurrentAuction()){
				myCurrentAuction = myCalendar.getAuctionFromCurrentAuctions(choice);
				viewItemList();
			}else{
				myOut.print("Cannot find your selection, please try again.\n");
			}

		}
	}

	/**
	 * print to console the list of the items of the auction.
	 * ask the bidder to select an item by item ID to view the item's detail and place bid,
	 */
	private void viewItemList(){
		boolean back = false;
		int choice = 0;
		
		while(!back){	//items list of the selected auction.
			myOut.println("List of items for auction " + myCurrentAuction.getName() + "\n");
			myOut.println(myCurrentAuction.retrieveItemList());

			myOut.println("\nPlease enter an item ID number or 0 to go back to the auctions list.\n");
			choice = getInt();

			if(choice == 0){	//break while loop go back to auction list.
				back = true;
			}else {
				Items selectedItem = myCurrentAuction.getItem(choice);
				if(selectedItem == null){
					myOut.print("Cannot find your selection, please try again.\n");
				}else{
					//sub menu, view item details and place bid.
					viewItem(selectedItem);						
				}
			}
		}
	}

	/**
	 * Print to console the details of the item of the auction aucID
	 * Ask the bidder to place a bid on this item, or enter 0 to go back to items list.
	 * If the bidder has already placed bid on this item and try to bid again, print out an error message,
	 * then automatic return to the items list.
	 * @param item
	 */
	private void viewItem(Items item){
		boolean back = false;
		int choice = 0;
		
		while(!back){
			myOut.println("Item's details: \n");
			myOut.println(item.toString() + "\n");

			myOut.println("Please enter 1 to place bid on this item or 0 to go back to item list. \n");
			choice = getInt();

			if(choice == 0){
				back = true;
			}else if(choice == 1){
				myOut.println("Please enter your bid value.\n");
				double bid = getDouble();

				//if place bid successful (bidder has never placed bid on this item before)
				//add this item to the bidder's bided list.
				try {
					myCurrentAuction.getItem(item.getId()).addBid(bidder.getBidderName(), bid);
					bidder.enterBid(item.getId(), myCurrentAuction.getName(), item.getMinBid(), bid);
					break;
				} catch (PlaceBidException e) {
					myOut.println(e.getMessage());
					myOut.println("Please select a different item.\n");
					break;
				}
			}else{
				myOut.println("Invalid input.");
			}
		}
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
}
