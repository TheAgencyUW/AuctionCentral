package AuctionCentral;

public class MoreTimeBetweenAuctionsException extends Exception {

	public MoreTimeBetweenAuctionsException() {
		super();
	}

	public MoreTimeBetweenAuctionsException(String message) {
		super(message);
	}

	public MoreTimeBetweenAuctionsException(Throwable cause) {
		super(cause);
	}

	public MoreTimeBetweenAuctionsException(String message, Throwable cause) {
		super(message, cause);
	}

	public MoreTimeBetweenAuctionsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
