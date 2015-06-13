package net.aws.gps;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@ManagedBean
public class Coordinates {
	
	private String longitude;
	private String latitude;
	
	public void getCoord() {
		latitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("latitude");
		longitude = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("longitude");
		
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Coordinates added", "Lat:" + latitude + " , long: " + longitude));
	}
}
