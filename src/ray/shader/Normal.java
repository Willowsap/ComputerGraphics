package ray.shader;

import ray.math.Color;
import ray.math.Vector3;

/**
 * This shader shows the surface normal instead of the color.
 * 
 * @author parryrm
 * @version 1.0
 *
 */
public class Normal implements Shader {

	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "normal end";
	}
	
	/**
	 * Calculates the color based on the surface normal.
	 * @param normal the normal to calculate the color of.
	 * @return the calculated color.
	 */
	public Color calcColor(Vector3 normal)
	{
		// x = red, y = green, z = blue
		// formula from p1 document
        return new Color((normal.x + 1) / 2,
        		(normal.y + 1) / 2,
        		(normal.z + 1) / 2);
    }

	@Override
	public Color getColor(Vector3 n, Vector3 l) {
		return this.calcColor(n);
	}

}