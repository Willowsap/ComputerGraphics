package ray.shader;

import ray.math.Color;
import ray.math.Vector3;

/**
 * A Lambertian material scatters light equally in all directions. BRDF value is
 * a constant
 *
 * @version 1.0
 * @author ags
 */
public class Lambertian implements Shader {
	
	/** The color of the surface. */
	protected final Color diffuseColor = new Color(1, 1, 1);
	
	public Color getColor(Vector3 n, Vector3 l)
	{
		return this.diffuseColor;
	}
	
	/**
	 * Setter method for the diffuseColor
	 * @param inDiffuseColor the new diffuseColor
	 */
	public void setDiffuseColor(Color inDiffuseColor)
	{
		diffuseColor.set(inDiffuseColor);
	}
	
	/**
	 * Getter method for diffuseColor.
	 * @return diffuseColor.
	 */
	public Color getDiffuseColor()
	{
		return this.diffuseColor;
	}
	
	/**
	 * The constructor for Lambertian.
	 * Currently does nothing.
	 */
	public Lambertian()
	{
		
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		
		return "lambertian: " + diffuseColor;
	}
	
}
