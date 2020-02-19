package ray.surface;

import ray.math.Point3;
import ray.math.Vector3;
import ray.shader.Shader;

/**
 * Abstract base class for all surfaces.  Provides access for shader and
 * intersection uniformly for all surfaces.
 *
 * @version 1.0
 * @author ags
 */
public abstract class Surface {
	
	public abstract double intersect(Point3 e, Vector3 d);
	public abstract Vector3 getNormal(Point3 p);
	
	/** Shader to be used to shade this surface. */
	protected Shader shader = Shader.DEFAULT_MATERIAL;
	
	/**
	 * Setter for the surface's shader.
	 * @param material the new shader.
	 */
	public void setShader(Shader material)
	{
		this.shader = material;
	}
	
	/**
	 * Getter for the surface's shader.
	 * @return the surface's shader.
	 */
	public Shader getShader()
	{
		return shader;
	}	
}