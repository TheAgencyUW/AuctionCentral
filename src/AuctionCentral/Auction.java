package AuctionCentral;
/**
 * @author Tan Pham
 */

import java.util.ArrayList;
import java.util.Date;

public class Auction {
	private String myName;
	private String myOrg;
	private int myStartTime;
	private int myEndTime;
	private Date myDate;
	private ArrayList<Items> myItems = new ArrayList<Items>();
	private int ItemID;
	
	/**
	 * Constructor.
	 * @param org
	 * @param date
	 * @param startTime
	 * @param endTime
	 */
	public Auction(String org, Date date, int startTime, int endTime){
		myOrg = org;
		myStartTime = startTime;
		myEndTime = endTime;
		myDate = date;
		myName = myOrg + " - " + myDate;
		ItemID = 1;
	}
	
	
	/**
	 * add an item into items list
	 */
	public void addItem(String name, String des, double minBid){
		Items item = new Items(ItemID++, name, des, minBid);
		myItems.add(item);
	}
	
	public Items getItem(int itemID){
		for(int i = 0; i < myItems.size(); i++){
			if(myItems.get(i).getId() == itemID){
				return myItems.get(i);
			}
		}
		return null;
	}
	
	public void removeItem(int itemID){
		for(int i = 0; i <myItems.size(); i++){
			if(myItems.get(i).getId() == itemID){
				myItems.remove(i);
			}
		}
	}
	/**
	 * edit the name of the item with ID#
	 * @param itemID
	 * @param name
	 */
	public void editItemName(int itemID, String name){
		for(int i = 0; i <myItems.size(); i++){
			if(myItems.get(i).getId() == itemID){
				myItems.get(i).setMyName(name);
			}
		}	
	}
	
	/**
	 * edit the description of the item with ID#
	 * @param itemID
	 * @param name
	 */
	public void editItemDes(int itemID, String des){
		for(int i = 0; i <myItems.size(); i++){
			if(myItems.get(i).getId() == itemID){
				myItems.get(i).setMyDescription(des);
			}
		}	
	}
	
	/**
	 * edit the minimum bid price of the item with ID#
	 * @param itemID
	 * @param name
	 */
	public void editItemMinBid(int itemID, double minBid){
		for(int i = 0; i <myItems.size(); i++){
			if(myItems.get(i).getId() == itemID){
				myItems.get(i).setMinBid(minBid);
			}
		}		
	}
	
	public String retrieveItemList(){
		String toreturn = "";
		for(int i = 0; i < myItems.size(); i++){
			toreturn += "Item ID: " + myItems.get(i).getId() +", Item name: " + myItems.get(i).getMyName() + 
					", Item minimum bid: " + myItems.get(i).getMinBid() +"\n";
		}
		return toreturn;
	}
	/**
	 * @return the myName
	 */
	public String getName(){
		return myName;
	}
	/**
	 * @return the myOrg
	 */
	public String getMyOrg() {
		return myOrg;
	}
	/**
	 * @param myOrg the myOrg to set
	 */
	public void setMyOrg(String myOrg) {
		this.myOrg = myOrg;
	}
	/**
	 * @return the myStartTime
	 */
	public int getMyStartTime() {
		return myStartTime;
	}
	/**
	 * @param myStartTime the myStartTime to set
	 */
	public void setMyStartTime(int myStartTime) {
		this.myStartTime = myStartTime;
	}
	/**
	 * @return the myEndTime
	 */
	public int getMyEndTime() {
		return myEndTime;
	}
	/**
	 * @param myEndTime the myEndTime to set
	 */
	public void setMyEndTime(int myEndTime) {
		this.myEndTime = myEndTime;
	}
	/**
	 * @return the myDate
	 */
	public Date getMyDate() {
		return myDate;
	}
	/**
	 * @param myDate the myDate to set
	 */
	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}
	
}
