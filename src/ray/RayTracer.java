package ray;

import ray.math.Color;
import ray.math.Point3;
import ray.math.Vector3;
import ray.surface.Surface;

/**
 * A simple ray tracer.
 *
 * @author ags
 */
public class RayTracer {

	/**
	 * The main method takes all the parameters an assumes they are input files
	 * for the ray tracer. It tries to render each one and write it out to a PNG
	 * file named <input_file>.png.
	 *
	 * @param args
	 */
	public static final void main(String[] args) {

		Parser parser = new Parser();
		for (int ctr = 0; ctr < args.length; ctr++) {

			// Get the input/output filenames.
			String inputFilename = args[ctr];
			String outputFilename = inputFilename + ".png";

			// Parse the input file
			Scene scene = (Scene) parser.parse(inputFilename, Scene.class);

			// Render the scene
			renderImage(scene);

			// Write the image out
			scene.getImage().write(outputFilename);
		}
	}

	public static Vector3 calcD(Camera camera, Image image, int col, int row)
	{
		Vector3 scaledU = new Vector3(camera.getU());
		Vector3 scaledV = new Vector3(camera.getV());
		Vector3 scaledW = new Vector3(camera.getW());
		scaledU.scale( (((double)col) + 0.5) / image.width  * camera.viewWidth  - camera.viewWidth  / 2.0);
		scaledV.scale( (((double)row) + 0.5) / image.height * camera.viewHeight - camera.viewHeight / 2.0); 
		scaledW.scale(camera.projDistance * -1);
		Vector3 d = new Vector3(scaledU);
		d.add(scaledV);
		d.add(scaledW);
		d.normalize();
		return d;
	}
	
	/**
	 * The renderImage method renders the entire scene.
	 *
	 * @param scene The scene to be rendered
	 */
	public static void renderImage(Scene scene)
	{
		Image image = scene.getImage();
		long startTime = System.currentTimeMillis();
		Camera camera = scene.getCamera();
		camera.prepare();
		Point3 e = camera.viewPoint;
		
		for (int col = 0; col < image.width; col++)
		{
			for (int row = 0; row < image.height; row++)
			{
				Vector3 d = calcD(camera, image, col, row);
				double t = Double.MAX_VALUE; 
				Surface closestSurf = null;
				
				for(Surface surf : scene.surfaces)
				{
					double newT = surf.intersect(e, d);
					if (newT > 0 && newT < t)
					{
						t = newT;
						closestSurf = surf;
					}
				}
				
				Point3 pointOnSurf = new Point3(e);
				Vector3 td = new Vector3(d);
				td.scale(t);
				pointOnSurf.add(td);
				
				Color color = new Color(0,0,0);
				// the ray intersects with the surface, color appropriately 
				// depends on if the surface is a constant or a normal
				if (closestSurf != null)
				{
					for (Light light : scene.lights)
					{
						Vector3 rayToLight = new Vector3();
						rayToLight.sub(light.position, pointOnSurf);
						boolean shadow = false;
						for(Surface surf : scene.surfaces)
						{
							if (surf.intersect(pointOnSurf, rayToLight) > 0)
							{
								shadow = true;
								break;
							}
						}
						shadow = false;
						if (!shadow)
						{
							color = closestSurf.getShader().getColor(closestSurf.getNormal(pointOnSurf), rayToLight);
						}
					}
				}
				// add the pixel to the image
				image.setPixelColor(color, col, row);
			}
		}
		// Output time
		long totalTime = (System.currentTimeMillis() - startTime);
		System.out.println("Done.  Total rendering time: "
				+ (totalTime / 1000.0) + " seconds");
	}
}
