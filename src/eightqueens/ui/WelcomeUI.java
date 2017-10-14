package eightqueens.ui;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import eightqueens.util.ImageUtil;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class WelcomeUI extends JFrame{
	public static WelcomeUI ui;
	
	private JPanel pnContainer;
	private JButton btnComputer;
	private JButton btnHuman;
	private JLabel lblTitle;
	private JLabel lblPhnMmGii;
	
	private WelcomeUI() {
		initComponents();
		ui = this;
	}
	
	private void initComponents() {
		this.setIconImage(ImageUtil.getImageIcon("icon.png").getImage());
		this.setTitle("Phần mềm giải bài toán 8 quân hậu");
		this.setSize(515, 257);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnContainer = new JPanel() {
			public void paintComponent(Graphics g) {
//				g.drawImage(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "nbg.png")).getImage(), 0, 0, null);
			}
		};
		pnContainer.setLayout(null);
		pnContainer.setBounds(0, 0, 600, 400);
		
		lblTitle = new JLabel();
		lblTitle.setIcon(ImageUtil.getImageIconWithSize("chess.png", 48));
		lblTitle.setBackground(SystemColor.textHighlight);
		lblTitle.setForeground(new Color(25, 25, 112));
		lblTitle.setBounds(220, 95, 58, 57);
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitle.setHorizontalAlignment(JLabel.CENTER);
		
		
		btnHuman = new JButton("Tự tìm lời giải\r\n");
		BaseUI.changeButtonAppearance(btnHuman, BaseUI.btnBG, BaseUI.btnFG);
		btnHuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				invokeUI(true);
			}
		});
		btnHuman.setFocusPainted(false);
		btnHuman.setBounds(92, 175, 150, 30);
		
		btnComputer = new JButton("<html><center>Giải thuật quay lui</center></html>");

		BaseUI.changeButtonAppearance(btnComputer, BaseUI.btnBG, BaseUI.btnFG);
		btnComputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invokeUI(false);
			}
		});
		btnComputer.setFocusPainted(false);
		btnComputer.setBounds(254, 175, 150, 30);
		
		getContentPane().add(pnContainer);
		pnContainer.add(lblTitle);
		pnContainer.add(btnComputer);
		pnContainer.add(btnHuman);
		
		JLabel lblChnCh = new JLabel("Chọn chế độ");
		lblChnCh.setHorizontalAlignment(SwingConstants.CENTER);
		lblChnCh.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		lblChnCh.setBounds(169, 152, 159, 16);
		pnContainer.add(lblChnCh);
		
		lblPhnMmGii = new JLabel("<html><center>Phần mềm giải bài toán <br>Tám quân hậu</center></html>");
		lblPhnMmGii.setForeground(BaseUI.TEXT_FG);
		lblPhnMmGii.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhnMmGii.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
		lblPhnMmGii.setBounds(12, 12, 475, 79);
		pnContainer.add(lblPhnMmGii);
		
	}
	
	private void invokeUI(boolean userMode) {
		this.dispose();
		if (userMode)
			new HumanUI().setVisible(true);
		else new ComputerUI().setVisible(true);
	}
	
	public static void invoke() {
		ui.setVisible(true);
	}
	
	public static void main(String[] args) {
		new WelcomeUI().setVisible(true);;
	}
}
