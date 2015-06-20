package net.aws.dao;

public class DAOConfigurationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789786696482724530L;

	public DAOConfigurationException(String message) {
		super(message);
	}

	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}

	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOConfigurationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
