/**
 * 
 */
package net.aws.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Sebastian Luca
 *
 */
public class FormsAbstractBean {
	
	/**
	 * Adds a message to the Faces Context (XHTML page)
	 * @param clientID a String that gives the ID for the targeted input id. 
	 * If <code>null</code>, message will be sent globally. Usage: 
	 * <code>"formId:inputId"</code>
	 * @param message a FacesMessage containing the severity and the required message
	 */
	protected void addMessage(String clientID, FacesMessage message){
        FacesContext.getCurrentInstance().addMessage(clientID, message);
    }
}
