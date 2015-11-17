package users;

import static org.junit.Assert.*;

import org.junit.Test;

public class AuctionCentralEmployeeTest {

	AuctionCentralEmployee mine;
	@Test
	public void test() {
		Calendar calendar = new Calendar();
		mine = new AuctionCentralEmployee(calendar, "Joe");
	}
}
