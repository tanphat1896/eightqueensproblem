package eightqueens.util;

import java.awt.Color;
import java.awt.Image;

public class Config {
	/**
	 * Data config
	 */
	public static int totalQueen = 8;
	
	/**
	 * Board config
	 */
	public static Image defaultQueen = null;
	public static Image boardBgImg = null;
	public static Color boardBgColor = null;
	public static Color boardSafeCellColor = null;
	public static boolean useLeftBoard = false;
	
	/**
	 * UI Config
	 */
	public static boolean isShowSafeCell = false;
	public static boolean isShowInspectedCell = false;
	public static boolean isShowDangerCell = false;
	public static boolean messageDisabled = false;
	
	public static boolean isUseSliderTimer = true;
	public static int solveSpeed = 100;
}
