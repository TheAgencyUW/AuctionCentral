package users;

import java.io.PrintStream;
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
	 */
	public Bidder(Calendar calendar, String name){
		this.myCalendar = calendar;
		myCurrentAuctions = calendar.getMyCurrentAuctions();
		this.bidderName = name;
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
	}


	public void bidderInterface(){
		int input;
		while(true){
			myOut.println("Welcome " + bidderName);
			myOut.println("Please enter a command:\n0:log out\n1: view bided items and make change\n1: view current auctions.\n");
			input = myIn.nextInt();
			if(input == 0){	//break the while loop, end bidderInterface(), return control back to main class.
				break;
			}else if(input == 1){
				viewCurrentBids();
			}else if(input == 2){
				//print the current auction and pass the control to that method.
				auctionList();
			}else{
				myOut.println("Invalid input");
			}
		}

	}


	/**
	 *  List all the items that the bidder has bided on.
	 * 
	 */
	public void viewCurrentBids ()
	{	
		int input;
		while(true){
			for(int i = 0; i < myBidList.size(); i++){
				BidedItem temp = myBidList.get(i);
				myOut.println((i+1) + " -> " + temp.toString());
			}
			myOut.print("Please enter a number to change the bid value of the item.\n Enter 0 to go back to the main menu./n");
			input = myIn.nextInt();
			if(input == 0 ){
				break;
			}else if(input <= myBidList.size()){
				String auction = myBidList.get(input).auctionName;
				int itemID = myBidList.get(input).item.getId();
				myOut.println("Please enter new bid value for item ID: " + itemID + " from auction " + auction);
				input = myIn.nextInt();
				changeBid(itemID, auction, input);
			}else{
				myOut.println("Invalid input.\n");
			}
		}
	}



	/**
	 * go the the current auction to find the item and change the bid value, if successful, update the bidlist of the bidder.
	 * @param itemID
	 * @param auc
	 * @param newBid
	 */
	public void changeBid(int itemID, String auction, double newBid)
	{	
		for(int i = 0; i < myCurrentAuctions.size();i++){
			if(myCurrentAuctions.get(i).getName() == auction){
				if(myCurrentAuctions.get(i).getItem(itemID).editBid(bidderName, newBid)){
					for(int j = 0; j < myBidList.size(); j++){
						if(myBidList.get(j).auctionName == auction && myBidList.get(j).item.getId() == itemID){
							myBidList.get(j).bidPrice = newBid;
						}
					}
				}
			}
		}
		
	}

	/**
	 * cancel a bid from the bided list (remove)
	 * @param itemBid
	 * @param itemNumber
	 * @param item
	 * @param auction
	 */
	public void cancelBid(int itemID, String auc)
	{
		for(int i = 0; i < myBidList.size(); i++){
			if(myBidList.get(i).auctionName == auc && myBidList.get(i).item.getId() == itemID){
				myBidList.remove(i);
			}
		}
	}



	public void auctionList(){
		int input;
		while(true){	//Auction list
			myOut.println("Current auctions list\n\n");
			myOut.println(viewCurrentAuctions());
			myOut.println("Please enter your number of selection or 0 to go back to main menu.\n");
			input = myIn.nextInt();

			if(input == 0){	
				break;
			}else if(input <= myCurrentAuctions.size()){
				int auction = input -1;	//save selected auction.
				viewItemList(auction);
			}else{
				myOut.print("Cannot find your selection, please try again.\n");
			}

		}
	}

	public String viewCurrentAuctions()
	{
		String toreturn = "";
		for(int i = 0; i < myCalendar.getMyCurrentAuctions().size(); i++){
			toreturn += (i+1) + ". Auction name: " + myCalendar.getMyCurrentAuctions().get(i).getName() + 
					", auction date: " + myCalendar.getMyCurrentAuctions().get(i).getMyDate() + "/n";
		}
		return toreturn;
	}


	public void viewItemList(int auction)
	{
		int input;
		while(true){	//items list of the selected auction.
			myOut.println("List of items for auction " + myCurrentAuctions.get(auction) + "\n");
			myOut.println(myCurrentAuctions.get(auction).retrieveItemList());
			myOut.println("\nPlease enter an item ID number or 0 to go back to the auctions list.\n");
			input = myIn.nextInt();

			if(input == 0){	//break while loop go back to auction list.
				break;
			}else {
				Items selectedItem = myCurrentAuctions.get(auction).getItem(input);
				if(selectedItem == null){
					myOut.print("Cannot find your selection, please try again.\n");
				}else{
					viewItem(selectedItem, auction);						
				}
			}
		}
	}


	public void viewItem(Items item, int aucID)
	{
		int input;
		while(true){
			myOut.println("Item's details: \n");
			myOut.println(item.toString() + "\n");
			myOut.println("Please enter 1 to place bid on this item or 0 to go back to item list. \n");
			input = myIn.nextInt();
			if(input == 0){
				break;
			}else if(input == 1){
				myOut.println("Please enter your bid value.\n");
				input = myIn.nextInt();
				if(myCurrentAuctions.get(aucID).getItem(item.getId()).addBid(bidderName, input)){
					enterBid(item, myCurrentAuctions.get(aucID).getName(),input);
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
	 * 
	 * add an item to the bid list
	 */
	public void enterBid(Items item, String auc, double bid)
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
	Items item;
	String auctionName;
	double bidPrice;
	public BidedItem(Items item, String aucName, double bid){
		this.item = item;
		this.auctionName = aucName;
		this.bidPrice = bid;
	}
	public String toString(){
		return "Item ID: " + item.getId() + ", " + item.getMyName() +
				" from auction " + auctionName + "bided value: " + bidPrice +"\n";
	}
}