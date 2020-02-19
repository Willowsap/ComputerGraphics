package ray;

import ray.math.Point3;
import ray.math.Vector3;

/**
 * Represents a simple camera.
 * @author ags
 * @version 1.0
 */
public class Camera
{
	private Vector3 u;
	
	private Vector3 v;
	
	private Vector3 w;
	
	/*
	 * Fields that are read in from the input file to describe the camera.
	 * You'll probably want to store some derived values to make ray generation easy.
	 */
	
	protected final Point3 viewPoint = new Point3();
	
	protected final Vector3 viewDir = new Vector3(0, 0, -1);
	
	protected final Vector3 viewUp = new Vector3(0, 1, 0);
	
	protected final Vector3 projNormal = new Vector3(0, 0, 1);
	
	protected double viewWidth = 1.0;
	
	protected double viewHeight = 1.0;
	
	protected double projDistance = 1.0;
	
	/**
	 * Setter method for the camera's viewpoint.
	 * @param viewPoint the new viewpoint.
	 */
	public void setViewPoint(Point3 viewPoint)
	{
		this.viewPoint.set(viewPoint);
	}
	
	/**
	 * Getter method for the camera's viewpoint.
	 * @return viewPoint.
	 */
	public Point3 getViewPoint()
	{
		return this.viewPoint;
	}
	
	/**
	 * Setter method for the camera's viewDir.
	 * @param viewDir the new viewDir.
	 */
	public void setViewDir(Vector3 viewDir)
	{
		this.viewDir.set(viewDir);
	}
	
	/**
	 * Getter method for the camera's viewDir.
	 * @return viewDir.
	 */
	public Vector3 getViewDir()
	{
		return this.viewDir;
	}
	
	/**
	 * Setter method for the camera's viewUp.
	 * @param viewUp the new viewUp.
	 */
	public void setViewUp(Vector3 viewUp)
	{
		this.viewUp.set(viewUp);
	}
	
	/**
	 * Getter method for the camera's viewUp.
	 * @return viewUp.
	 */
	public Vector3 getViewUp()
	{
		return this.viewUp;
	}
	
	/**
	 * Setter method for the camera's projNormal.
	 * @param projNormal the new projNormal.
	 */
	public void setProjNormal(Vector3 projNormal)
	{
		this.projNormal.set(projNormal);
	}
	
	/**
	 * Getter method for the camera's projNormal.
	 * @return projNormal.
	 */
	public Vector3 getProjNormal()
	{
		return this.projNormal;
	}
	
	/**
	 * Setter method for the camer'a viewWidth.
	 * @param viewWidth the new viewWidth.
	 */
	public void setViewWidth(double viewWidth)
	{
		this.viewWidth = viewWidth;
	}
	
	/**
	 * Getter method for the camera's viewWidth.
	 * @return viewWidth;
	 */
	public double getViewWidth()
	{
		return this.viewWidth;
	}
	
	/**
	 * Setter method for the camera's viewHeight.
	 * @param viewHeight the new viewHeight.
	 */
	public void setViewHeight(double viewHeight)
	{
		this.viewHeight = viewHeight;
	}

	/**
	 * Getter method for the camera's viewHeight.
	 * @return viewHeight.
	 */
	public double getViewHeight()
	{
		return this.viewHeight;
	}
	
	/**
	 * Setter method for the camera's projDistance.
	 * @param projDistance the new projDistance.
	 */
	public void setprojDistance(double projDistance)
	{
		this.projDistance = projDistance;
	}
	
	/**
	 * Getter method for the camera's projDistance.
	 * @return projDistance.
	 */
	public double getProjDistance()
	{
		return this.projDistance;
	}
	
	/**
	 * Getter method for the cameras u vector.
	 * Must run prepare before accessing u.
	 * @return the u vector.
	 */
	public Vector3 getU()
	{
		return this.u;
	}
	
	/**
	 * Getter method for the camera's v vector.
	 * Must run prepare before accessing v.
	 * @return the v vector.
	 */
	public Vector3 getV()
	{
		return this.v;
	}
	
	/**
	 * Getter method for the camera's w vector.
	 * Must run prepare before accessing w.
	 * @return the w vector.
	 */
	public Vector3 getW()
	{
		return this.w;
	}
	
	/**
	 * Prepares the camera to be used.
	 * Normalizes the vectors.
	 * Creates the coordinate system.
	 */
	public void prepare()
	{
		this.projNormal.normalize();
		this.viewDir.normalize();	
		this.viewUp.normalize();
		
		// w is opposite the view direction
		w = new Vector3();
		w.scaleAdd(-1, this.viewDir);
		w.normalize();
						
		// u is perpendicular to w and to up-vector
		u = new Vector3();
		u.cross(this.viewUp, w);
		u.normalize();
					
		// v is perpendicular to w and u
		v = new Vector3();
		v.cross(w, u);
		v.normalize();			
	}
}
