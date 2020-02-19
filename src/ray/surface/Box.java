package ray.surface;

import ray.math.Point3;
import ray.math.Vector3;

/**
 * Represents a box object.
 * @version 1.0
 * @author ags
 *
 */
public class Box extends Surface {
	
	/* The corner of the box with the smallest x, y, and z components. */
	protected final Point3 minPt = new Point3();
	
	/* The corner of the box with the largest x, y, and z components. */
	protected final Point3 maxPt = new Point3();
	
	public Vector3 getNormal(Point3 p)
	{
		return null;
	}
	
	/**
	 * Setter method for the box's minPt
	 * @param minPt the new minPt
	 */
	public void setMinPt(Point3 minPt)
	{
		this.minPt.set(minPt);
	}
	
	/**
	 * Getter method for the box's minPt
	 * @return the box's minPt
	 */
	public Point3 getMinPoint()
	{
		return this.minPt;
	}

	/**
	 * Setter method for the box's maxPt.
	 * @param maxPt the new maxPt
	 */
	public void setMaxPt(Point3 maxPt)
	{
		this.maxPt.set(maxPt);
	}
	
	/**
	 * Getter method for the box's maxPt
	 * @return the box's maxPt
	 */
	public Point3 getMaxPoint()
	{
		return this.maxPt;
	}
	
	/**
	 * Box constructor.
	 * Currently does nothing.
	 */
	public Box()
	{
		
	}

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "box " + minPt + " " + maxPt + " " + shader + " end";
	}

	@Override
	public double intersect(Point3 e, Vector3 d) {
		// TODO Auto-generated method stub
		return 0;
	}
}
