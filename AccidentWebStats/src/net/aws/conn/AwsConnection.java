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
	
	/**
	 * This method is used to increase the times an accident has been recorded
	 * at the same coordinates. It accesses a SQL UPDATE query which increments by
	 * one the current hitCount for specified id.
	 * @param  loc_id is an integer representing the id of the location that needs incrementing
	 * @return 1 if update was a success and 0 otherwise
	 * @throws IOException when accessing query properties file
	 * @throws SQLException when executing operations using Prepared Statement object
	 */
	public int incrementHitCount( int loc_id ) throws IOException, SQLException {
		String query_key = "incr_hits";
		String query = Queries.getQuery(query_key);
		
		setPreparedStatement(query);
		preStatement.setInt(1, loc_id);
		preStatement.setInt(2, loc_id);
		
		return preStatement.executeUpdate();
	}
	
	/**
	 * This method queries the database for entries that are 100 meters from specified position.
	 * If an entry is found, the ID is returned. If not, -1 is returned
	 * @param latitude represents first Coordinate of current position
	 * @param longitude represents second Coordinate of current position
	 * @return and integer representing the ID of the found entry. If no entry is found, -1 is returned
	 * @throws IOException when accessing query properties file
	 * @throws SQLException when executing operations using Prepared Statement object
	 */
	public int getHitCount(String latitude, String longitude) throws IOException, SQLException {
		String query_key = "get_hits";
		
		String query = Queries.getQuery(query_key);
		
		setPreparedStatement(query);
		preStatement.setDouble(1, Double.parseDouble(latitude));
		preStatement.setDouble(2, Double.parseDouble(longitude));
		
		result = preStatement.executeQuery();
		if( result.next() ) {
			return result.getInt(1);
		}		
		
		return -1;
	}
	
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
	public int insertCoordinates(String latitude, String longitude) throws IOException, SQLException {
		String queryKey = "insert_coord";

		String query = Queries.getQuery(queryKey);
		setPreparedStatement(query);
		
		System.out.println(query);
		
		preStatement.setDouble(1, Double.parseDouble(latitude));
		preStatement.setDouble(2, Double.parseDouble(longitude));		
		System.out.println(latitude + " & " + longitude);
		return preStatement.executeUpdate();
	}


	/**
	 * Closes opened connections: PooledConnection, 
	 * Connection and ResultSet
	 * @throws SQLException
	 */
	public void closeConn() throws SQLException {
		if(result != null) result.close();
		conn.close();
		pconn.close();		
	}
	
	public void testConn() throws SQLException {
		preStatement = conn.prepareStatement("INSERT INTO AWS_COORDINATES(COORD,TIMESTAMP,HITS)" +
				"VALUES( SDO_GEOMETRY(2001, 8307, SDO_POINT_TYPE (?, ?, NULL), NULL, NULL), SYSDATE, 1)");
		
		preStatement.setDouble(1, Double.parseDouble("45.805091399999995"));
		preStatement.setDouble(2, Double.parseDouble("45.805091399999995"));
		preStatement.executeUpdate();

		//return result;		
	}
}


















