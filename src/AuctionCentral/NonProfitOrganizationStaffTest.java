package users;

import static org.junit.Assert.*;

import org.junit.Test;

public class NonProfitOrganizationStaffTest {

	NonProfitOrganizationStaff mine;
	
	@Test
	public void test() {
		Calendar calendar = new Calendar();
		mine = new NonProfitOrganizationStaff(calendar, "Goodwill", "John");
		assertTrue(mine.myAddAuction);
		assertFalse(mine.myAddItem);
		assertEquals(mine.myCounter, 0);
		assertEquals(mine.myDate, null);
		assertEquals(mine.myDay, 0);
		assertFalse(mine.myEditAuction);
		assertFalse(mine.myEditItem);
		assertEquals(mine.myEnd, null);
		assertEquals(mine.myMonth, null);
		assertEquals(mine.myStart, null);
		assertEquals(mine.myYear, 0);
		assertEquals(mine.title, null);
	}
	
	@Test
	public void addNewAuctionTest() {
		mine.addNewAuction();
	}
	
	@Test
	public void editAuctionTest() {
		mine.editAuction();
	}
	
	@Test
	public void addNewItemTest() {
		mine.addNewItem();
	}
	
	@Test
	public void editItemTest() {
		mine.editItem();
	}
}
