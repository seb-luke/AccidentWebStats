/**
 * 
 */
package net.aws.exception;

import java.sql.SQLException;

/**
 * @author Sebastian Luca
 *
 */
public class PreparedSQLException extends SQLException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 813144281567777612L;
	private static final String msg = 
			"Error executing the SQL Prepared Statement";
	/**
	 * 
	 */
	public PreparedSQLException() {
		super(msg);
	}

	/**
	 * @param reason
	 */
	public PreparedSQLException(String reason) {
		super(msg + reason);
	}

	/**
	 * @param cause
	 */
	public PreparedSQLException(Throwable cause) {
		super(msg, cause);
	}

	/**
	 * @param reason
	 * @param SQLState
	 */
	public PreparedSQLException(String reason, String SQLState) {
		super(msg + reason, SQLState);
	}

	/**
	 * @param reason
	 * @param cause
	 */
	public PreparedSQLException(String reason, Throwable cause) {
		super(msg + reason, cause);
	}

	/**
	 * @param reason
	 * @param SQLState
	 * @param vendorCode
	 */
	public PreparedSQLException(String reason, String SQLState, int vendorCode) {
		super(msg + reason, SQLState, vendorCode);
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param cause
	 */
	public PreparedSQLException(String reason, String sqlState, Throwable cause) {
		super(msg + reason, sqlState, cause);
	}

	/**
	 * @param reason
	 * @param sqlState
	 * @param vendorCode
	 * @param cause
	 */
	public PreparedSQLException(String reason, String sqlState, int vendorCode,
			Throwable cause) {
		super(msg + reason, sqlState, vendorCode, cause);
	}

}
