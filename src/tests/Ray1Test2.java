package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.junit.Test;

import ray.Image;
import ray.Parser;
import ray.RayTracer;
import ray.Scene;
import ray.math.Color;

/**
 * JUnit test cases for the Ray1 ray tracer.
 * 
 * @author Mitch Parry
 * @version 2013-09-10
 * 
 */
public class Ray1Test2
{
    final static double EPS = 1e-2;
    final static double GAMMA = 2.2;
    final static int LOW_8_BITS = 0xFF;
    final static double MAX_BYTE = 255.0;
    final String testDirectory = "scenes/";
    private Pattern importPattern; 
    private String[] allowedImports;
    private String importsError;
    
    public Ray1Test2()
    {
    	this.importPattern = Pattern.compile("(^|\\s)import\\s+([A-Za-z0-9]+(\\s*\\.\\s*[A-Za-z0-9]+)+\\s*;)");
    	this.allowedImports = new String[] {
        		"java.awt.Transparency",
        		"java.awt.color.ColorSpace",
        		"java.awt.image.BufferedImage",
        		"java.awt.image.ComponentColorModel",
        		"java.awt.image.DataBufferFloat",
        		"java.awt.image.PixelInterleavedSampleModel",
        		"java.awt.image.WritableRaster",
        		"java.awt.image.Raster",
        		"java.io.File",
        		"java.io.IOException",
        		"java.lang.reflect.Array",
        		"java.lang.reflect.Constructor",
        		"java.lang.reflect.Method",
        		"java.lang.Math",
                "java.nio.file.Files",
                "java.nio.file.Paths",
        		"java.util.ArrayList",
        		"java.util.HashMap",
        		"java.util.StringTokenizer",
        		"java.util.regex",
        		"javax.imageio.ImageIO",
        		"javax.xml.parsers.DocumentBuilder",
                "org.junit",
        		"org.w3c.dom.Document",
        		"org.w3c.dom.Element",
        		"org.w3c.dom.NamedNodeMap",
        		"org.w3c.dom.Node",
        		"ray.",
        		"solution."
        };
    	importsError = null;
    }    

    /**
     * Wrapper for checkImports
     * 
     */
    public void importsPass()
    {
    	if (importsError == null)
    	{
    		importsError = checkImports(".");
    		System.out.println("detected: " + importsError);
    	}
    	
    	if (importsError != "pass")
    	{
    		System.out.println("failing: " + importsError);
    		fail(importsError);
    	}
    	
    }
    
    /**
     * Check imports
     */
    public String checkImports(String folderPath)
    {
		System.out.println("Checking imports: " + folderPath);
    	final File folder = new File(folderPath);
        for (final File fileEntry : folder.listFiles()) {
        	String[] result = Ray1Test2.class.getName().split("\\.");
        	String className = result[result.length-1];
            if (fileEntry.isDirectory()) {
                String s = checkImports(folderPath + "/" + fileEntry.getName());
                if (s != "pass")
                {
                	return s;
                }
            } else if (fileEntry.isFile() & fileEntry.getName().toLowerCase().endsWith("java")
            		& !fileEntry.getName().startsWith(className)) {
            	String filePath = folderPath + "/" + fileEntry.getName();
                System.out.println(filePath);
                try
                {
                    String java = new String(Files.readAllBytes(Paths.get(filePath)));
                    Matcher importMatcher = importPattern.matcher(java);
                    while (importMatcher.find()) 
                    {
                    	String match = importMatcher.group();
                    	match = match.replaceAll("\\s", "");
                    	match = match.replace("import", "");
                    	System.out.println(match);
                    	boolean allowed = false;
                    	for (String allowedImport : allowedImports)
                    	{
                    		if (match.startsWith(allowedImport))
                    		{
                    			allowed = true;
                    			break;
                    		}
                    	}
                    	if (!allowed)
                    	{
                    		System.out.println("Disallowed");
                    		return "The import " + match + " is not allowed in the file " + fileEntry.getName();
                    	}
                    }
                }
                catch (IOException e)
                {
                	e.printStackTrace();
                	return "Import code threw IOException.";
                }
            }
        }
        return "pass";
    }
    
    
    /**
     * This tests sphere intersection.
     */
    @Test
    public void testSphereIntersection()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"one-sphere-constant",
   			"four-spheres-constant",
   			"one-sphere-constant-reverse",
   			"four-spheres-constant-reverse"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testSphereIntersection2() {testSphereIntersection();}
    @Test public void testSphereIntersection3() {testSphereIntersection();}
    
