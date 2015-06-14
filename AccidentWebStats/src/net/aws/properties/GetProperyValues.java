/**
 * 
 */
package net.aws.properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sebastian Luca
 *
 */
public class GetProperyValues {

	public Properties getPropValue(String propFileName) throws IOException {
		Properties prop = new Properties();
		
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		
		if( inStream != null ) {
			prop.load(inStream);
		} else {
			throw new FileNotFoundException("Property File " + propFileName + " not found in classpath");
		}
		
		return prop;
	}

}
