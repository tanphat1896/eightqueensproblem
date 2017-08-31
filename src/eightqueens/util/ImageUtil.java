package eightqueens.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {
	public static Image darkBG;
	public static Image queen;
	public static Image danger;
	public static Image ok;
	public static Image poll;
	
	public static void initImage(int size) {
		darkBG = getScaledImage("dark_background.png", size);
		queen = getScaledImage("queen.png", size);
		danger = getScaledImage("x.png", size);
		ok = getScaledImage("ok.png", size);
		poll = getScaledImage("poll.png", size);
	}
	
	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon(Util.IMAGE_URI + name);
	}
	
	public static Image getScaledImage(String name, int size) {
		try {
			BufferedImage bimg = ImageIO.read(new File(Util.IMAGE_URI + name));
			return bimg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		} catch (IOException e) {
//			e.printStackTrace();
			return new ImageIcon().getImage();
		}
	}
}