    /**
     * This tests sphere normals.
     */
    @Test
    public void testSphereNormals()
    {
    	importsPass();
    	String[] tests = new String[] {
    		"one-sphere-normal",
       		"four-spheres-normal",
       		"one-sphere-normal-reverse",
       		"four-spheres-normal-reverse"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testSphereNormals2() {testSphereNormals();}
    @Test public void testSphereNormals3() {testSphereNormals();}

    /**
     * This tests box intersection.
     */
    @Test
    public void testBoxIntersection()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"one-box-constant",
   			"two-boxes-constant",
   			"one-box-constant-reverse",
   			"two-boxes-constant-reverse"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testBoxIntersection2() {testBoxIntersection();}
    @Test public void testBoxIntersection3() {testBoxIntersection();}

    /**
     * This tests box normals.
     */
    @Test
    public void testBoxNormals()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"one-box-normal",
   			"two-boxes-normal",
   			"one-box-normal-reverse",
   			"two-boxes-normal-reverse"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testBoxNormals2() {testBoxNormals();}
    @Test public void testBoxNormals3() {testBoxNormals();}
    
    /**
     * This tests diffuse shading.
     */
    @Test
    public void testDiffuseShading()
    {
    	importsPass();
    	String[] tests = new String[] {
    		"one-box-diffuse",
    		"two-boxes-diffuse",
    		"one-sphere-diffuse",
    		"four-spheres-diffuse"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testDiffuseShading2() {testDiffuseShading();}
    @Test public void testDiffuseShading3() {testDiffuseShading();}
    
    
    /**
     * This tests specular shading.
     */
    @Test
    public void testSpecularShading()
    {
    	importsPass();
    	String[] tests = new String[] {
    		"one-box-specular",
    		"two-boxes-specular",
    		"one-sphere-specular",
    		"four-spheres-specular"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testSpecularShading2() {testSpecularShading();}
    @Test public void testSpecularShading3() {testSpecularShading();}
    
    /**
     * This tests the wire box model.
     */
    @Test
    public void testWireBox()
    {
    	importsPass();
    	String[] tests = new String[] {
    		"wire-box-axon",
    		"wire-box-orth",
    		"wire-box-per"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    @Test public void testWireBox2() {testWireBox();}
    @Test public void testWireBox3() {testWireBox();}

    /**
     * This tests the wire box model.
     */
    @Test
    public void testWireBoxShiftedPerspective()
    {
    	importsPass();
    	String[] tests = new String[] {
    		"wire-box-sper",
    		"wire-box-sper2"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }      

    /**
     * Helper method that runs a specific test.
     * 
     * @param xmlFile
     *            The filename for the test.
     */
    private static void runTest(String xmlFile)
    {
        String pngFile = xmlFile + ".correct.png";
        String[] tokens = xmlFile.split("/");
        String testName = tokens[tokens.length - 1];
        Parser parser = new Parser();
        Scene scene = (Scene) parser.parse(xmlFile, Scene.class);
        RayTracer.renderImage(scene);
        Image result = scene.getImage();
        writeRead(result);
        Image correct = new Image(result.getWidth(), result.getHeight());
        read(pngFile, correct);
        double rmse = difference(result, correct);
        System.out.println(xmlFile + " : rmse = " + rmse);
        assertEquals(testName + " example fails with rmse = " + rmse,
                0, rmse, EPS);
    }

    /**
     * Read this image from the filename. The output is always read as a PNG
     * regardless of the extension on the filename given.
     * 
     * @param fileName
     *            the input filename
     * @param image
     *            The output image
     */
    public static void read(String fileName, Image image)
    {
        BufferedImage bufferedImage;
        try
        {
            bufferedImage = ImageIO.read(new File(fileName));
        }
        catch (Exception e)
        {
        	String message = "Error occured while attempting to read file: " + fileName;
            System.out.println(message);
            System.err.println(e);
            e.printStackTrace();
            fail(message);
            return;
        }
        
        boolean allBlack = true;
        Color pixelColor = new Color();
        for (int y = 0; y < image.getHeight(); y++)
        {
            for (int x = 0; x < image.getWidth(); x++)
            {
                int rgb = bufferedImage.getRGB(x, (image.getHeight() - 1 - y));
                double blue = Math.pow((rgb & LOW_8_BITS) / MAX_BYTE, GAMMA);
                double green = Math.pow(((rgb >> 8) & LOW_8_BITS) / MAX_BYTE,
                        GAMMA);
                double red = Math.pow(((rgb >> 16) & LOW_8_BITS) / MAX_BYTE,
                        GAMMA);
                pixelColor.set(red, green, blue);
                image.setPixelColor(pixelColor, x, y);
                if (pixelColor.toInt() != 0)
                {
                	allBlack = false;
                }
            }
        }
        assertEquals("read image that is all black.", allBlack, false);
    }

    /**
     * Computes the root mean square difference between the two images. If they
     * have different sizes it returns infinity.
     * 
     * @param im1
     *            The first image.
     * @param im2
     *            The second image.
     * @return The root mean square difference.
     */
    private static double difference(Image im1, Image im2)
    {
        if (im1.getWidth() != im2.getWidth()
                || im1.getHeight() != im2.getHeight())
        {
            System.out.println("Error: Images have different sizes");
            return Double.POSITIVE_INFINITY;
        }
        int width = im1.getWidth();
        int height = im1.getHeight();
        Color color1 = new Color();
        Color color2 = new Color();
        double mse = 0.0;
        double diff = 0.0;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                im1.getPixelColor(color1, x, y);
                im2.getPixelColor(color2, x, y);
                diff = color1.r - color2.r;
                mse += diff * diff;
                diff = color1.g - color2.g;
                mse += diff * diff;
                diff = color1.b - color2.b;
                mse += diff * diff;

            }
        }
        mse /= (width * height);
        return Math.sqrt(mse);
    }

    /**
     * Simulates the process of writing a PNG and then reading it back in again.
     * 
     * @param image
     *            The image to change.
     */
    private static void writeRead(Image image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        boolean allBlack = true;
        Color pixelColor = new Color();
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                image.getPixelColor(pixelColor, x, y);
                pixelColor.gammaCorrect(GAMMA);
                int rgb = pixelColor.toInt();
                double blue = Math.pow((rgb & LOW_8_BITS) / MAX_BYTE, GAMMA);
                double green = Math.pow(((rgb >> 8) & LOW_8_BITS) / MAX_BYTE,
                        GAMMA);
                double red = Math.pow(((rgb >> 16) & LOW_8_BITS) / MAX_BYTE,
                        GAMMA);
                pixelColor.set(red, green, blue);
                image.setPixelColor(pixelColor, x, y);
                if (pixelColor.toInt() != 0)
                {
                	allBlack = false;
                }
            }
        }
        assertEquals("writeRead image is all black.", allBlack, false);
    }
}
