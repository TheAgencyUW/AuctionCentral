package AuctionCentral.Test;

import static org.junit.Assert.*;

import java.time.YearMonth;
import java.util.GregorianCalendar;

import org.junit.Test;

import AuctionCentral.AddAuctionException;
import AuctionCentral.Auction;
import AuctionCentral.MyCalendar;

public class MyCalendarTest {
	private MyCalendar cal;


	/**
	 * test constructor
	 */
	@Test
	public void testMyCalendar() {
		MyCalendar test = new MyCalendar("calendar");
		assertNotNull(test);
	}

	/**
	 * test add auction on a good auction that pass all the business rules
	 */
	@Test
	public void testAddAuctionWithGoodAuction() {
		cal = new MyCalendar("calendar");
		int currtSize = cal.getSizeOfCurrentAuction();
		try {
			cal.addAuction(new Auction("orgName", new GregorianCalendar(2016, 0, 7).getTime(), 8, 10));
		} catch (AddAuctionException e) {
			//expect no catch here
		}
		//expect the size of current auction list increase 1
		assertEquals(cal.getSizeOfCurrentAuction(), currtSize + 1);
	}


	/**
	 * test parse the year month from a date
	 */
	@Test
	public void testParseYearMonth() {
		cal = new MyCalendar("calendar");
		YearMonth ym = cal.parseYearMonth(new GregorianCalendar(2016, 0, 7).getTime());
		int year = ym.getYear();
		int month = ym.getMonthValue();
		//expect return the correct year and month
		assertEquals(year, 2016);
		assertEquals(month, 1);
	}

	/**
	 * test get the day from a date
	 */
	@Test
	public void testGetDayFromDate() {
		cal = new MyCalendar("calendar");
		//expect the correct day, 25.
		assertEquals(cal.getDayFromDate(new GregorianCalendar(2015, 11, 25).getTime()), 25);
	}

	/**
	 * test get the number of days between two dates
	 */
	@Test
	public void testGetDaysBetween() {
		cal = new MyCalendar("calendar");
		//expect the correct number in return: 0
		assertEquals(cal.getDaysBetween(new GregorianCalendar(2015, 11, 25).getTime(), new GregorianCalendar(2015, 11, 25).getTime()),0);
	}

	/**
	 * test a max auction calendar
	 */
	@Test
	public void testHasMaxAuctions_OnMaxCalendar() {
		cal = new MyCalendar("calendar25full");
		//expect return true.
		assertTrue(cal.hasMaxAuctions());
	}
	
	/**
	 * test a not full calendar
	 */
	@Test
	public void testHasMaxAuctions_OnNotFullCalendar() {
		cal = new MyCalendar("calendar");
		//expect return false.
		assertFalse(cal.hasMaxAuctions());
	}

	/**
	 * test pass max date into future on a good date
	 */
	@Test
	public void testHasPassedMaxDays_OnGoodDate() {
		cal = new MyCalendar("calendar");
		//expect return false
		assertFalse(cal.hasPassedMaxDays(new GregorianCalendar(2016, 0, 25).getTime()));
	}
	
	/**
	 * test pass max date into future on a date that more than 90 days
	 */
	@Test
	public void testHasPassedMaxDays_OnMoreThan90Days() {
		cal = new MyCalendar("calendar");
		//expect return false
		assertTrue(cal.hasPassedMaxDays(new GregorianCalendar(2016, 5, 25).getTime()));
	}

	/**
	 * test if there is more than 2 hours apart with a GOOD time
	 */
	@Test
	public void testHasMinTimeBetweenAuctions_OnGoodDate() {
		cal = new MyCalendar("calendar");
		assertTrue(cal.hasMinTimeBetweenAuctions(new GregorianCalendar(2015, 11, 10).getTime(), 5, 8));
	}
	
	
	/**
	 * test if there is more than 2 hours apart with a BAD time
	 */
	@Test
	public void testHasMinTimeBetweenAuctions_OnBadDate() {
		cal = new MyCalendar("calendar");
		assertFalse(cal.hasMinTimeBetweenAuctions(new GregorianCalendar(2015, 11, 10).getTime(), 10, 15));
	}

	/**
	 * test has maximum of two auction on the same date on a full date
	 */
	@Test
	public void testHasMaxPerDay_OnFullDate() {
		cal = new MyCalendar("calendar");
		//expect true
		assertTrue(cal.hasMaxPerDay(new GregorianCalendar(2016, 0, 21).getTime()));
	}
	
	/**
	 * test has maximum of two auction on the same date on a NOT full date
	 */
	@Test
	public void testHasMaxPerDay_OnNotFullDate() {
		cal = new MyCalendar("calendar");
		//expect false
		assertFalse(cal.hasMaxPerDay(new GregorianCalendar(2015, 11, 10).getTime()));
	}

	/**
	 * test has max per 7 day on good date
	 */
	@Test
	public void testHasMaxPer7Days_OnGoodDate() {
		cal = new MyCalendar("calendar");
		//expect false
		assertFalse(cal.hasMaxPer7Days(new GregorianCalendar(2015, 11, 12).getTime()));
	}

	/**
	 * test has max per 7 day on FULL date
	 */
	@Test
	public void testHasMaxPer7Days_OnFull7Date() {
		cal = new MyCalendar("calendar");
		//expect true
		assertTrue(cal.hasMaxPer7Days(new GregorianCalendar(2016, 1, 22).getTime()));
	}
	
	
	/**
	 * test for 365 days between each auction for each org
	 * test on good date
	 */
	@Test
	public void testMinDaysBetweenOrgAuctions_OnGoodDate() {
		cal = new MyCalendar("calendar");
		//expect true
		assertTrue(cal.minDaysBetweenOrgAuctions("Goodwill", new GregorianCalendar(2017, 11, 22).getTime()));
	}
	
	/**
	 * test for 365 days between each auction for each org
	 * test on good date
	 */
	@Test
	public void testMinDaysBetweenOrgAuctions_OnDateWithin365() {
		cal = new MyCalendar("calendar");
		//expect false
		assertFalse(cal.minDaysBetweenOrgAuctions("Goodwill", new GregorianCalendar(2016, 10, 22).getTime()));
	}

}
