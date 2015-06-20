/**
 * 
 */
package net.aws.beans;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.jsp.jstl.sql.Result;

import net.aws.conn.AwsConnection;

/**
 * @author Sebastian Luca
 *
 */
@ManagedBean(name="dashBean")
@RequestScoped
public class DashboardBean extends FormsAbstractBean {

	//private List<CoordinateDAO> coordinates = new ArrayList<CoordinateDAO>();
	private int noOfHits = 5;
	private double latitude;
	private double longitude;
	private int results = 5;
	private Result searchResults = null; 
	
	public Result getTopHits() {
		Result r = populateTopHits();
		return r;
	}
	
	private Result populateTopHits() {
		AwsConnection awsConn = new AwsConnection();
		Result result = null;
		try {
			result = awsConn.getTopHits(noOfHits);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			awsConn.closeConn();
		}
		
		return result;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the results
	 */
	public int getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(int results) {
		this.results = results;
	}

	/**
	 * @return the searchResults
	 */
	public Result getSearchResults() {
		return searchResults;
	}
	
	public String search() {
		AwsConnection awsConn = new AwsConnection();
		
		try {
			searchResults = awsConn.getNearby(latitude,longitude,results);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			awsConn.closeConn();
		}
		
		return "dashboard";
	}

}









