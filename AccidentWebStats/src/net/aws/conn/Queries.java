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
	 * @param  queryType	key corresponding to the requested query
	 * @return				a String containing requested query
	 * @throws IOException	the requested query property file could not be found.
	 * This method is used to take the query from the properties file.
	 */
	public static String getQuery(String queryType) throws IOException {
		return new GetProperyValues().getPropValue(propFileName).getProperty(queryType);
	}
}
