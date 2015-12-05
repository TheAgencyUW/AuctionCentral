/**
 * 
 */
package AuctionCentral.Test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import AuctionCentral.Items;
import AuctionCentral.PlaceBidException;

/**
 * @author Tan Pham
 *
 */
public class ItemsTest {
	private Items testItem;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testItem = new Items(1,"test", "test item", 3.5);
	}
	/**
	 * test for constructor.
	 * 
	 * Test method for {@link AuctionCentral.Items#Items(int, java.lang.String, java.lang.String, double)}.
	 */
	@Test
	public void testItemsConstructorOnCreateNewItem() {
		testItem = new Items(1,"testConstructorItem", "test item", 3.5);
		
		//expect testItem is created. Not null
		assertNotNull("constructor fails: cannot create item.", testItem);
	}

	
	/**
	 * Test on place bid less than minimum bid.
	 */
	@Test
	public void testAddBidOnAddingBidBelowMinimumBid(){
		testItem.getBidList().clear();
		try {
			testItem.addBid("testBuilder", 3.0);
		} catch (PlaceBidException e) {
			//expect a catch
		}
		//expect no bid add to the bidlist
		assertEquals(testItem.getBidList().size(), 0);
	}
	
	
	/**
	 * test on place a good bid
	 */
	@Test
	public void testAddBidWithGoodBid(){
		testItem.getBidList().clear();
		try {
			testItem.addBid("test bidder", 4.0);
		} catch (PlaceBidException e) {
			//Expected no catch here.
		}
		
		//expect add bid successful
		assertEquals(testItem.getBidList().get(0).getBidValue(), 4.0, .1);
	}
	
	
	/**
	 * Test on edit bid less than minimum bid.
	 */
	@Test
	public void testEditBidOnAddingBidBelowMinimumBid(){
		testItem.getBidList().clear();
		try {
			testItem.addBid("testBidder", 4.0);
		} catch (PlaceBidException e1) {
			//expect no catch here.
		}
		try {
			testItem.editBid("testBuilder", 3.0);
		} catch (PlaceBidException e) {
			//expect a catch
		}	
		
		//expect bid value stay the same
		assertEquals(testItem.getBidList().get(0).getBidValue(), 4.0, .1);
	}
	
	
	/**
	 * test on place a good bid
	 */
	@Test
	public void testEditBidWithGoodBid(){
		testItem.getBidList().clear();
		try {
			testItem.addBid("test bidder", 4.0);
		} catch (PlaceBidException e) {
			//Expected no catch here.
		}
		
		try {
			testItem.editBid("test bidder", 4.5);
		} catch (PlaceBidException e) {
			//expect no catch here
		}
		
		//expect edit bid successful
		assertEquals(testItem.getBidList().get(0).getBidValue(), 4.5, .1);
	}
	
	/**
	 * test if a bidder place bid twice on the same item.
	 */
	@Test
	public void testAddBidOnPlaceBidTwiceByTheSameBidder(){
		testItem.getBidList().clear();
		try {
			testItem.addBid("test bidder", 4.0);
		} catch (PlaceBidException e) {
			//Expected no catch here.
		}
		
		try {
			testItem.addBid("test bidder", 4.5);
		} catch (PlaceBidException e) {
			//expect a catch
		}
		//expect no bid added. size of bid list stay at 1
		assertEquals(testItem.getBidList().size(),1);
	}
	
}
