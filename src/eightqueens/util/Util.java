package eightqueens.util;
import java.net.URL;

public class Util {
	public static final String ROOT_URI = System.getProperty("user.dir");
	public static final String RESOURCE_FOLDER = "/resource";
	public static final String IMAGE_FOLDER = RESOURCE_FOLDER + "/image/";;
	
	public static URL getResource(String fullPath) {
		return Util.class.getResource(fullPath);
	}
}
