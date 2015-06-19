/**
 * 
 */
package net.aws.forms;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.aws.conn.AwsConnection;

/**
 * @author Sebastian Luca
 *
 */
@ManagedBean(name="loginBean")
@RequestScoped
public class LoginBean extends FormsAbstractBean{

	private String username;
	private String password;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String logIn() {

		boolean loginCorrect = false;
		AwsConnection awsConn = new AwsConnection();
		ResourceBundle msg = ResourceBundle.getBundle("net.aws.lang.awsLang");
		
		try {
			if(!awsConn.usernameExists(username)) {
				addMessage("loginForm:username", new FacesMessage(FacesMessage.SEVERITY_WARN, 
						msg.getString("login.username.wrong"), null));
				return "failure";
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			loginCorrect = awsConn.checkPassword(username, password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("SQL Exception in LoginBean.logIn(). Check "
					+ "password query did not run correctly. " + e.getMessage());
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password "
					+ "hash select query could not be run", null));
			return "failure";
		}
		
		awsConn.closeConn();
		
		if(!loginCorrect) {
			addMessage("loginForm:password", new FacesMessage(FacesMessage.SEVERITY_WARN, 
					msg.getString("login.password.wrong"), null));
			return "failure";
		}
		
		//TODO set session cookie
		return "success";
	}
	
	

}














