package eightqueens.ui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import eightqueens.util.ImageUtil;
import eightqueens.util.Util;

public class MainProgram {
	public static void main(String[] args) {
		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		new CoreUI().setVisible(true);
	}
}
