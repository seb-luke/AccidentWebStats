/**
 * 
 */
package net.aws.conn;

import java.io.IOException;
import java.sql.SQLException;

import net.aws.properties.GetProperyValues;

/**
 * @author Sebastian Luca
 *
 */
public class Queries {
	
	private static final String propFileName = "query.properties";

	public static String getQuery(String queryType) throws SQLException, IOException {
		return new GetProperyValues().getPropValue(propFileName).getProperty(queryType);
	}
}
