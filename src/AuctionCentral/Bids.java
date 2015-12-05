package AuctionCentral;

public class Bids{
	private String bidder;
	private double bidValue;

	Bids(String bidder, double bidValue){
		this.bidder = bidder;
		this.bidValue = bidValue;
	}
	/**
	 * @return the bidder
	 */
	public String getBidder() {
		return bidder;
	}
	/**
	 * @param bidder the bidder to set
	 */
	public void setBidder(String bidder) {
		this.bidder = bidder;
	}
	/**
	 * @return the bidValue
	 */
	public double getBidValue() {
		return bidValue;
	}
	/**
	 * @param bidValue the bidValue to set
	 */
	public void setBidValue(double bidValue) {
		this.bidValue = bidValue;
	}
}
