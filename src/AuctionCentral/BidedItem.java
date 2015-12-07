package AuctionCentral;

/**
 * an object that will represent an item that a bidder has bided on.
 * this will store the item details and the auction which the item is being sold.
 */
public class BidedItem{
	int itemID;
	String auctionName;
	double minBid;
	double bidPrice;
	public double getBidPrice(){
		return bidPrice;
	}
	public BidedItem(int itemID, String aucName, double minBid, double bid){
		this.itemID = itemID;
		this.auctionName = aucName;
		this.minBid = minBid;
		this.bidPrice = bid;
	}
	public String toString(){
		return "Item ID: " + itemID + " from auction " + auctionName + "minimum bid: " + minBid + ", bided value: " + bidPrice +"\n";
	}
}
