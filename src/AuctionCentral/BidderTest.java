package AuctionCentral;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BidderTest 
{
	private Bidder bid;
	private Calendar cal;
	private String name;
	private int itemNum;
	private String auc;
	private Items item;
	
	@Before
	public void setUp() throws Exception 
	{
		bid = new Bidder(cal, name);
		itemNum = 0001;
		auc = "Auction";
		item = new Items(itemNum, "book", "This is a book", 10.00);
		
	}
	
	public void testBidder()
	{
		bid = new Bidder(cal, "Bob");
		assertNotNull("Bidder creation fail, no calendar", cal);
		assertNotNull("Bidder creation fail, no name", "Bob");
	}
	@Test
	public void testChangeBid()
	{
		bid.enterBid(itemNum, auc, 50);
		bid.changeBid(itemNum, auc, 75);
		assertEquals("Error, bid was not changed", 75.00, 50.00);
	}
	@Test
	public void testCancelBid()
	{
		bid.enterBid(itemNum, auc, 50);
		bid.cancelBid(itemNum, auc);
		assertEquals("Error, bid was not cancelled", null, 50.00);
	}
	
	@Test
	public void testEnterBid()
	{	
		bid.enterBid(itemNum, auc, 50);
		assertEquals("Error, bid was not entered", 50.00, null);
		
	}

	@Test
	public void testChangeOnHigherBid()
	{
		bid.enterBid(itemNum, auc, 50.00);
		bid.changeBid(itemNum, auc, 40.00);
		assertEquals("Bid is lower than current bid", item.getMinBid(), 40.00);
	}
	
	@Test
	public void placeOnLowerThanOrig()
	{
		bid.enterBid(itemNum, auc, 5);
		assertEquals("Bid is lower than the Minimum Starting Bid", item.getMinBid(), 5.00);
	}

}
