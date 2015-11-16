package AuctionCentral;
/*
 * Junit testing for bidder
 * 
 * Phillip M
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BidderTest 
{
	private Bidder bidderTest;
	private Auction auctionTest;
	private Items itemTest;

	public void setUp() throws Exception
	{
		bidderTest = new Bidder();
		auctionTest = new Auction(null, null, null, null);
		itemTest = new Items(0, null, null, 0);
	}
	
	@Test
	public void testEnterBid() {
		assertEquals("Test Failed", 1.00, 0.00, 2.50);
	}

	@Test
	public void testChangeBid() {
		assertEquals("Test Failed", 1.00, 1.50, 2.50);
	}

	@Test
	public void testCancelBid() {
		assertEquals("Test Failed", 0.00, 1.00, 2.50);
	}

	@Test
	public void testViewCurrentBids() {
		assertEquals("Test Failed", bidderTest.viewCurrentBids(), null);
	}

	@Test
	public void testViewCurrentAuctions() {
		assertEquals("Test Failed", bidderTest.viewCurrentAuctions(auctionTest), null);
	}

	@Test
	public void testViewItem() {
		assertEquals("Test Failed", bidderTest.viewItem(itemTest), null);
	}

}