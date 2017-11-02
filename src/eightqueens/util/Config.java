package eightqueens.util;

import java.awt.Color;

public class Config {
	/**
	 * Data config
	 */
	public static int totalQueen = 8;

	/**
	 * Board config
	 */
//	public static Image defaultQueen = null;
//	public static Image boardBgImg = null;
	public static String defaultQueen = "basic";
	public static String boardBgImg = "wood";
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

	public static void resetConfig() {
		totalQueen = 8;
		defaultQueen = null;
		boardBgImg = null;
		boardBgColor = null;
		boardSafeCellColor = null;
		useLeftBoard = false;

		isShowSafeCell = false;
		isShowInspectedCell = true;
		isShowDangerCell = false;
		messageDisabled = false;

		isUseSliderTimer = true;
		solveSpeed = 100;
	}
}
