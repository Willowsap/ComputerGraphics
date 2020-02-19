package ray.shader;

import ray.math.Color;
import ray.math.Vector3;

/**
 * A material unaffected by light.  Points on its surface always appear the same color.
 *
 * @version 1.0
 * @author parryrm
 */
public class Constant implements Shader {

	/** The color of the surface. */
	protected final Color color = new Color(1, 1, 1);
	
	/**
	 * Setter method for the constant's color.
	 * @param color the new color.
	 */
	public void setColor(Color color)
	{ 
		this.color.set(color);
	}
	
	/**
	 * Getter method for the constant's color.
	 * @return the constant's color.
	 */
	public Color getColor(Vector3 n, Vector3 l)
	{
		return this.color;
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "constant " + color + " end";
	}
}
