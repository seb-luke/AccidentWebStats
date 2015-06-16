package net.aws.gps;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import net.aws.conn.AwsConnection;

@ManagedBean
public class Coordinates {
	
	private String msg = "No message";
	AwsConnection awsConn = null;
	
	public void getCoord() {
		String latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
		String longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");

		// Reduce the number to 4 digits because devices usually are precise
		// only to that particular decimal => 11m offset
		DecimalFormat coordFormater = new DecimalFormat("###.####");
		latitude = coordFormater.format(Double.parseDouble(latitude));
		longitude = coordFormater.format(Double.parseDouble(longitude));
		
		int hitID;
		if( (hitID = coordinatesExist(latitude,longitude)) != -1) {
			increaseHitCount(hitID);				
		} else {
			addLocationToDB(latitude,longitude);
		}

		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Response", msg));
	}

	private int coordinatesExist(String latitude, String longitude) {
		awsConn = new AwsConnection();
		int loc_id = -1;
		try {
			loc_id = awsConn.getHitCount(latitude, longitude);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loc_id;
	}

	private void increaseHitCount(int loc_id) {
		awsConn = new AwsConnection();
		try {
			if( awsConn.incrementHitCount(loc_id) != 0)
				msg = "Coordinates existing. Hit count increased";
			else
				msg = "Coordinates existing. Hit count could not be increased!";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addLocationToDB(String latitude, String longitude) {
		int result;
		
		awsConn = new AwsConnection();
		try {
			result = awsConn.insertCoordinates(latitude, longitude);
			if(result == 1)
				msg = "Coordinates inserted";
			else
				msg = "Coordinates failed to insert";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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






