package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MyCalendar {

	//Class constants
	/** The max amount of current auctions.*/
	private static final int MAXCURRENTAUCTIONS = 25;
	/** The amount of days an organization must have between two auctions.*/
	private static final int MINDAYSBETWEENAUCTIONS = 365;
	/** The max number of auctions that are allowed in one day.*/
	private static final int MAXAUCTIONSPERDAY = 2;
	/** The minimum hours that there must be between two auctions in one day.*/
	private static final int MINHOURSBETWEENTWOAUCTIONS = 2;
	/** The max amount of auctions that can be held in any 7 day period.*/
	private static final int MAXPER7DAYS = 5;
	/** The maximum amount of days before the auction that we are going to check.*/
	private static final int MAXDAYSBEFORECHECK = -2;
	/** The maximum amount of days after the auction that we are going to check.*/
	private static final int MAXDAYSAFTERCHECK = 2;
	/** 
	 * The max amount of days out from the current date an auction can be 
	 * scheduled.
	 */
	private static final int MAXDAYSOUT = 90;
	/** A date for the current date.*/
	private static final Date CURRENTDATE = new Date();
	
	//Class variables
	/** A list of all the current auctions.*/
	private List<Auction> myCurrentAuctions;
	/** A Scanner for input.*/
	private Scanner myInput;
	/** A string that holds a list of the different months*/
	private String[] myMonth = new String[12];
	/** A string that holds the different days of the week.*/
	private String[] myDays = new String[7];
	/** A map that holds a list of all the auctions by YearMonth.*/
	private Map<YearMonth, ArrayList<Auction>> myAuctionListByYearMonth;
	/** A map that holds a list of all the auctions by the different organization.*/
	private Map<String, ArrayList<Auction>> myAuctionsByOrg;
	
	//Class constructors
	public MyCalendar() {
		myCurrentAuctions = new ArrayList<Auction>();
		myAuctionListByYearMonth = new TreeMap<YearMonth, ArrayList<Auction>>();
		myAuctionsByOrg = new TreeMap<String, ArrayList<Auction>>();
		myInput = null;
		
		fillMonth();
		fillDays();
	}

	//Class methods
	/**
	 * Used to add an auction into the system.
	 * 
	 * @param theCurentAuction is the auction that you want to schedule.
	 * @throws HasMaxAuctionsExceptions
	 * @throws MinDaysNotPassedException
	 * @throws MaxPer7DaysException
	 * @throws MaxPerDayException
	 * @throws MoreTimeBetweenAuctionsException
	 * @throws MaxDaysPassedException 
	 * @throws MoreThan90Exception 
	 */
	public void addAuction(Auction theCurentAuction) 
			throws HasMaxAuctionsExceptions, MinDaysNotPassedException,
			MaxPer7DaysException, MaxPerDayException, 
			MoreTimeBetweenAuctionsException, MaxDaysPassedException {
		
		if (hasMaxAuctions()) {
			throw new HasMaxAuctionsExceptions("");
		} else if (!minDaysBetweenOrgAuctions(theCurentAuction.getMyOrg(),
				theCurentAuction.getMyDate())) {
			throw new MinDaysNotPassedException("");
		} else if (hasPassedMaxDays(theCurentAuction.getMyDate())) {
			throw new MaxDaysPassedException("");
		} else if (hasMaxPer7Days(theCurentAuction.getMyDate())){
			throw new MaxPer7DaysException("");
		} else if (hasMaxPerDay(theCurentAuction.getMyDate())){
			throw new MaxPerDayException("");
		} else if (!hasMinTimeBetweenAuctions(theCurentAuction.getMyDate(),
				getTimeInto24Hour(theCurentAuction.getMyStartTime()), 
				getTimeInto24Hour(theCurentAuction.getMyEndTime()))){
			throw new MoreTimeBetweenAuctionsException("");
		} else {
			addAuctionToCurrentAuctions(theCurentAuction);
			addAuctionToOrgList(theCurentAuction);
			addAuctionToYearMonth(theCurentAuction);
		}
	}

	/**
	 * Takes a string that is in 12 hour time format with am or pm at the end, and
	 * returns an integer representation of its respected 24 hour time format.
	 * 
	 * @param theTime a string representation of 12 hour time format.
	 * @return an integer representing 24 hour time format.
	 */
	protected int getTimeInto24Hour(String theTime) {
		int time = Integer.parseInt(theTime.substring(0, theTime.length() - 2));
		if ("pm".equals(theTime.substring(theTime.length() - 2))) {
			time += 12;
		}
		
		return time;
	}
	
	/**
	 * Takes in a Date and spits out the month and year as a YearMonth obj.
	 * 
	 * @param theDate is the date that you want the year month of.
	 * @return a YearMonth object that has the year and month of the date that
	 * was passed in.
	 */
	protected YearMonth parseYearMonth(Date theDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
	}
	
	/**
	 * 
	 * @param theDate of the auction that you want to add.
	 * @return
	 */
	protected int getDayFromDate(Date theDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Calculates the days between the current date and another date.
	 * 
	 * @param theDate the date that we want to know how many days are from the 
	 * current date.
	 * @return a long representing the days between current date and another date.
	 */
	protected long getDaysBetween(Date theFirstDate, Date theSecondDate) {
		return ((theFirstDate.getTime() - theSecondDate.getTime()) * 24 * 60 * 60 * 1000);
	}
	
	/**
	 * Checks to see if there is already the max amount of auctions that are in
	 * the future.
	 * 
	 * @return true if there is at least the MAXCURRENTAUCTIONS.
	 */
	public boolean hasMaxAuctions() {
		return (myCurrentAuctions.size() >= MAXCURRENTAUCTIONS);
	}
	
	/**
	 * Checks to see if date of the auction is within a set time period of 
	 * MAXDAYSOUT.
	 * 
	 * @param theDate is the date of the auction.
	 * @return true if the date of the auction is within the time period of now
	 * till MAXDAYSOUT. 
	 */
	public boolean hasPassedMaxDays(Date theDate) {
		return 	getDaysBetween(CURRENTDATE, theDate) < MAXDAYSOUT; 
	}
	
	/**
	 * Checks to make sure that there is at least MINHOURSBETWEENTWOAUCTION if
	 * there is already another auction scheduled for the same date.
	 * 
	 * @param theDate of the auction that you want to add.
	 * @param theStartTime 
	 * @param theEndTime
	 * @return true if there is at least MINHOURSBETWEENTWOAUCTION the current
	 * auction and the one we want to add.
	 */
	public boolean hasMinTimeBetweenAuctions(Date theDate, int theStartTime,
			int theEndTime) {
		YearMonth ym = parseYearMonth(theDate);
		int day = getDayFromDate(theDate);
		boolean passed = true;
		
		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> checkList = myAuctionListByYearMonth.get(ym);
			for (int i = 0; i < checkList.size() && passed; i++) {
			}
		}
		
		return passed;
	}

	/**
	 * Checks to see if there are less then MAXAUCTIONSPERDAY on any given date.
	 * 
	 * @param theDate of the auction that you want to add.
	 * @return true if there is less then MAXAUCTIONSPERDAY already scheduled.
	 */
	public boolean hasMaxPerDay(Date theDate) {
		YearMonth ym = parseYearMonth(theDate);
		int days = 0;
		boolean passed = true;
				
		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> checkList = myAuctionListByYearMonth.get(ym);
			for (int i = 0; i < checkList.size() && passed; i++) {
				if (getDaysBetween(checkList.get(i).getMyDate(), theDate) == 0) {
					days++;
				}
				passed = (days >= MAXAUCTIONSPERDAY);
			}
		}
		
		return passed;
	}

	/**
	 *  
	 * @param theDate of the auction that you want to add.
	 * @return
	 */
	public boolean hasMaxPer7Days(Date theDate) {
		YearMonth ym = parseYearMonth(theDate);
		int weekCount = 0;
		int daysBetween = 0;
		boolean passed = false;
		
		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> checkList = myAuctionListByYearMonth.get(ym);
			for (int i = 0; i < checkList.size() && !passed; i++) {
				daysBetween = (int) getDaysBetween(checkList.get(i).getMyDate(), theDate);
				
				if (daysBetween >= MAXDAYSBEFORECHECK && daysBetween <= MAXDAYSAFTERCHECK) {
					weekCount++;
				}
				passed = (weekCount >= MAXPER7DAYS);
			}
		}
		
		return passed;
	}

	/**
	 * 
	 * 
	 * @param theOrg
	 * @param theDate
	 * @return
	 */
	public boolean minDaysBetweenOrgAuctions(String theOrg, Date theDate) {
		boolean passDays = true;
		ArrayList<Auction> orgAuctions;
		
		if (myAuctionsByOrg.containsKey(theOrg)) {
			orgAuctions = myAuctionsByOrg.get(theOrg);
			for (int i = 0; i < orgAuctions.size() && passDays; i++) {
				passDays = (getDaysBetween(orgAuctions.get(i).getMyDate(),
						theDate) >= MINDAYSBETWEENAUCTIONS);
			}
		}
		
		return passDays;
	}
	
	/**
	 * Creates a string that is a list of all the current auctions.
	 * 
	 * @return the list of all the current auctions.
	 */
	public String getListOfCurrentAuctions() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("");
		for (int i = 0; i < myCurrentAuctions.size(); i++) {
			sb.append(i + 1);
			sb.append(") ");
			sb.append(myCurrentAuctions.get(i));
		}
			
		return sb.toString();
	}
	
	/**
	 * Prints the current list of auctions to the console.
	 */
	public void printListOfCurrentAuctions() {
		System.out.println(getListOfCurrentAuctions());
	}
	
	/**
	 * Creates a string representation of the current month with the different
	 * auctions put in.
	 * 
	 * @param theYearMonth is the year and month that you want to view.
	 * @return a string representation of the month calendar.
	 */
	public String getAnyMonthCalendarView(YearMonth theYearMonth) {
		StringBuilder sb = new StringBuilder();
		//needs to be done.
		return sb.toString();
	}
	
	/**
	 * Creates a string representation of the current month with the different
	 * auctions put in.
	 * 
	 * @return a string representation of the month calendar.
	 */
	public String getCurrentMonthCalendarView() {
		return getAnyMonthCalendarView(YearMonth.now());
	}
	
	/**
	 * Prints any months calendar to the console.
	 */
	public void printAnyMonthCalendar(YearMonth theYearMonth) {
		System.out.println(getAnyMonthCalendarView(theYearMonth));
	}
	
	/**
	 * Prints the current month calendar to the console.
	 */
	public void printCurrentMonthCalendar() {
		System.out.println(getCurrentMonthCalendarView());
	}
	
	/**
	 * Adds the auction to the myAuctionListByYearMonth in its apporiate YearMonth.
	 * 
	 * @param theCurentAuction
	 */
	private void addAuctionToYearMonth(Auction theCurentAuction) {
		YearMonth ym = parseYearMonth(theCurentAuction.getMyDate());
		if (!myAuctionListByYearMonth.containsKey(ym)) {
			myAuctionListByYearMonth.put(ym, new ArrayList<Auction>());
		}
		ArrayList<Auction> newList = myAuctionListByYearMonth.get(ym);
		newList.add(theCurentAuction);
		//sort list
		myAuctionListByYearMonth.put(ym, newList);
	}

	/**
	 * Puts the auction in the myAuctionsByOrg map under the proper organization.
	 * 
	 * @param theCurentAuction is the auction that we are currently trying to add.
	 */
	private void addAuctionToOrgList(Auction theCurentAuction) {
		if (!myAuctionsByOrg.containsKey(theCurentAuction.getMyOrg())) {
			myAuctionsByOrg.put(theCurentAuction.getMyOrg(), new ArrayList<Auction>());
		}
		
		ArrayList<Auction> newList = myAuctionsByOrg.get(theCurentAuction.getMyOrg());
		newList.add(theCurentAuction);
		//sort list
		myAuctionsByOrg.put(theCurentAuction.getMyOrg(), newList);
	}

	/**
	 * Adds the current auction to the current auction list if the auctions date
	 * is after todays date.
	 * 
	 * @param theCurentAuction is the auction that we are currently trying to add.
	 */
	private void addAuctionToCurrentAuctions(Auction theCurentAuction) {
		if (theCurentAuction.getMyDate().getTime() > CURRENTDATE.getTime()) {
			myCurrentAuctions.add(theCurentAuction);
			// need to sort list.
		}
	}
	
	/**
	 * Fills the string that holds the different months.
	 */
	private void fillMonth() {
		int i = 0;
		try {
			myInput = new Scanner(new File("Months.txt"));
			while (myInput.hasNext()) {
				myMonth[i] = myInput.nextLine();
				i++;
			}
			myInput.close();
		} catch (FileNotFoundException e) {
			System.out.println("No months found");
		}
	}
	
	/**
	 * Fills the string that holds the days of the week.
	 */
	private void fillDays() {
		int i = 0;
		try {
			myInput = new Scanner(new File("Days.txt"));
			while (myInput.hasNext()) {
				myDays[i] = myInput.nextLine();
				i++;
			}
			myInput.close();
		} catch (FileNotFoundException e) {
			System.out.println("No days found");
		}
	}
}
