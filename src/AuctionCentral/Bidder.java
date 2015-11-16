package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * bidder.java
 * 
 * @author Tan Pham, Phillip Mishchuk
 * 
 */

public class Bidder
{

	/** A printstream for a shortcut to print out to the console.*/
	private static PrintStream myOut ;
	/** 
	 * A scanner that is used to scan in different files and read from 
	 * the console.
	 * */
	private static Scanner myIn;

	//To store the calendar which contents all the auction passing from main.
	private Calendar myCalendar;

	private ArrayList<Auction> myCurrentAuctions;

	// A list of all items that the bidder has bided on.
	private ArrayList<BidedItem> myBidList = new ArrayList<BidedItem>();

	private String bidderName;

	/**
	 * @return the bidderName
	 */
	public String getBidderName() {
		return bidderName;
	}
	/**
	 * @param bidderName the bidderName to set
	 */
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}


	/**
	 * Constructor
	 * create object bidder.
	 */
	public Bidder(Calendar calendar, String name){
		this.myCalendar = calendar;
		myCurrentAuctions = calendar.getMyCurrentAuctions();
		this.bidderName = name;
		loadBidder();
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
	}


	/**
	 * Bidder interface
	 * print to console the option that bidder can do
	 * await for bidder input and control next task.
	 */
	public void bidderInterface(){
		String input;
		boolean back = false;
		while(!back){
			myOut.println("Welcome " + bidderName);
			myOut.println("Please enter a command:\n0:log out\n1: view bided items and make change\n2: view current auctions.\n");
			input = myIn.next();
			if(input.equals("0")){	//break the while loop, end bidderInterface(), return control back to main class.
				logout();
				back = true;
			}else if(input.equals("1")){
				viewCurrentBids();
			}else if(input.equals("2")){
				//print the current auction and pass the control to that method.
				auctionList();
			}else{
				myOut.println("Invalid input");
			}
		}

	}


	/**
	 * read in file only
	 * no Junit needed
	 * 
	 * If the bidder has used the system before, load the bidder infomation into the system.
	 */
	private void loadBidder(){
		int itemID;
		String auction;
		double bidPrice;
		try {
			myIn = new Scanner(new File(getBidderName() + ".txt"));
			while (myIn.hasNext()) {
				itemID = myIn.nextInt();
				auction = myIn.next();
				bidPrice = myIn.nextDouble();
				myBidList.add(new BidedItem(itemID, auction, bidPrice));
			}
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("bidder not found, login as new bidder");
		}
	}


	/**
	 * I/O only
	 *	No Junit needed 
	 *
	 * save the bidder info to a file for next time use
	 * logout and return control the main class.
	 */
	private void logout(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(getBidderName() + ".txt");
			for(int i = 0; i < myBidList.size(); i ++){
				writer.print(myBidList.get(i).itemID + " ");
				writer.print(myBidList.get(i).auctionName + " ");
				writer.println(myBidList.get(i).bidPrice);
			}		
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * I/O only
	 * No Junit needed
	 * 
	 * 
	 *  List all the items that the bidder has bided on.
	 * 
	 */
	private void viewCurrentBids ()
	{	
		String input;
		int choice = 0;
		boolean back = false;
		while(!back){
			for(int i = 0; i < myBidList.size(); i++){
				BidedItem temp = myBidList.get(i);
				myOut.println((i+1) + " -> " + temp.toString());
			}

			boolean validInput = false;
			while(!validInput){
				myOut.print("Please enter your selection to change the bid value of the item.\n Enter 0 to go back to the main menu.\n");
				input = myIn.next();
				try{
					choice = Integer.parseInt(input);
					validInput = true;
				}catch(Exception e){
					myOut.println("Invalid input");
				}
			}
			if(choice == 0 ){
				back = true;
			}else if(choice <= myBidList.size()){
				String auction = myBidList.get(choice-1).auctionName;
				int itemID = myBidList.get(choice-1).itemID;
				double newBid = 0;
				validInput = false;
				while(!validInput){
					myOut.println("Please enter new bid value for item ID: " + itemID + " from auction " + auction);
					myOut.println("Enter 0 to cancel bid on the item");
					input = myIn.next();
					try{
						newBid = Double.parseDouble(input);
						validInput = true;
					}catch(Exception e){
						myOut.println("Invalid input");
					}
				}
				if(newBid == 0.0){
					cancelBid(itemID, auction);
				}else{
					changeBid(itemID, auction, newBid);
				}
				myOut.println("Your item has been changed successful as below.\n");
			}else{
				myOut.println("Invalid input.\n");
			}
		}
	}



	/**
	 * Junit required
	 * 
	 * 
	 * go the the current auction to find the item and change the bid value, if successful, update the bidlist of the bidder.
	 * @param itemID
	 * @param auc
	 * @param newBid
	 */
	private void changeBid(int itemID, String auction, double newBid)
	{	
		for(int j = 0; j < myBidList.size(); j++){
			if(myBidList.get(j).auctionName == auction && myBidList.get(j).itemID == itemID){
				myBidList.get(j).bidPrice = newBid;
			}
		}
	}

	/**
	 * Junit required
	 * 
	 * cancel a bid from the bided list (remove)
	 * @param itemBid
	 * @param itemNumber
	 * @param item
	 * @param auction
	 */
	private void cancelBid(int itemID, String auc)
	{
		for(int i = 0; i < myBidList.size(); i++){
			if(myBidList.get(i).auctionName == auc && myBidList.get(i).itemID == itemID){
				myBidList.remove(i);
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
	private void auctionList(){
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
			}else if(choice <= myCurrentAuctions.size()){
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
	private void viewItemList(int auction)
	{
		boolean back = false;
		String input;
		int choice = 0;
		while(!back){	//items list of the selected auction.
			myOut.println("List of items for auction " + myCurrentAuctions.get(auction).getName() + "\n");
			myOut.println(myCurrentAuctions.get(auction).retrieveItemList());

			boolean validInput = false;
			while(!validInput){
				myOut.println("\nPlease enter an item ID number or 0 to go back to the auctions list.\n");
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
			}else {
				Items selectedItem = myCurrentAuctions.get(auction).getItem(choice);
				if(selectedItem == null){
					myOut.print("Cannot find your selection, please try again.\n");
				}else{
					//sub menu, view item details and place bid.
					viewItem(selectedItem, auction);						
				}
			}
		}
	}


	/**
	 * I/O only
	 * NO need Junit for this method.
	 * enterBid() is tested separately.
	 * 
	 * Print to console the details of the item of the auction aucID
	 * Ask the bidder to place a bid on this item, or enter 0 to go back to items list.
	 * If the bidder has already placed bid on this item and try to bid again, print out an error message,
	 * then automatic return to the items list.
	 * @param item
	 * @param aucID
	 */
	private void viewItem(Items item, int aucID)
	{
		boolean back = false;
		String input;
		int choice = 0;
		while(!back){
			myOut.println("Item's details: \n");
			myOut.println(item.toString() + "\n");

			boolean validInput = false;
			while(!validInput){
				myOut.println("Please enter 1 to place bid on this item or 0 to go back to item list. \n");
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
			}else if(choice == 1){
				myOut.println("Please enter your bid value.\n");
				choice = myIn.nextInt();

				//if place bid successful (bidder has never placed bid on this item before)
				//add this item to the bidder's bided list.
				if(myCurrentAuctions.get(aucID).getItem(item.getId()).addBid(bidderName, choice)){
					enterBid(item.getId(), myCurrentAuctions.get(aucID).getName(),choice);
				}else{
					myOut.println("Please select a different item.\n");
					break;
				}
			}else{
				myOut.println("Invalid input.");
			}
		}
	}

	/**
	 * JUNIT required
	 * 
	 * add an item to the bidder's bid list
	 */
	private void enterBid(int item, String auc, double bid)
	{
		BidedItem bidedItem = new BidedItem(item, auc, bid);
		myBidList.add(bidedItem);
	}



}

/**
 * an object that will represent an item that a bidder has bided on.
 * this will store the item details and the auction which the item is being sold.
 */
class BidedItem{
	int itemID;
	String auctionName;
	double bidPrice;
	public BidedItem(int itemID, String aucName, double bid){
		this.itemID = itemID;
		this.auctionName = aucName;
		this.bidPrice = bid;
	}
	public String toString(){
		return "Item ID: " + itemID + " from auction " + auctionName + ", bided value: " + bidPrice +"\n";
	}
}



