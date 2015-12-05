package AuctionCentral;

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
	public ArrayList<Bids> getBidList(){
		return bidList;
	}


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
	public boolean addBid(String bidder, double bidValue) throws PlaceBidException{
		if(bidValue<minBid){
			throw new PlaceBidException("Your bid cannot be less than minimum bid.");
		}
		for(int i = 0; i <bidList.size(); i++){
			if(bidList.get(i).getBidder() == bidder){
				throw new PlaceBidException("A bidder cannot place more than one bid on the same item. \n"
						+ "If you want to change you bid value, please select edit bid option in your bided list.");
			}			
		}

		Bids bid = new Bids(bidder, bidValue);
		bidList.add(bid);
		
		return true;
	}

	/**
	 * edit a bid value of the item
	 * return true if edit successful.
	 * @param bidder
	 * @param newBid
	 * @return
	 */
	public boolean editBid(String bidder, double newBid) throws PlaceBidException{
		boolean bidderExist = false;
		for(int i = 0; i <bidList.size(); i++){
			if(bidList.get(i).getBidder() == bidder){
				if(newBid < minBid) throw new PlaceBidException("Your bid cannot less than the minimum bid.\n");
				bidList.get(i).setBidValue(newBid);
				bidderExist = true;
			}			
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



