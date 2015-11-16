package users;

/**
 * @author Tan Pham
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AuctionTest {
	
	private Auction test;

	@Before
	public void setUp() throws Exception {
		test = new Auction("testOrg", "10/10/2010", "9am", "1pm");
		test.addItem("Item1", "test item 1", 3.5);
	}

	@Test
	public void testAuction() {
		test = new Auction("testOrg", "10/10/2010", "9am", "1pm");
		assertNotNull("constructor fails: cannot create item.", test);
	}

	@Test
	public void testAddItem() {
		test.addItem("Item2", "test item 2", 4.5);
		assertNotNull("add item fails",test.getItem(2));
	}

	@Test
	public void testRemoveItem() {
		test.removeItem(1);
		assertNull("remove item fails", test.getItem(1));
	}

	@Test
	public void testEditItemName() {
		test.editItemName(1, "renamed");
		assertEquals("edit item name fails", test.getItem(1).getMyName(), "renamed");
	}

	@Test
	public void testEditItemDes() {
		test.editItemDes(1, "renamed item");
		assertEquals("edit item description fails", test.getItem(1).getMyDescription(), "renamed item");
	}

	@Test
	public void testEditItemMinBid() {
		test.editItemMinBid(1, 5.5);
		assertEquals("edit item min bid fails", test.getItem(1).getMinBid(), 5.5, .01);
	}

	@Test
	public void testRetrieveItemList() {
		String str = test.retrieveItemList();
		assertNotNull("retrieve items list fails", str);
	}

	@Test
	public void testGetName() {
		assertEquals("get auction name fails", test.getName(), "testOrg - 10/10/2010");
	}

	@Test
	public void testGetMyOrg() {
		assertEquals("get organization name fails", test.getMyOrg(), "testOrg");
		}

	@Test
	public void testSetMyOrg() {
		test.setMyOrg("newOrg");
		assertEquals("set organization fails", test.getMyOrg(), "newOrg");
	}

	@Test
	public void testGetMyStartTime() {
		assertEquals("get start time fails", test.getMyStartTime(), "9am");
	}

	@Test
	public void testSetMyStartTime() {
		test.setMyStartTime("10am");
		assertEquals("set start time fails", test.getMyStartTime(), "10am");
	}

	@Test
	public void testGetMyEndTime() {
		assertEquals("get end time fails", test.getMyEndTime(), "1pm");
	}

	@Test
	public void testSetMyEndTime() {
		test.setMyEndTime("2pm");
		assertEquals("set end time fails", test.getMyEndTime(), "2pm");
	}

	@Test
	public void testGetMyDate() {
		assertEquals("get date fails", test.getMyDate(), "10/10/2010");
	}

	@Test
	public void testSetMyDate() {
		test.setMyDate("11/11/2011");
		assertEquals("set date fails", test.getMyDate(), "11/11/2011");
	}

}