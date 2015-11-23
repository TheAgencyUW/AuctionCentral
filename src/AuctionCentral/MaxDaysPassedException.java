package AuctionCentral;

public class MaxDaysPassedException extends Exception {

	public MaxDaysPassedException() {
		super();
	}

	public MaxDaysPassedException(String arg0) {
		super(arg0);
	}

	public MaxDaysPassedException(Throwable cause) {
		super(cause);
	}

	public MaxDaysPassedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MaxDaysPassedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
