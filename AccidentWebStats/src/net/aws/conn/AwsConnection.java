/**
 * 
 */
package net.aws.conn;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import net.aws.exception.MissingStatementException;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 * The AwsConnectionTemplate class will take care of connecting to the database 
 * using pooled connection and executing a SQL statement via Prepared Statement 
 * to avoid SQL Injection. 
 * It is created as an abstract class that can be extended for different SQL queries.
 * It is important to set the PreparedStatement variable when extending the
 * abstract method setPreparedStatement
 * @author Sebastian Luca
 */
public class AwsConnection {
	
	private static final String url = "jdbc:oracle:thin:@localhost:1521:starterOracl";
	private static final String user = "AWS";
	private static final String pwd = "qwerty";
	
	protected PreparedStatement preStatement = null;
	private   ResultSet result = null;
	private   PooledConnection pconn = null; 
	private   Connection conn = null;
	
	
	/**
	 * This constructor needs to be called from the child classes after
	 * calling the setPreparedStatement method, otherwise the system will
	 * crash. 
	 * This constructor will firstly create a connection using the credentials
	 * that were set above, after which it will execute the prepared statement 
	 */
	public AwsConnection() {
		
		//Connect to the Oracle database using the Driver
		try {
			connectToOracle();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @throws SQLException
	 * This method connects to the Oracle Database
	 * using the static final credentials set in the class.
	 * This way it will generate the connection object needed
	 * to query the database
	 */
	private void connectToOracle() throws SQLException {
		OracleConnectionPoolDataSource ocpds = new OracleConnectionPoolDataSource();
		ocpds.setURL(url);
		ocpds.setUser(user);
		ocpds.setPassword(pwd);
		
		pconn = ocpds.getPooledConnection();
		
		conn = pconn.getConnection();
	}
	
	/**
	 * @param  query A string containing the SQL Query that needs to be executed
	 * @throws SQLException if provided query string was not built correctly		 
	 * This method creates the prepare statement using the received query
	 */
	private void setPreparedStatement(String query) throws SQLException {
		preStatement = conn.prepareStatement(query);
	}
	
/*	private void executeStatement() throws MissingStatementException, PreparedSQLException {
		
		if(preStatement == null) throw new MissingStatementException();
		
		try {
			result = preStatement.executeQuery();
		} catch (SQLException e) {
			throw new PreparedSQLException();
		}
	}*/
	
	/**
	 * Takes the latitude and longitude as parameters and inserts them into the Database.
	 * It takes the query from the query properties file, adds the coordinates as 
	 * Strings in the prepared statement object and executes the statement. 
	 * @param latitude		the first GPS coordinate
	 * @param longitude		the second GPS coordinate
	 * @return				the result as a ResultSet. It needs to be then parsed
	 * @throws IOException  in case the query property file is not found
	 * @throws SQLException in case the builded query has errors
	 * @throws MissingStatementException in case the statement execution fails
	 */
	public int insertCoordinates(String latitude, String longitude) throws IOException, SQLException, MissingStatementException {
		String queryKey = "insert_coord";

		String query = Queries.getQuery(queryKey);
		setPreparedStatement(query);
		
		System.out.println(query);
		
		preStatement.setDouble(1, Double.parseDouble(latitude));
		preStatement.setDouble(2, Double.parseDouble(longitude));		
		
		return preStatement.executeUpdate();
	}


	public void closeConn() throws SQLException {
		if(result != null) result.close();
		conn.close();
		pconn.close();		
	}
	
	public int testConn() throws SQLException {
		preStatement = conn.prepareStatement("INSERT INTO AWS_COORDINATES(COORD,TIMESTAMP,HITS)" +
				"VALUES( SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE (?, ?, NULL), NULL, NULL), SYSDATE, 1)");
		
		preStatement.setDouble(1, Double.parseDouble("45.805091399999995"));
		preStatement.setDouble(2, Double.parseDouble("45.805091399999995"));
		return preStatement.executeUpdate();

		//return result;		
	}
}


















