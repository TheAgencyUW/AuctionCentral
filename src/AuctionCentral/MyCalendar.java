package AuctionCentral;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MyCalendar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 364146438338826843L;
	//Class constants
	/** The max amount of current auctions.*/
	private static final int MAX_CURRENT_AUCTIONS = 25;
	/** The amount of days an organization must have between two auctions.*/
	private static final int MIN_DAYS_BETWEEN_AUCTIONS = 365;
	/** The max number of auctions that are allowed in one day.*/
	private static final int MAX_AUCTIONS_PER_DAY = 2;
	/** The minimum hours that there must be between two auctions in one day.*/
	private static final int MIN_HOURS_BETWEEN_TWO_AUCTIONS = 2;
	/** The max amount of auctions that can be held in any 7 day period.*/
	private static final int MAX_PER_7_DAYS = 5;
	/** The maximum amount of days before the auction that we are going to check.*/
	private static final int MAX_DAYS_BEFORE_CHECK = -2;
	/** The maximum amount of days after the auction that we are going to check.*/
	private static final int MAX_DAYS_AFTER_CHECK = 2;
	/** 
	 * The max amount of days out from the current date an auction can be 
	 * scheduled.
	 */
	private static final int MAX_DAYS_OUT = 90;
	/** A date for the current date.*/
	private static final Date CURRENT_DATE = new Date();

	//Class variables
	/** A list of all the current auctions.*/
	private ArrayList<Auction> myCurrentAuctions;
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
	
	private String calName;

	//Class constructors
	public MyCalendar(String name) {
		myCurrentAuctions = new ArrayList<Auction>();
		myAuctionListByYearMonth = new TreeMap<YearMonth, ArrayList<Auction>>();
		myAuctionsByOrg = new TreeMap<String, ArrayList<Auction>>();
		myInput = null;
		calName = name;
		loadCalendar(this);
		fillMonth();
		fillDays();
	}
	

	/**
	 * 
	 */
	public void saveCalendarState() {
		try {
			FileOutputStream fileOut = new FileOutputStream(calName + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch(IOException i){
		
		}
	}
	/**
	 * 
	 */
	private void loadCalendar(MyCalendar cal){
		try {
			FileInputStream fileIn = new FileInputStream(calName + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			cal = (MyCalendar) in.readObject();
			in.close();
			fileIn.close();
		} catch (ClassNotFoundException | IOException c) {
			makeNewCalendar(calName + ".txt");
		}
	}

	/**
	 * 
	 */
	private void makeNewCalendar(String file) {
		PrintStream myOut = new PrintStream(System.out, true);
		/** 
		 * A scanner that is used to scan in different files and read from 
		 * the console.
		 * */
		Scanner myIn;
		String orgName;
		int year, month, day, start, end;
		double item1Bid, item2Bid;
		try {
			myIn = new Scanner(new File(file));
			while (myIn.hasNextLine()&&myIn.hasNext()) {
				orgName = myIn.next();
				year = myIn.nextInt();
				month = myIn.nextInt() - 1;
				day = myIn.nextInt();
				start = myIn.nextInt();
				end = myIn.nextInt();
				item1Bid = myIn.nextDouble();
				item2Bid = myIn.nextDouble();
				Auction toAdd = new Auction(orgName, new GregorianCalendar(year, month, day).getTime(), start, end);
				toAdd.addItem("item1", "item 1 from auction 1", item1Bid);
				toAdd.addItem("item2", "item 2 from auction 1", item2Bid);
				try {
					addAuction(toAdd);
				} catch (AddAuctionException e) {
					myOut.print(e.getMessage());
				}
			}
			
			myIn.close();
		} catch (FileNotFoundException e) {
			myOut.println("No calendar found");
		}
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
	public boolean addAuction(Auction theCurentAuction) 
			throws AddAuctionException {

		if (hasMaxAuctions()) {
			throw new AddAuctionException("HasMaxAuctionsExceptions\nWe already reached our maximum of event at this time.\n"
					+ "Please try to contact us in the next month.\n");
		} else if (!minDaysBetweenOrgAuctions(theCurentAuction.getMyOrg(),
				theCurentAuction.getMyDate())) {
			throw new AddAuctionException("MinDaysNotPassedException\n You already had an auction with us this year.\n"
					+ "Please try to contact us again next year.\n");
		} else if (hasPassedMaxDays(theCurentAuction.getMyDate())) {
			throw new AddAuctionException("MaxDaysPassedException\nThe day you have chosen is to far to the future.\n"
					+ "Please select a day within 90 calendar days from now.\n");
		} else if (hasMaxPer7Days(theCurentAuction.getMyDate())){
			throw new AddAuctionException("MaxPer7DaysException\n We have reached our maximum number of auction of this week.\n"
					+ "Please select a different day.");
		} else if (hasMaxPerDay(theCurentAuction.getMyDate())){
			throw new AddAuctionException("MaxPerDayException\nWe have reached our maxinum number of auction for this day.\n"
					+ "Please select another day.");
		} else if (!hasMinTimeBetweenAuctions(theCurentAuction.getMyDate(), 
				theCurentAuction.getMyStartTime(), theCurentAuction.getMyEndTime())){
			throw new AddAuctionException("MoreTimeBetweenAuctionsException\nYour chosen time is too close to the auction befor/after.\n"
					+ "Please select a different time.");
		} else if(theCurentAuction.getMyDate().getTime() < CURRENT_DATE.getTime()){
			throw new AddAuctionException("PassDateException\nCannot add auction to the pass, please check your date and try again.\n");
		}else{
			addAuctionToCurrentAuctions(theCurentAuction);
			addAuctionToOrgList(theCurentAuction);
			addAuctionToYearMonth(theCurentAuction);
			return true;
		}
	}

	public void editAuction(Auction auction) throws AddAuctionException{
		for(int i = 0; i < myCurrentAuctions.size(); i++){
			if(myCurrentAuctions.get(i).getName().equals(auction.getName())){	
				Auction temp = myCurrentAuctions.remove(i);
				removeAuctionFromYearMonth(temp);
				removeAuctionFromOrgList(temp);
				if(!addAuction( auction)){
					myCurrentAuctions.add(temp);
					addAuctionToOrgList(temp);
					addAuctionToYearMonth(temp);
				};

			}
		}
	}

	/**
	 * Takes a string that is in 12 hour time format with am or pm at the end, and
	 * returns an integer representation of its respected 24 hour time format.
	 * 
	 * @param theTime a string representation of 12 hour time format.
	 * @return an integer representing 24 hour time format.
	 *
	protected int getTimeInto24Hour(String theTime) {
		int time = Integer.parseInt(theTime.substring(0, theTime.length() - 2));
		if ("pm".equals(theTime.substring(theTime.length() - 2))) {
			time += 12;
		}

		return time;
	}
	 */

	/**
	 * Takes in a Date and spits out the month and year as a YearMonth obj.
	 * 
	 * @param theDate is the date that you want the year month of.
	 * @return a YearMonth object that has the year and month of the date that
	 * was passed in.
	 */
	public YearMonth parseYearMonth(Date theDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(theDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		YearMonth ym = YearMonth.of(year, month);
		return ym;
	}

	/**
	 * 
	 * @param theDate of the auction that you want to add.
	 * @return
	 */
	public int getDayFromDate(Date theDate) {
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
	public long getDaysBetween(Date theFirstDate, Date theSecondDate) {
		long toreturn = ((theFirstDate.getTime() - theSecondDate.getTime()) /( 24 * 60 * 60 * 1000));
		return toreturn;
	}

	/**
	 * Checks to see if there is already the max amount of auctions that are in
	 * the future.
	 * 
	 * @return true if there is at least the MAXCURRENTAUCTIONS.
	 */
	public boolean hasMaxAuctions() {
		return (myCurrentAuctions.size() >= MAX_CURRENT_AUCTIONS);
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
		return 	getDaysBetween(theDate, CURRENT_DATE) > MAX_DAYS_OUT; 
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
		boolean passed = true;
		int start, end;

		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> checkList = myAuctionListByYearMonth.get(ym);
			for (int i = 0; i < checkList.size(); i++) {
				if (getDaysBetween(checkList.get(i).getMyDate(), theDate) == 0) {
					start = checkList.get(i).getMyStartTime();
					end = checkList.get(i).getMyEndTime();
					if (start > theEndTime) {
						passed = start - theEndTime >= MIN_HOURS_BETWEEN_TWO_AUCTIONS;
					} else if (theStartTime > end) {
						passed =  theStartTime - end >= MIN_HOURS_BETWEEN_TWO_AUCTIONS;
					} else {
						passed = false;
					}

				}
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
		boolean passed = false;

		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> checkList = myAuctionListByYearMonth.get(ym);
			for (int i = 0; i < checkList.size(); i++) {
				if (getDaysBetween(checkList.get(i).getMyDate(), theDate) == 0) {
					days++;
				}
			}
			passed = (days >= MAX_AUCTIONS_PER_DAY);
		}

		return passed;
	}

	/**
	 *  
	 * @param theDate of the auction that you want to add.
	 * @return
	 */
	public boolean hasMaxPer7Days(Date theDate) {
		int weekCount = 0;
		int daysBetween = 0;
		boolean passed = false;

			ArrayList<Auction> checkList = myCurrentAuctions;
			for (int i = 0; i < checkList.size(); i++) {
				daysBetween = (int) getDaysBetween(checkList.get(i).getMyDate(), theDate);

				if (daysBetween >= MAX_DAYS_BEFORE_CHECK && daysBetween <= MAX_DAYS_AFTER_CHECK) {
					weekCount++;
				}
				
			}
		passed = (weekCount >= MAX_PER_7_DAYS);

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
			for (int i = 0; i < orgAuctions.size(); i++) {
				if(getDaysBetween(theDate, orgAuctions.get(i).getMyDate()) <= MIN_DAYS_BETWEEN_AUCTIONS){
					passDays = false;
				}
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
			sb.append(myCurrentAuctions.get(i).toString());
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
	 * Used to get the size of the current auction list.
	 * 
	 * @return an integer that is the size of the current auction list.
	 */
	public int getSizeOfCurrentAuction() {
		return myCurrentAuctions.size();
	}
	
	/**
	 * Used to get a specific auction from the list of current auctions using the
	 * correlated index from getListOfCurrentAuctions. 
	 * 
	 * @param theAuctionIndex an integer specifying the auction that you want 
	 * to see.
	 * @return an Auction that the user wants if the auction exists else null.
	 */
	public Auction getAuctionFromCurrentAuctions(int theAuctionIndex) {
		Auction temp = null;
		if (theAuctionIndex <= myCurrentAuctions.size()) {
			temp = myCurrentAuctions.get(theAuctionIndex - 1);
		}
		return temp;
	}
	
	/**
	 * Creates a string representation of the current month with the different
	 * auctions put in.
	 * 
	 * @param theYearMonth is the year and month that you want to view.
	 * @return a string representation of the month calendar.
	 * @throws ParseException 
	 */
	public String getAnyMonthCalendarView(YearMonth theYearMonth) throws ParseException {
		StringBuilder sb = new StringBuilder();

		//-------------------------------------------------------------------------------------
		//| Monday    | Tuesday   | Wednesday | Thursday  | Friday    | Saturday  | Sunday    |
		//-------------------------------------------------------------------------------------
		//|           |         1 |         2 |         3 |         4 |         5 |         6 |   
		//|           |           |1) Goodwill|           |           |           |           |   
		//|           |           |           |           |           |           |           |   
		//-------------------------------------------------------------------------------------
		String lineBreak = "----------------------------------------------------"
				+ "---------------------------------";
		String newLine = "\n";
		String space = " ";
		String lineSep = "|";
		int currDay = 0, length = 0, auctionCount = 1;
		ArrayList<Auction> currAuctionList = myAuctionListByYearMonth.get(theYearMonth);
		Date checkDate = new Date();
		// Calendar set-up
		length = theYearMonth.lengthOfMonth();
		currDay -= theYearMonth.atDay(1).getDayOfWeek().getValue();
		currDay++;
		sb.append(lineBreak);
		sb.append(newLine);
		sb.append(lineSep);

		for (int i = 0; i < 7; i++){
			sb.append(space);
			sb.append(String.format("%9s", myDays[i]));
			sb.append(space);
			sb.append(lineSep);
		}
		sb.append(newLine);

		for (int i = 0; i < 21; i++){
			if (i % 4 == 0) {
				sb.append(lineBreak);
				sb.append(newLine);
				if (currDay >= length) {
					i += 4;
				}
				if (i > 0) {
					currDay += 7;
				}
			} else {
				for (int j = 0; j < lineBreak.length(); j++) {
					if (j % 12 == 0) {
						sb.append(lineSep);
						if (j < lineBreak.length() - 1) {
							currDay++;
						}
					} else if (i % 4 == 1){
						sb.append(space);
						if (currDay > 0 && currDay <= length) {
							if (j % 12 == 9) {
								sb.append(String.format("%2d", currDay));
								j += 2;
							}
						}
					} else if (j % 12 == 1 && currAuctionList != null) {
						Calendar cal = new GregorianCalendar(theYearMonth.getYear(), 
								theYearMonth.getMonthValue() - 1, currDay);

						checkDate = cal.getTime();
						if (auctionCount <= currAuctionList.size() && currDay > 0) {
							int num = auctionCount - 3 + (i % 4);
							if (num == auctionCount) {
								num--;
							}
							
							if (getDaysBetween(checkDate, currAuctionList.get
									(num).getMyDate()) == 0) {
								sb.append(String.format("%2d. %7s", auctionCount, 
										currAuctionList.get(auctionCount - 1).getMyOrg()));
								auctionCount++;
								j += 10;
							} else {
								sb.append(space);
							}
						} else {
							sb.append(space);
						}
					} else {
						sb.append(space);
					}
				}
				//				out.println();	
				sb.append(newLine);
				currDay -= 7;
			}
		}
		return sb.toString();
	}

	/**
	 * Creates a string representation of the current month with the different
	 * auctions put in.
	 * 
	 * @return a string representation of the month calendar.
	 * @throws ParseException 
	 */
	public String getCurrentMonthCalendarView() throws ParseException {
		return getAnyMonthCalendarView(YearMonth.now());
	}

	/**
	 * Prints any months calendar to the console.
	 * @throws ParseException 
	 */
	public void printAnyMonthCalendar(YearMonth theYearMonth) throws ParseException {
		System.out.println(getAnyMonthCalendarView(theYearMonth));
	}

	/**
	 * Prints the current month calendar to the console.
	 * @throws ParseException 
	 */
	public void printCurrentMonthCalendar() throws ParseException {
		System.out.println(getCurrentMonthCalendarView());
	}


	/**
	 * Bubble sorts the list coming in and returns a list that is sorted by date.
	 * 
	 * @param toSort
	 * @return
	 */
	private  ArrayList<Auction> sortList(ArrayList<Auction> toSort) {
		ArrayList<Auction> sortedList = new ArrayList<Auction>();

			while(toSort.size()>0){
				Auction temp = toSort.get(0);
				int toRemove = 0;
				for (int j = 0; j < toSort.size(); j++) {
					if (getDaysBetween(toSort.get(j).getMyDate(), 
							temp.getMyDate()) < 0) {
						temp = toSort.get(j);
						toRemove = j;
					}
				}
				toSort.remove(toRemove);
				sortedList.add(temp);
			}
		return sortedList;

	}

	/**
	 * Adds the auction to the myAuctionListByYearMonth in its appropriate YearMonth.
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
		//sorts list before adding it back in
		
		myAuctionListByYearMonth.put(ym, sortList(newList));
	}

	private void removeAuctionFromYearMonth(Auction auction){
		YearMonth ym = parseYearMonth(auction.getMyDate());
		if (myAuctionListByYearMonth.containsKey(ym)) {
			ArrayList<Auction> newList = myAuctionListByYearMonth.get(ym);
			for(int i = 0; i < newList.size(); i++){
				if(newList.get(i).getName() == auction.getName()){
					newList.remove(i);
				}
			}
			myAuctionListByYearMonth.put(ym, newList);
		}
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
		//sorts list before putting it back in.
		
		myAuctionsByOrg.put(theCurentAuction.getMyOrg(), sortList(newList));
	}

	private void removeAuctionFromOrgList(Auction auction){
		if (myAuctionsByOrg.containsKey(auction.getMyOrg())) {
			ArrayList<Auction> newList = myAuctionsByOrg.get(auction.getMyOrg());
			for(int i = 0; i < newList.size(); i++){
				if(newList.get(i).getName() == auction.getName()){
					newList.remove(i);
				}
			}
			myAuctionsByOrg.put(auction.getMyOrg(), newList);
		}
	}

	/**
	 * Adds the current auction to the current auction list if the auctions date
	 * is after todays date.
	 * 
	 * @param theCurentAuction is the auction that we are currently trying to add.
	 */
	private void addAuctionToCurrentAuctions(Auction theCurentAuction) {
		if (theCurentAuction.getMyDate().getTime() > CURRENT_DATE.getTime()) {
			myCurrentAuctions.add(theCurentAuction);
			ArrayList<Auction> newList = sortList(myCurrentAuctions);
			myCurrentAuctions = newList;
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
