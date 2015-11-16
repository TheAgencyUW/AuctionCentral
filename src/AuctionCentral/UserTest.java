package users;
/*
 * Junit testing for user
 * 
 * Phillip M
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class UserTest 
{

	private User testUser;
	
	@Before
	public void setUp() throws Exception 
	{
		testUser = new User();
	}

	@Test
	public void testLogin() 
	{
		testUser.login("Test");
		assertEquals("User not found, test failed", testUser.toString(), "test");
		
	}

	@Test
	public void testToString() 
	{
		assertEquals("toStirng not found, test failed", testUser.toString(), "test");
	}

}