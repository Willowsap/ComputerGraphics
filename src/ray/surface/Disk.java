      /**
 * 
 */
package ray.surface;

import ray.math.Point3;
import ray.math.Vector3;

/**
 * Representation of a 2D disk by a center, radius and normal.
 * 
 * @version 1.0
 * @author parryrm
 *
 */
public class Disk extends Plane {

	/** The radius. */
	protected double radius = 1.0;
	
	@Override
	public double intersect(Point3 e, Vector3 d)
	{
		Vector3 cameraToSurf = new Vector3();
		cameraToSurf.sub(this.point, e);
		double val = this.normal.dot(cameraToSurf) / this.normal.dot(d);
		
		Point3 pointOnSurface = new Point3(e);
		pointOnSurface.scaleAdd(val, d);
		return this.radius < pointOnSurface.distance(this.point) ? -1 : val;
	}
	/**
	 * Setter method for the disk's radius.
	 * @param radius the new radius.
	 */
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	/**
	 * Getter method for the disk's radius.
	 * @return the disk's radius
	 */
	public double getRadius()
	{
		return this.radius;
	}
	
	/**
	 * Constructor for the disk.
	 * Currently does nothing.
	 */
	public Disk()
	{
		
	}

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "disk " + point + " " + normal + " " + radius + " " + shader + " end";
	}

}
