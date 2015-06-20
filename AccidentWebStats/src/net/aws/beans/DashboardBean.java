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
		}
		
		return result;
	}
	

}
