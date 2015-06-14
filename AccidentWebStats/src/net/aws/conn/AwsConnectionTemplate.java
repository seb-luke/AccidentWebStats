/**
 * 
 */
package net.aws.conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import net.aws.exception.MissingStatementException;
import net.aws.exception.PreparedSQLException;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 * @author Sebastian Luca
 *
 */
public abstract class AwsConnectionTemplate {
	
	private static final String url = "jdbc:oracle:thin@localhost:1521:starterOracl";
	private static final String user = "AWS";
	private static final String pwd = "qwerty";
	
	private PreparedStatement preStatement = null;
	private ResultSet result = null;
	private PooledConnection pconn = null; 
	private Connection conn = null;
	
	
	/**
	 * This constructor needs to be called from the child classes after
	 * calling the setPreparedStatement method, otherwise the system will
	 * crash. 
	 * This constructor will firstly create a connection using the credentials
	 * that were set above, after which it will execute the prepared statement 
	 */
	public AwsConnectionTemplate() {
		
		//Connect to the Oracle database using the Driver
		try {
			connectToOracle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Execute the prepared statement
		try {
			executeStatement();
		} catch (MissingStatementException e) {
			e.getMessage();
		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				pconn.close();
				conn.close();
			} catch (SQLException e) {
				e.getMessage();
			}
			
		}
	}


	/**
	 * @throws SQLException
	 */
	private void connectToOracle() throws SQLException {
		OracleConnectionPoolDataSource ocpds = new OracleConnectionPoolDataSource();
		ocpds.setURL(url);
		ocpds.setUser(user);
		ocpds.setPassword(pwd);
		
		pconn = ocpds.getPooledConnection();
		
		conn = pconn.getConnection();
	}
	
	protected void setPreparedStatement(PreparedStatement preStatement) {
		this.preStatement = preStatement;
	}
	
	private void executeStatement() throws MissingStatementException, PreparedSQLException {
		
		if(preStatement == null) throw new MissingStatementException();
		
		try {
			result = preStatement.executeQuery();
		} catch (SQLException e) {
			throw new PreparedSQLException();
		}
	}
	
	public ResultSet getResult() {
		return result;
	}
}


















