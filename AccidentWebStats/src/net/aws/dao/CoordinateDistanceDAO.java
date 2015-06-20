/**
 * 
 */
package net.aws.dao;

import java.sql.Timestamp;

/**
 * This class extends the functionality of the {@link CoordinateDAO} class
 * by having an extra field containing the distance between requested location
 * and returned locations
 * @author Sebastian Luca
 *
 */
public class CoordinateDistanceDAO extends CoordinateDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2762006395327954662L;
	private double location;

	public CoordinateDistanceDAO(int loc_id, double latitude, double longitude,
			Timestamp timestamp, double hits, double location) {
		super(loc_id, latitude, longitude, timestamp, hits);
		this.location = location;
	}
	
	public double getLocation() {
		return this.location;
	}


}















