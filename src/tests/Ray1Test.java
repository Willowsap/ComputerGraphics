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
public class Ray1Test
{
    final static double EPS = 1e-2;
    final static double GAMMA = 2.2;
    final static int LOW_8_BITS = 0xFF;
    final static double MAX_BYTE = 255.0;
    final String testDirectory = "scenes/";
    private Pattern importPattern; 
    private String[] allowedImports;
    private String importsError;
    
    public Ray1Test()
    {
    	importPattern = Pattern.compile("(^|\\s)import\\s+([A-Za-z0-9]+(\\s*\\.\\s*[A-Za-z0-9]+)+\\s*;)");
    	allowedImports = new String[] {
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
    		System.out.println("failed: " + importsError);
    		fail(importsError);
    	}
    	
    }
    
    /**
     * Check imports
     */
    public String checkImports(String folderPath)
    {
		System.out.println("Checking imports");
    	final File folder = new File(folderPath);
        for (final File fileEntry : folder.listFiles()) {
        	String[] result = Ray1Test.class.getName().split("\\.");
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
     * This tests planes with constant color.
     */
    @Test
    public void testPlaneConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"rubiks-blue",
    		"rubiks-green",
   			"rubiks-orange",
   			"rubiks-red",
   			"rubiks-white",
   			"rubiks-yellow"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests two planes with constant color.
     */
    @Test
    public void testTwoPlanesConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-orange",
       			"rubiks-blue-red",
       			"rubiks-blue-white",
       			"rubiks-blue-yellow",
       			"rubiks-green-orange",
       			"rubiks-green-red",
       			"rubiks-green-white",
       			"rubiks-green-yellow",
       			"rubiks-white-orange",
       			"rubiks-white-red",
       			"rubiks-yellow-orange",
       			"rubiks-yellow-red"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests three planes with constant color.
     */
    @Test
    public void testThreePlanesConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-white-orange",
       			"rubiks-blue-white-red",
       			"rubiks-blue-yellow-orange",
       			"rubiks-blue-yellow-red",
       			"rubiks-green-white-orange",
       			"rubiks-green-white-red",
      			"rubiks-green-yellow-orange",
       			"rubiks-green-yellow-red"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }    
    
    /**
     * This tests planes shading the surface normals.
     */
    @Test
    public void testPlaneNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"rubiks-blue-normal",
    		"rubiks-green-normal",
   			"rubiks-orange-normal",
   			"rubiks-red-normal",
   			"rubiks-white-normal",
   			"rubiks-yellow-normal"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests two planes shading the surface normals.
     */
    @Test
    public void testTwoPlanesNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-orange-normal",
       			"rubiks-blue-red-normal",
       			"rubiks-blue-white-normal",
       			"rubiks-blue-yellow-normal",
       			"rubiks-green-orange-normal",
       			"rubiks-green-red-normal",
       			"rubiks-green-white-normal",
       			"rubiks-green-yellow-normal",
       			"rubiks-white-orange-normal",
       			"rubiks-white-red-normal",
       			"rubiks-yellow-orange-normal",
       			"rubiks-yellow-red-normal"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests three planes shading the surface normals.
     */
    @Test
    public void testThreePlanesNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-white-orange-normal",
       			"rubiks-blue-white-red-normal",
       			"rubiks-blue-yellow-orange-normal",
       			"rubiks-blue-yellow-red-normal",
       			"rubiks-green-white-orange-normal",
       			"rubiks-green-white-red-normal",
      			"rubiks-green-yellow-orange-normal",
       			"rubiks-green-yellow-red-normal"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }    

