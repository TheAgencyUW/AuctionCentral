package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	/** A PrintStream for a shortcut to print out to the console.*/
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
		//myCurrentAuctions = calendar.getMyCurrentAuctions();
		this.bidderName = name;
		loadBidder();
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
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
	 * Junit required
	 * 
	 * 
	 * go the the current auction to find the item and change the bid value, if successful, update the bidlist of the bidder.
	 * @param itemID
	 * @param auc
	 * @param newBid
	 */
	protected void changeBid(int itemID, String auction, double newBid)
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
	protected void cancelBid(int itemID, String auc)
	{
		for(int i = 0; i < myBidList.size(); i++){
			if(myBidList.get(i).auctionName == auc && myBidList.get(i).itemID == itemID){
				myBidList.remove(i);
			}
		}
	}

	/**
	 * JUNIT required
	 * 
	 * add an item to the bidder's bid list
	 */
	protected void enterBid(int item, String auc, double bid)
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



