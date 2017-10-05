package eightqueens.ui;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import eightqueens.util.Util;
import java.awt.Color;

@SuppressWarnings("serial")
public class WelcomeUI extends JFrame{
	private JPanel pnContainer;
	private JButton btnComputer;
	private JButton btnHuman;
	private JLabel lblTitle;
	
	private WelcomeUI() {
		initComponents();
	}
	
	private void initComponents() {
		this.setTitle("Phần mềm giải bài toán 8 quân hậu");
		this.setSize(600, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnContainer = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "nbg.png")).getImage(), 
						0, 0, null);
			}
		};
		pnContainer.setLayout(null);
		pnContainer.setBounds(0, 0, 600, 400);
		
		lblTitle = new JLabel("<html><center>Phần mềm giải bài toán <br> Tám quân hậu</center></html>");
		lblTitle.setForeground(new Color(25, 25, 112));
		lblTitle.setBounds(0, 22, 600, 100);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		
		
		btnHuman = new JButton("Giải thủ công");
		btnHuman.setFocusPainted(false);
		btnHuman.setBounds(111, 251, 150, 50);
		
		btnComputer = new JButton("Giải tự động");
		btnComputer.setFocusPainted(false);
		btnComputer.setBounds(343, 251, 150, 50);
		
		getContentPane().add(pnContainer);
		pnContainer.add(lblTitle);
		pnContainer.add(btnComputer);
		pnContainer.add(btnHuman);
		
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Windows Look and feel not support!!");
		}
		new WelcomeUI().setVisible(true);;
	}
}
