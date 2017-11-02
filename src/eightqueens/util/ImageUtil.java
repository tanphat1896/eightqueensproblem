package eightqueens.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtil {
	public static Image darkBG;
	public static Image queen;
	public static Image danger;
	public static Image ok;
	public static Image poll;
	public static int size;
	
	public static void initImage(int size) {
		ImageUtil.size = size;
		darkBG = getScaledImage((Config.boardBgImg == null ? "wood": Config.boardBgImg) + "_bg.png", size);
		queen = getScaledImage((Config.defaultQueen == null ? "basic": Config.defaultQueen) + "_queen.png", size);
		danger = getScaledImage("x.png", size);
		ok = getScaledImage("found.png", size);
		poll = getScaledImage("poll.png", size);
	}
	
	public static void reinitBackground(String color) {
		darkBG = getScaledImage(color + "_bg.png", size);
		Config.boardBgImg = color;
	}
	
	public static void reinitQueen(String type) {
		queen = getScaledImage(type + "_queen.png", size);
		Config.defaultQueen = type;
	}
	
	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + name));
	}
	
	public static ImageIcon getImageIconWithSize(String name, int size) {
		return new ImageIcon(getScaledImage(name, size));
	}
	
	public static Image getScaledImage(String name, int size) {
		try {
			BufferedImage bimg = ImageIO.read(Util.getResource(Util.IMAGE_FOLDER + name));
//			BufferedImage bimg = ImageIO.read(ImageUtil.class.getResource("/resource/image/" + name));
			return bimg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
		} catch (IOException e) {
//			e.printStackTrace();
		} 
		return new ImageIcon().getImage();
	}
}