package eightqueens.ui;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eightqueens.util.GUIUtil;
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
	private JLabel lblTitleIcon;
	private JLabel lblTitle;
	private JLabel lblImgCaption;
	private JLabel lblNienLuan;
	
	private WelcomeUI() {
		initComponents();
		ui = this;
	}
	
	private void initComponents() {
		this.setIconImage(ImageUtil.getImageIcon("icon.png").getImage());
		this.setTitle("Phần mềm giải bài toán 8 quân hậu");
		this.setSize(603, 352);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		pnContainer = new JPanel(); 
//		{
//			public void paintComponent(Graphics g) {
////				g.drawImage(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "nbg.png")).getImage(), 0, 0, null);
//			}
//		};
		pnContainer.setBackground(GUIUtil.MAIN_BG);
		pnContainer.setLayout(null);
		pnContainer.setBounds(0, 0, 600, 400);
		
		lblTitleIcon = new JLabel();
		lblTitleIcon.setIcon(ImageUtil.getImageIconWithSize("chess.png", 48));
		lblTitleIcon.setBackground(SystemColor.textHighlight);
		lblTitleIcon.setForeground(new Color(25, 25, 112));
		lblTitleIcon.setBounds(25, 25, 48, 48);
		lblTitleIcon.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitleIcon.setHorizontalAlignment(JLabel.CENTER);
		
		
		btnHuman = new JButton("Tự tìm lời giải\r\n");
//		btnHuman.setBorderPainted(false);
//		btnHuman.setForeground(Color.WHITE);
//		btnHuman.setBackground(BaseUI.btnBG);
//		btnHuman.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
//		BaseUI.changeButtonAppearance(btnHuman, BaseUI.btnBG, BaseUI.btnFG);
		GUIUtil.changeButtonAppearance(btnHuman);
		btnHuman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				invokeUI(true);
			}
		});
		btnHuman.setFocusPainted(false);
		btnHuman.setBounds(305, 191, 168, 39);
		
		btnComputer = new JButton("<html><center>Giải thuật quay lui</center></html>");
//		btnComputer.setBorderPainted(false);
//		btnComputer.setForeground(Color.WHITE);
//		btnComputer.setBackground(BaseUI.btnBG);
//		btnComputer.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		GUIUtil.changeButtonAppearance(btnComputer);

//		BaseUI.changeButtonAppearance(btnComputer, BaseUI.btnBG, BaseUI.btnFG);
		btnComputer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invokeUI(false);
			}
		});
		btnComputer.setFocusPainted(false);
		btnComputer.setBounds(305, 140, 168, 39);
		
		getContentPane().add(pnContainer);
		pnContainer.add(lblTitleIcon);
		pnContainer.add(btnComputer);
		pnContainer.add(btnHuman);
		
		JLabel lblChoose = new JLabel("Chọn phương án tìm lời giải");
		lblChoose.setForeground(Color.DARK_GRAY);
		lblChoose.setHorizontalAlignment(SwingConstants.CENTER);
		lblChoose.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblChoose.setBounds(224, 102, 255, 20);
		pnContainer.add(lblChoose);
		
		lblTitle = new JLabel("<html>Phần mềm giải bài toán Tám quân hậu</html>");
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblTitle.setBounds(85, 11, 490, 79);
		pnContainer.add(lblTitle);
		
		lblImgCaption = new JLabel("");
		lblImgCaption.setBounds(12, 102, 200, 200);
		lblImgCaption.setIcon(ImageUtil.getImageIconWithSize("4queen.png", lblImgCaption.getHeight()));
		pnContainer.add(lblImgCaption);
		
		lblNienLuan = new JLabel("Niên luận cơ sở ngành KTPM. Học kỳ 1, 2017-2018");
		lblNienLuan.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNienLuan.setBounds(284, 286, 291, 16);
		pnContainer.add(lblNienLuan);
		
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
