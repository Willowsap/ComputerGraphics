/**
 * 
 */
package ray.surface;

import ray.math.Point3;
import ray.math.Vector3;

/**
 * Represents a plane as a point on the plane and the normal to the plane.
 * 
 * @version 1.0
 * @author parryrm
 *
 */
public class Plane extends Surface {

	/** A point on the plane. */
	protected final Point3 point = new Point3();
	
	/** The normal vector. */
	protected final Vector3 normal = new Vector3();
	
	public double intersect(Point3 e, Vector3 d)
	{
		Vector3 cameraToSurf = new Vector3();
		cameraToSurf.sub(this.point, e);
		return this.normal.dot(cameraToSurf) / this.normal.dot(d);
	}
	
	/**
	 * Setter method for the plane's point.
	 * @param point the new point.
	 */
	public void setPoint(Point3 point)
	{
		this.point.set(point);
	}
	
	/**
	 * Getter method for the plane's point.
	 * @return the plane's point.
	 */
	public Point3 getPoint() 
	{
		return this.point;
	}

	/**
	 * Setter method for the plane's normal.
	 * @param normal the new normal.
	 */
	public void setNormal(Vector3 normal)
	{
		this.normal.set(normal);
	}
	
	/**
	 * Getter method for the plane's normal.
	 * @return the plane's normal.
	 */
	public Vector3 getNormal(Point3 p)
	{
		return this.normal;
	}
	
	/**
	 * Constructor for the plane.
	 * Currently does nothing.
	 */
	public Plane()
	{
		
	}

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "plane " + point + " " + normal + " " + shader + " end";
	}

}
