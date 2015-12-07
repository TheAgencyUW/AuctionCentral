package AuctionCentral.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import AuctionCentral.Bidder;

public class BidderTest {
	private Bidder bidder;
	@Before
	public void setUp() throws Exception {
		bidder = new Bidder("test bidder");
	}

	@Test
	public void testEnterBid() {
		bidder.enterBid(1, "auction 1" , 10.0, 15.0);
		assertNotNull(bidder.getBidList());
	}
	
	@Test
	public void testChangeBid(){
		bidder.enterBid(1, "auction 1" , 10.0, 15.0);
		bidder.changeBid(1, "auction 1", 20.0);
		assertEquals(bidder.getBidList().get(0).getBidPrice(), 20.0, .01);
	}

}
