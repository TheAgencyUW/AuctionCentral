package AuctionCentral;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/** 
 * bidder.java
 * 
 * @author Tan Pham, Phillip Mishchuk
 * 
 */

public class User 
{
	/** A printstream for a shortcut to print out to the console.*/
	private static PrintStream myOut ;
	/** 
	 * A scanner that is used to scan in different files and read from 
	 * the console.
	 * */
	private static Scanner myIn;

	//To store the calendar which contents all the auction passing from main.
	private Calendar myCalendar;
	
	//Store current auction reflect to the calendar.
	private ArrayList<Auction> myCurrentAuctions;

	
	private String username;

	/**
	 * @return the bidderName
	 */
	public String getBidderName() {
		return username;
	}
	/**
	 * @param bidderName the bidderName to set
	 */
	public void setBidderName(String bidderName) {
		this.username = bidderName;
	}


	/**
	 * Constructor
	 */
	public User(Calendar calendar, String name){
		this.myCalendar = calendar;
		myCurrentAuctions = calendar.getMyCurrentAuctions();
		this.username = name;
		myOut = new PrintStream(System.out, true);
		myIn = new Scanner(System.in);
	}

	public void userInterface(){
		int input;
		while(true){
			myOut.println("Welcome " + username);
			myOut.println("Please enter a command:\n");
			myOut.println("0:log out\n");
			myOut.println("1: task 1\n");
			myOut.println("2: task 2\n");
			input = myIn.nextInt();
			if(input == 0){	//break the while loop, end bidderInterface(), return control back to main class.
				break;
			}else if(input == 1){
				task1();
			}else if(input == 2){
				task2();
			}else{
				myOut.println("Invalid input");
			}
		}

	}
	public void task1(){
		//to be override
	}
	public void task2(){
		//to be override
	}
}