    /**
     * This tests disks with constant color.
     */
    @Test
    public void testDiskConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"rubiks-blue-disk",
    		"rubiks-green-disk",
   			"rubiks-orange-disk",
   			"rubiks-red-disk",
   			"rubiks-white-disk",
   			"rubiks-yellow-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests two disks with constant color.
     */
    @Test
    public void testTwoDisksConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-orange-disk",
       			"rubiks-blue-red-disk",
       			"rubiks-blue-white-disk",
       			"rubiks-blue-yellow-disk",
       			"rubiks-green-orange-disk",
       			"rubiks-green-red-disk",
       			"rubiks-green-white-disk",
       			"rubiks-green-yellow-disk",
       			"rubiks-white-orange-disk",
       			"rubiks-white-red-disk",
       			"rubiks-yellow-orange-disk",
       			"rubiks-yellow-red-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests three planes with constant color.
     */
    @Test
    public void testThreeDisksConstant()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-white-orange-disk",
       			"rubiks-blue-white-red-disk",
       			"rubiks-blue-yellow-orange-disk",
       			"rubiks-blue-yellow-red-disk",
       			"rubiks-green-white-orange-disk",
       			"rubiks-green-white-red-disk",
      			"rubiks-green-yellow-orange-disk",
       			"rubiks-green-yellow-red-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }    
    
    /**
     * This tests planes shading the surface normals.
     */
    @Test
    public void testDiskNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"rubiks-blue-normal-disk",
    		"rubiks-green-normal-disk",
   			"rubiks-orange-normal-disk",
   			"rubiks-red-normal-disk",
   			"rubiks-white-normal-disk",
   			"rubiks-yellow-normal-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests two planes shading the surface normals.
     */
    @Test
    public void testTwoDisksNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-orange-normal-disk",
       			"rubiks-blue-red-normal-disk",
       			"rubiks-blue-white-normal-disk",
       			"rubiks-blue-yellow-normal-disk",
       			"rubiks-green-orange-normal-disk",
       			"rubiks-green-red-normal-disk",
       			"rubiks-green-white-normal-disk",
       			"rubiks-green-yellow-normal-disk",
       			"rubiks-white-orange-normal-disk",
       			"rubiks-white-red-normal-disk",
       			"rubiks-yellow-orange-normal-disk",
       			"rubiks-yellow-red-normal-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }

    /**
     * This tests three planes shading the surface normals.
     */
    @Test
    public void testThreeDisksNormal()
    {
    	importsPass();
    	String[] tests = new String[] {
       			"rubiks-blue-white-orange-normal-disk",
       			"rubiks-blue-white-red-normal-disk",
       			"rubiks-blue-yellow-orange-normal-disk",
       			"rubiks-blue-yellow-red-normal-disk",
       			"rubiks-green-white-orange-normal-disk",
       			"rubiks-green-white-red-normal-disk",
      			"rubiks-green-yellow-orange-normal-disk",
       			"rubiks-green-yellow-red-normal-disk"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }       
    
    
    /**
     * This tests the rays generated using Mario.
     */
    @Test
    public void testDisks()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"mario-red",
   			"mario-green",
   			"mario-blue",
   			"mario-rgb",
   			"mario-full"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }
    
    /**
     * This tests the rays generated using Mario and precise disks.
     */
    @Test
    public void testDisksPrecise()
    {
    	importsPass();
    	String[] tests = new String[] {
   			"mario-red-precise",
   			"mario-green-precise",
   			"mario-blue-precise",
   			"mario-rgb-precise",
   			"mario-full-precise"
    	};
    	for (String test: tests) {
    		String xmlFile = testDirectory + test + ".xml";
    		runTest(xmlFile);
    	}
    }    
    
    /**
     * This tests a disk in front of a plane
     */
    @Test
    public void testPlaneDisk()
    {
    	importsPass();
        String test = "plane-disk";
        String xmlFile = testDirectory + test + ".xml";
        runTest(xmlFile);
    }

    /**
     * This tests three disks.
     */
    @Test
    public void testThreeDisks()
    {
    	importsPass();
        String test = "three-disks";
        String xmlFile = testDirectory + test + ".xml";
        runTest(xmlFile);
    }

    /**
     * This tests three planes.
     */
    @Test
    public void testThreePlanes()
    {
    	importsPass();
        String test = "three-planes";
        String xmlFile = testDirectory + test + ".xml";
        runTest(xmlFile);
    }

    /**
     * This tests three planes with normal shading.
     */
    @Test
    public void testThreePlanesNormal2()
    {
    	importsPass();
        String test = "three-planes-normal";
        String xmlFile = testDirectory + test + ".xml";
        runTest(xmlFile);
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
