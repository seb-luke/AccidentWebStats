package net.aws.gps;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import net.aws.conn.AwsConnection;
import net.aws.exception.MissingStatementException;

@ManagedBean
public class Coordinates {
	
	private String msg = "No message";
	AwsConnection awsConn = null;
	
	public void getCoord() {
		String latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
		String longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

		// Reduce the number to 4 digits because devices usually are precise
		// only to that particular decimal
		System.out.println("Latitude before: " + latitude);
		DecimalFormat coordFormater = new DecimalFormat("###.####");
		latitude = coordFormater.format(Double.parseDouble(latitude));
		longitude = coordFormater.format(Double.parseDouble(longitude));
		
		if( coordinatesExist(latitude,longitude) ) {
			increaseHitCount(latitude,longitude);
		} else {
			addLocationToDB(latitude,longitude);
		}

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Response", msg));
	}

	private boolean coordinatesExist(String latitude, String longitude) {
		// TODO Auto-generated method stub
		return false;
	}

	private void increaseHitCount(String latitude, String longitude) {
		// TODO Auto-generated method stub
		
	}

	private void addLocationToDB(String latitude, String longitude) {
		int result;
		
		awsConn = new AwsConnection();
		try {
			result = awsConn.insertCoordinates(latitude, longitude);
			if(result == 1)
				msg = "Coordinates inserted";
			else
				msg = "Coordinates failed to inser";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingStatementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				awsConn.closeConn();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
}






