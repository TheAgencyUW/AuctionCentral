package AuctionCentral;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BidderTest 
{
	private Bidder bid;
	private Calendar cal;
	private String name;
	
	@Before
	public void setUp() throws Exception 
	{
		bid = new Bidder(cal, name);
	}
	
	public void testBidder()
	{
		bid = new Bidder(cal, "Bob");
		assertNotNull("Bidder creation fail, no calendar", cal);
		assertNotNull("Bidder creation fail, no name", "Bob");
	}
	
	public void testChangeBid()
	{
		fail("Not yet implemented");
	}
	
	public void testCancelBid()
	{
		fail("Not yet implemented");
	}
	
	public void testEnterBid()
	{
		fail("Not yet implemented");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
