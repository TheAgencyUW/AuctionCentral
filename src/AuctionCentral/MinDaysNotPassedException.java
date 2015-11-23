package AuctionCentral;

public class MinDaysNotPassedException extends Exception {

	public MinDaysNotPassedException() {
		super();
	}

	public MinDaysNotPassedException(String message) {
		super(message);
	}

	public MinDaysNotPassedException(Throwable cause) {
		super(cause);
	}

	public MinDaysNotPassedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MinDaysNotPassedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
