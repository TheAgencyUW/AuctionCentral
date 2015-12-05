package AuctionCentral.Test;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import AuctionCentral.Auction;

public class AuctionTest {
	private Auction test;

	@Before
	public void setUp() throws Exception {
		test = new Auction("testOrg", new GregorianCalendar(2014, java.util.Calendar.FEBRUARY, 11).getTime(), 9, 13);
		test.addItem("Item1", "test item 1", 3.5);
	}

	/**
	 * test the constructor
	 */
	@Test
	public void testAuctionConstructor() {
		test = new Auction("testOrg", new GregorianCalendar(2014, java.util.Calendar.FEBRUARY, 11).getTime(), 9, 13);
		//expect auction created. not null
		assertNotNull("constructor fails: cannot create item.", test);
	}

	@Test
	public void testAddItem() {
		test.addItem("Item2", "test item 2", 4.5);
		//expect item created. item list not null
		assertNotNull("add item fails", test.getItem(2));
	}

	@Test
	public void testRemoveItem() {
		test.removeItem(1);
		//expect item 1 remove. item 1 is null
		assertNull("remove item fails", test.getItem(1));
	}

	@Test
	public void testEditItemName() {
		test.editItemName(1, "renamed");
		//expect name change to new name "remaned"
		assertEquals("edit item name fails", test.getItem(1).getMyName(), "renamed");
	}

	@Test
	public void testEditItemDes() {
		test.editItemDes(1, "renamed item");
		//expect item's description change. "renamed item"
		assertEquals("edit item description fails", test.getItem(1).getMyDescription(), "renamed item");
	}

	@Test
	public void testEditItemMinBid() {
		test.editItemMinBid(1, 5.5);
		//expect min bid change to new value. 5.5
		assertEquals("edit item min bid fails", test.getItem(1).getMinBid(), 5.5, .01);
	}


	@Test
	public void testCopyAuction() {
		Auction copy = new Auction("copyOrg", new GregorianCalendar(2014, java.util.Calendar.FEBRUARY, 11).getTime(), 9, 13);
		test.copyAuction(copy);
		//expect test.toString = copy.toString
		assertEquals(test.toString(),copy.toString());
	}

}
