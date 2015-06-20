/**
 * 
 */
package net.aws.dao;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Sebastian Luca
 *
 */
public class CoordinateDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5172707681577963781L;
	private int loc_id;
	private double latitude;
	private double longitude;
	private Timestamp timestamp;
	private double hits;

	/**
	 * 
	 */
	public CoordinateDAO(int loc_id, double latitude, double longitude,
			Timestamp timestamp, double hits) {
		this.loc_id = loc_id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.hits = hits;
	}
	
	public int getLocId() {
		return this.loc_id;
	}
	
	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}
	
	public Timestamp getTimestamp() {
		return this.timestamp;
	}
	
	public double getHits() {
		return this.hits;
	}
	
	

    /**
     * The user ID is unique for each User. So this should compare User by ID only.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        return (other instanceof CoordinateDAO) &&
        	   (loc_id == ((CoordinateDAO) other).getLocId());
    }

    /**
     * The user ID is unique for each User. So User with same ID should return same hashcode.
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.getClass().hashCode() + loc_id;
    }
}














