/**
 * 
 */
package users;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Tan Pham
 *
 */

public class ItemsTest {
	private Items testItem;
	
	@Before
	public void setUp() throws Exception {
		testItem = new Items(1,"test", "test item", 3.5);
	}



	/**
	 * Test method for {@link AuctionCentral.Items#Items(int, java.lang.String, java.lang.String, double)}.
	 */
	@Test
	public void testItems() {
		testItem = new Items(1,"test", "test item", 3.5);
		assertNotNull("constructor fails: cannot create item.", testItem);
	}

	/**
	 * Test method for {@link AuctionCentral.Items#getMyName()}.
	 */
	@Test
	public void testGetMyName() {
		assertEquals("get item's name fails", testItem.getMyName(),"test");
	}

	/**
	 * Test method for {@link AuctionCentral.Items#setMyName(java.lang.String)}.
	 */
	@Test
	public void testSetMyName() {
		testItem.setMyName("testtest");
		assertEquals("set item's name fails", testItem.getMyName(),"testtest");
	}

	/**
	 * Test method for {@link AuctionCentral.Items#getMyDescription()}.
	 */
	@Test
	public void testGetMyDescription() {
		assertEquals("get item's description fails", testItem.getMyDescription(),"test item");
	}

	/**
	 * Test method for {@link AuctionCentral.Items#setMyDescription(java.lang.String)}.
	 */
	@Test
	public void testSetMyDescription() {
		testItem.setMyDescription("test set item");
		assertEquals("set item's name fails", testItem.getMyDescription(),"test set item");
	}

	/**
	 * Test method for {@link AuctionCentral.Items#setMinBid(double)}.
	 */
	@Test
	public void testSetMinBid() {
		testItem.setMinBid(4.5);
		assertEquals("set item's minimum bid fails", testItem.getMinBid(), 4.5, .01);
	}

	/**
	 * Test method for {@link AuctionCentral.Items#getId()}.
	 */
	@Test
	public void testGetId() {
		assertEquals("get item's ID fails", testItem.getId(), 1);
	}

	/**
	 * Test method for {@link AuctionCentral.Items#setId(int)}.
	 */
	@Test
	public void testSetId() {
		testItem.setId(2);
		assertEquals("set item's ID fails", testItem.getId(), 2);
	}

}