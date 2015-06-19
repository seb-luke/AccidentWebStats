/**
 * 
 */
package net.aws.beans;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.aws.conn.AwsConnection;
import net.crackstation.security.AwsPasswordHash;

/**
 * This bean will manage the user registration form
 * @author Sebastian Luca
 *
 */

@ManagedBean(name="formBean")
@SessionScoped
public class UserRegistrationBean extends FormsAbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4100205223392474929L;
	private String name;
	private String surname;
	private String email;
	private String username;
	private String password;
//	private String salt;
	private String passwordConfirm;
	private String userRole;
	private Date birthdate;
//	private String birthdate;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
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
	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	/**
	 * @param passwordConfirm the passwordConfirm to set
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @return the birthday
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	/**
	 * @param birthdate the birthday to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	
	/**
	 * The method check first if the input inserted in the form is valid
	 * according to the system requirements. If not, the validateInput method will
	 * create new FacesMessages that will appear on the JSF page and this method will
	 * return "failure".
	 * Secondly, this method tries to insert the input into the database and will return
	 * <code>success</code> if it is correctly inserted. 
	 * TODO ??Because this method uses password hash and SQL queries, exceptions need to be handled 
	 * @return <code>success</code> if form is validated and users inserted into
	 * the database and <code>failure</code> otherwise.
	 */
	public String addUser() {
		if( !validateInput() ) {
			return "failure";
		}
		if( !insertUserIntoDatabase() ) {
			return "failure";
		}
		return "success";			
	}
	
	/**
	 * This method will insert the form input into the users database after
	 * making all required checks.
	 * If it is a success, it will return true. If not, it will return false.
	 * @return <code>true</code> if the new user is inserted correctly
	 * and <code>false</code> if user insertion has failed.
	 */
	private boolean insertUserIntoDatabase() {
		String[] saltAndPassword = null;
		try {
			saltAndPassword = AwsPasswordHash.createHash(password);
		} catch (Exception e1) {
			System.out.println("\n\t\t\tAICI");
			e1.getMessage();
			e1.printStackTrace();
		}
		String salt;
		String hashedPasswd;
		
		if( saltAndPassword != null ) {
			salt = saltAndPassword[0];
			hashedPasswd = saltAndPassword[1];
		}
		else {
			System.out.println("\n\t\tpassword cannot be salted and hashed");
			return false;
		}
		
		AwsConnection awsConn = new AwsConnection();
		int queryResult = 0;
		//TODO insert selected security question
		try {
			queryResult = awsConn.insertUser(name, surname, username, email, hashedPasswd, salt, userRole, 
					new java.sql.Date(birthdate.getTime()), 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			awsConn.closeConn();
		}
		
		if( queryResult == 0 ) {
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Couold not insert user into database", null));
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method will validate if the inserted input in User Registration is valid.
	 * It will check if the inserted confirmation password equals the password field
	 * and if not, it will create a new FacesMessage that outputs that passwords do not match;
	 * it will check if the username does not exist in the database and if it does it will
	 * create a new FacesMessage that outputs that username does exist in the database; 
	 * it will check if the email does not exist in the database and if does, it will
	 * create a new FacesMessage that outputs that email already exists in the database
	 * @return <code>false</code> if inserted passwords do not match or if 
	 * username or email exist in the database and returns <code>true</code> otherwise. 
	 */
	private boolean validateInput() {
		boolean formIsValid = true;
		AwsConnection awsConn = new AwsConnection();
		
		if( !password.equals(passwordConfirm) ) {
			formIsValid = false;
			addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", null));
		}
			
		try {
			if(awsConn.usernameExists(username)) {
				formIsValid = false;
				addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username exists. Try a new one.", null));
			}
			if( awsConn.emailExists(email) ) {
				formIsValid = false;
				addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email exists. Try a new one or try password recovery", null));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			awsConn.closeConn();
		}
		
		return formIsValid;
	}
	
}


























