package net.aws.gps;

import java.io.IOException;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import net.aws.conn.AwsConnection;
import net.aws.exception.MissingStatementException;

@ManagedBean
public class Coordinates {
	
	private String longitude;
	private String latitude;
	private String msg = "No message";
	AwsConnection awsConn = null;
	
	public void getCoord() {
		latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
		longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");
		
		/*FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Coordinates added", "Lat:" + latitude + " , long: " + longitude));
		*/
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

		System.out.println(latitude +" " + longitude);
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Response", msg));
	}
}






