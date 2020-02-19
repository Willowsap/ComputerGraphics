package ray.shader;

import ray.math.Color;
import ray.math.Vector3;

/**
 * A Phong material. Uses the Modified Blinn-Phong model which is energy
 * preserving and reciprocal.
 *
 * @version 1.0
 * @author ags
 */
public class Phong implements Shader {
	
	/** The color of the diffuse reflection. */
	protected final Color diffuseColor = new Color(1, 1, 1);
	
	/** The color of the specular reflection. */
	protected final Color specularColor = new Color(1, 1, 1);
	
	/** The exponent controlling the sharpness of the specular reflection. */
	protected double exponent = 1.0;
	
	public Color getColor(Vector3 n, Vector3 l)
	{
		return this.diffuseColor;
	}
	
	/**
	 * Setter method for the Phong's diffuseColor.
	 * @param diffuseColor the new diffuseColor.
	 */
	public void setDiffuseColor(Color diffuseColor)
	{
		this.diffuseColor.set(diffuseColor);
	}
	
	/**
	 * Getter method for the Phong's diffuseColor.
	 * @return the diffuseColor.
	 */
	public Color getDiffuseColor()
	{
		return this.diffuseColor;
	}
	
	/**
	 * Setter method for the Phong's specularColor.
	 * @param specularColor the new specularColor.
	 */
	public void setSpecularColor(Color specularColor)
	{
		this.specularColor.set(specularColor);
	}
	
	/**
	 * Getter method for the Phong's specularColor.
	 * @return the specularColor.
	 */
	public Color getSpecularColor()
	{
		return this.specularColor;
	}
	
	/**
	 * Setter method for the Phong's exponent.
	 * @param exponent the new exponent.
	 */
	public void setExponent(double exponent)
	{
		this.exponent = exponent;
	}
	
	/**
	 * Getter method for the Phong's exponent.
	 * @return the exponent.
	 */
	public double getExponent()
	{
		return this.exponent;
	}
	
	/**
	 * Constructor for Phong.
	 * Currently does nothing.
	 */
	public Phong()
	{
		
	}
	
	/**
	 * @see Object#toString()
	 */
	public String toString()
	{
		return "phong " + diffuseColor + " " + specularColor + " " + exponent + " end";
	}
}
