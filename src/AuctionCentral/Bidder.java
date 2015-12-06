package AuctionCentral;

import java.util.ArrayList;


/** 
 * bidder.java
 * 
 * @author Tan Pham, Phillip Mishchuk
 * 
 */

public class Bidder
{
	
	// A list of all items that the bidder has bided on.
	private ArrayList<BidedItem> myBidList;

	//the bidder name
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
	 * 
	 * @return the bidder bided list.
	 */
	public ArrayList<BidedItem> getBidList(){
		return myBidList;
	}



	/**
	 * Constructor
	 * create object bidder.
	 */
	public Bidder(String name){
		this.bidderName = name;
		myBidList = new ArrayList<BidedItem>();
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
	protected boolean changeBid(int itemID, String auction, double newBid){	
		boolean successful = false;
		for(int j = 0; j < myBidList.size(); j++){
			if(myBidList.get(j).auctionName == auction && myBidList.get(j).itemID == itemID && newBid > myBidList.get(itemID).minBid){
				myBidList.get(j).bidPrice = newBid;
				successful = true;
			}
		}
		return successful;
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
	protected void enterBid(int item, String auc, double min, double bid)
	{
		BidedItem bidedItem = new BidedItem(item, auc, min, bid);
		myBidList.add(bidedItem);
	}
}





