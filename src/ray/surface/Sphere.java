package ray.surface;

import ray.math.Point3;
import ray.math.Vector3;

/**
 * Represents a sphere as a center and a radius.
 * @version 1.0
 * @author ags
 */
public class Sphere extends Surface {
	
	/** The radius of the sphere. */
	protected double radius = 1.0;
	
	/** The center of the sphere. */
	protected final Point3 center = new Point3();
	
	@Override
	public double intersect(Point3 e, Vector3 d) {
		
		Vector3 eMinusC = new Vector3(e.subReturn(this.center));
		Vector3 negativeD = new Vector3(d);
		negativeD.scale(-1);
		
		double denom = d.dot(d);
		if (denom == 0) return -1;
		double discriminant = Math.pow(d.dot(eMinusC), 2) - d.dot(d)
				* ((eMinusC).dot(eMinusC) - Math.pow(this.radius, 2));
		
		double t1 = (negativeD.dot(eMinusC) + Math.sqrt(discriminant)) / denom;
		double t2 = (negativeD.dot(eMinusC) - Math.sqrt(discriminant)) / denom;
		return t1 > t2 ? t2 : t1;
	}
	
	public Vector3 getNormal(Point3 p)
	{
		Vector3 normal = new Vector3(p.subReturn(this.center));
		normal.normalize();
		return normal;
	}
	
	/**
	 * Setter method for the sphere center.
	 * @param center the new center point of the sphere.
	 */
	public void setCenter(Point3 center)
	{
		this.center.set(center);
	}
	
	/**
	 * Getter method for the sphere center.
	 * @return the sphere center.
	 */
	public Point3 getCenter() 
	{
		return this.center;
	}
	
	/**
	 * The setter method for the sphere's radius.
	 * @param radius the new radius of the sphere.
	 */
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	
	/**
	 * The getter method for the sphere's radius.
	 * @return the sphere's radius.
	 */
	public double getRadius() 
	{
		return this.radius;
	}
	
	/**
	 * Sphere constructor.
	 * Currently does nothing.
	 */
	public Sphere()
	{
		
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "sphere " + center + " " + radius + " " + shader + " end";
	}
}
