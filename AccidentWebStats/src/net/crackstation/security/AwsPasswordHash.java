package net.crackstation.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Sebastian Luca
 *
 */
public class AwsPasswordHash {


    /**
     * This method calls {@link PasswordHash#createHash(String)}. It then
     * splits the returned String into salt and hashed password and then
     * returns a String array containing those two
     * @param password the plain text password that needs hashing
     * @return a <code>String Array</code> containing <b>the salt</b> on 
     * <code>String[0]</code> and the <b>hashed password</b> on
     * <code>String[1]</code>
     */
    public static String[] createHash(String password) {
    	String passStructure = null;
    	String[] result;
    	try {
			 passStructure= PasswordHash.createHash(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if( passStructure != null ) {
    		result = passStructure.split(":");
    		return new String[] {result[1], result[2]}; 
    	}
    	//TODO create a new exception and throw it instead of returning null
    	return null;
    }
    
    
    public static boolean validatePassword(String password, String salt, String correctHash) 
    		throws NoSuchAlgorithmException, InvalidKeySpecException {
    	return PasswordHash.validatePassword(password, PasswordHash.PBKDF2_ALGORITHM + ";" + salt + ";" + correctHash);
    }
}














