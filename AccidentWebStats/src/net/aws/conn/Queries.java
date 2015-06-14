/**
 * 
 */
package net.aws.conn;

import java.io.IOException;

import net.aws.properties.GetProperyValues;

/**
 * @author Sebastian Luca
 *
 */
public class Queries {
	
	private static final String propFileName = "query.properties";

	/**
	 * @param  queryType	The key describing the needed query from the property file
	 * @return				Returns a String representing the requested query from the property file
	 * @throws IOException	If the requested query property file could not be found.
	 */
	public static String getQuery(String queryType) throws IOException {
		return new GetProperyValues().getPropValue(propFileName).getProperty(queryType);
	}
}
