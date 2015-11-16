package AuctionCentral;

import java.util.ArrayList;
import java.util.List;

public class Calendar {

	//Class constants
	/** The max amount of current auctions.*/
	private static final int MAXCURRENTAUCTIONS = 25;
	/** The amount of days an organization must have between two auctions.*/
	private static final int MINDAYSBETWEENAUCTIONS = 365;
	/** The max number of auctions that are allowed in one day.*/
	private static final int MAXAUCTIONSPERDAY = 2;
	/** The minimum hours that there must be between two auctions in one day.*/
	private static final int MINHOURSBETWEENTWOAUCTIONS = 2;
	/** The max amount of auctions that can be held in any 7 day period.*/
	private static final int MAXPER7DAYS = 5;
	
	//Class variables
	/** A list of all the current auctions.*/
	private List<Auction> myCurrentAuctions;
	/** */
	private Scaner myInput;
	
	public Calendar() {
		myCurrentAuctions = new ArrayList<Auction>();
	}
}
