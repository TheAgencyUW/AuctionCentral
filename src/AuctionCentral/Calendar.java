package AuctionCentral;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Calendar {

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
	private Map<YearMonth, ArrayList<String>> myAuctionListByYearMonth;
	/** A map that holds a list of all the auctions by the different organization.*/
	private Map<String, ArrayList<String>> myAuctionsByOrg;
	
	public Calendar() {
		myCurrentAuctions = new ArrayList<Auction>();
		myAuctionListByYearMonth = new TreeMap<YearMonth, ArrayList<String>>();
		myInput = null;
		
		fillMonth();
		fillDays();
	}

	public void addAuction(Auction theCurentAuction) throws HasMaxAuctionsExceptions {
		if (hasMaxAuctions()) {
			throw new HasMaxAuctionsExceptions("");
		} else if (!minDaysBetweenOrgAuctions(theCurentAuction.getMyOrg(), )) {
			
		}
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
