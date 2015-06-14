/**
 * 
 */
package net.aws.exception;

/**
 * @author Sebastian Luca
 * This exception is meant to handle the case for 
 * Missing Statement from the Prepared Statement
 * created for sending SQL commands to the Oracle
 * database.
 */
public class MissingStatementException extends Exception {
	
	private static final String msg = 
					"The Prepared Statement variable was not " + 
					"changed whithin the child class, therefore the" + 
					"variable is null.\nBefore executing the constructor, " + 
					"call super.setPreparedStatement(PreparedStatement)\n";

	public MissingStatementException() {
		super(msg);
	}
	
	public MissingStatementException(String message) {
		super(msg + message);
	}
	
	public MissingStatementException(String message, Throwable cause) {
		super(msg + message,cause);
	}
	
	public MissingStatementException(Throwable cause) {
		super(msg, cause);
	}
	
	private static final long serialVersionUID = 4326492066637027089L;

}
