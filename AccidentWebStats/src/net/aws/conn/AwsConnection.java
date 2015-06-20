/**
 * 
 */
package net.aws.conn;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import javax.sql.PooledConnection;

import net.crackstation.security.AwsPasswordHash;
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
	 * Method checks if requested username exists in the database. If that is true, 
	 * the first row will be found and next() method will return true
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean usernameExists(String username) throws IOException, SQLException {
		String queryKey = "check_uname";
		
		String query = Queries.getQuery(queryKey);
		setPreparedStatement(query);
		
		preStatement.setString(1, username);
		result = preStatement.executeQuery();

		boolean b = result.next();
		
		return b;
		
		
	}


	/**
	 * @param email
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean emailExists(String email) throws IOException, SQLException {
		String queryKey = "check_email";
		
		String query = Queries.getQuery(queryKey);
		setPreparedStatement(query);
		
		preStatement.setString(1, email);
		result = preStatement.executeQuery();

		boolean b = result.next();
		return b;
	}
	
	/**
	 * This method adds a new user to the database by running the query saved
	 * in the properties file. The return is an <code>int</code> that shows the affected number of rows
	 * @param name the user's name
	 * @param surname the user's surname
	 * @param username the new username
	 * @param email the user's Email address
	 * @param password the 256 bit hashed password
	 * @param salt the 256 bit generated security key
	 * @param permissions the user permission ('adm' for admin / 'usr' for user)
	 * @param birthdate the date of birth as a <i>String</i>
	 * @param securityQuestionID the ID for the selected security question
	 * taken from the security questions table in the database
	 * @return <code>1</code> if row was inserted or <code>0</code> if insert had an error.
	 * See {@link java.sql.PreparedStatement#executeUpdate()}
	 * @throws IOException if method could not read from Query properties file
	 * @throws SQLException if the run query did not complete. 
	 */
	public int insertUser(
			String name, String surname, String username, String email,
			String password, String salt, String permissions, Date birthdate, int securityQuestionID) 
					throws IOException, SQLException {
		
		String queryKey = "insert_user";
		
		String query = Queries.getQuery(queryKey);
		setPreparedStatement(query);
		
		preStatement.setString(1, email);
		preStatement.setString(2, name);
		preStatement.setString(3, surname);
		preStatement.setString(4, username);
		preStatement.setString(5, password);
		preStatement.setString(6, salt);
		preStatement.setString(7, permissions);
		preStatement.setDate(8, birthdate);
		preStatement.setInt(9, securityQuestionID);
		
		/*preStatement.setString(1, "mail");
		preStatement.setString(2, "nume");
		preStatement.setString(3, "prenume");
		preStatement.setString(4, "uname");
		preStatement.setString(5, "parola");
		preStatement.setString(6, "salt");
		preStatement.setString(7, "adm");
		preStatement.setString(8, "01-01-2001");
		preStatement.setInt(9, 1);*/
		
		return preStatement.executeUpdate();
	}
	
	public boolean checkPassword(String username, String password) 
			throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		String salt;
		String hashedPassword;
		String query_key = "get_pwd_salt";
		
		String query = Queries.getQuery(query_key);
		setPreparedStatement(query);
		
		preStatement.setString(1, username);
		result = preStatement.executeQuery();
		
		if(!result.next()) {
			throw new SQLException("ResultSet came back empty");
		}
		
		salt = result.getString("salt");
		hashedPassword = result.getString("passwd");
		
		boolean b = AwsPasswordHash.validatePassword(password, salt, hashedPassword);
		
		return b;
	}
	
	
	public String getUsrRole(String username) throws IOException, SQLException {
		String usrRole = "usr";
		String query_key = "get_usr_role";
		
		String query = Queries.getQuery(query_key);
		setPreparedStatement(query);
		
		preStatement.setString(1, username);
		result = preStatement.executeQuery();
		
		if(!result.next()) {
			throw new SQLException("ResultSet came back empty after querying after the user role");
		}
		
		usrRole = result.getString("permissions");
		return usrRole;
	}


	/**
	 * Closes opened connections: PooledConnection, Connection and ResultSet (if not null)
	 * Handles SQL Exception cause by closing connections and results
	 */
	public void closeConn() {
		
		try {
			if(result != null) result.close();
			conn.close();
			pconn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}






}


















