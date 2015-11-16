package users;

import java.util.ArrayList;

/**
 * 
 * @author Tan Pham
 *
 */
public class Items {
	private int id;
	private String myName;
	private String myDescription;
	private double minBid;
	private ArrayList<Bids> bidList = new ArrayList<Bids>();


	/**
	 * Constructor.
	 * @param id
	 * @param name
	 * @param des
	 * @param minBid
	 */
	public Items(int id, String name, String des, double minBid){
		this.myName = name;
		this.myDescription = des;
		this.minBid = minBid;
		this.id = id;	
	}
	
	public String toString(){
		return "ID: " + id + ", Item name: " + myName + ", Minimum bid: " + minBid + ", Item description: " + myDescription;
	}

	/**
	 * add a bid of a bid list to compute who will be the winner when the auction over.
	 * return true if add successful.
	 * @param bidder
	 * @param bidValue
	 */
	public boolean addBid(String bidder, double bidValue){
		boolean bidderExist = false;
		for(int i = 0; i <bidList.size(); i++){
			if(bidList.get(i).getBidder() == bidder){
				System.out.println("A bidder cannot place more than one bid on the same item. \n"
						+ "If you want to change you bid value, please select edit bid option in your bided list.");
				bidderExist = true;
			}			
		}
		if(!bidderExist){
			Bids bid = new Bids(bidder, bidValue);
			bidList.add(bid);
		}
		return !bidderExist;//if the bidder does not exist, add successful, return true. else return false
	}

	/**
	 * edit a bid value of the item
	 * return true if edit successful.
	 * @param bidder
	 * @param newBid
	 * @return
	 */
	public boolean editBid(String bidder, double newBid){
		boolean bidderExist = true;
		for(int i = 0; i <bidList.size(); i++){
			if(bidList.get(i).getBidder() == bidder){
				bidList.get(i).setBidValue(newBid);
			}			
		}
		if(!bidderExist){
			System.out.println("You have not placed any bid on this item. \n"
					+ "If you want to place a bid, please select place bid option.");
			bidderExist = false;
		}
		return bidderExist;	//if the bidder exists, edit successful, return true. else return false
	}



	/**
	 * @return the myName
	 */
	public String getMyName() {
		return myName;
	}
	/**
	 * @param myName the myName to set
	 */
	public void setMyName(String myName) {
		this.myName = myName;
	}
	/**
	 * @return the myDescription
	 */
	public String getMyDescription() {
		return myDescription;
	}
	/**
	 * @param myDescription the myDescription to set
	 */
	public void setMyDescription(String myDescription) {
		this.myDescription = myDescription;
	}
	/**
	 * @return the minBid
	 */
	public double getMinBid() {
		return minBid;
	}
	/**
	 * @param minBid the minBid to set
	 */
	public void setMinBid(double minBid) {
		this.minBid = minBid;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
class Bids{
	private String bidder;
	private double bidValue;

	Bids(String bidder, double bidValue){
		this.bidder = bidder;
		this.bidValue = bidValue;
	}
	/**
	 * @return the bidder
	 */
	public String getBidder() {
		return bidder;
	}
	/**
	 * @param bidder the bidder to set
	 */
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
	/**
	 * @return the bidValue
	 */
	public double getBidValue() {
		return bidValue;
	}
	/**
	 * @param bidValue the bidValue to set
	 */
	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}

}