package AuctionCentral;

public class MaxPerDayException extends Exception {

	public MaxPerDayException() {
		super();
	}

	public MaxPerDayException(String message) {
		super(message);
	}

	public MaxPerDayException(Throwable cause) {
		super(cause);
	}

	public MaxPerDayException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaxPerDayException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
