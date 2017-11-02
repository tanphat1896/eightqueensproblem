package eightqueens.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import eightqueens.ui.redefinecomp.*;

public class GUIUtil {

	public static final int BTN_TEXT_SIZE = 13;
	public static final int MAIN_TEXT_SIZE = 13;
	
	public static final String FONT_NAME = "Tahoma";
	
	public static final Font MAIN_FONT = new Font(FONT_NAME, Font.PLAIN, MAIN_TEXT_SIZE);
	public static final Font BTN_FONT = new Font("Tahoma", Font.PLAIN, BTN_TEXT_SIZE);
	
	public static final Color MAIN_BG = new Color(249, 249, 249);
	public static final Color BTN_BG = new Color(231, 245, 253);
	public static final Color BTN_FG = new Color(40, 40, 40);
	public static final Color BTN_COLOR_BORDER = new Color(96, 182, 221);
	
	public static final Color BORDER_PANEL = new Color(184, 201, 224);
	
	public static void changeButtonAppearance(JButton btn, Color bg, Color fg) {
		btn.setFont(BTN_FONT);
		btn.setFocusPainted(false);
		btn.setBorder(new RoundedBorder(7, Color.lightGray)); 
		btn.setBackground(bg);
		btn.setForeground(fg);
	}
	
	public static void changeButtonAppearance(JButton btn) {
		btn.setFont(BTN_FONT);
		btn.setFocusPainted(false);
		btn.setBorder(new RoundedBorder(7, BTN_COLOR_BORDER)); 
		btn.setBackground(BTN_BG);
	    btn.setForeground(BTN_FG);
	}
}
