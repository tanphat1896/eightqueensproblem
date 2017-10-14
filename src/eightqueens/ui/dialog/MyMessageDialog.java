package eightqueens.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import eightqueens.ui.BaseUI;
import eightqueens.util.ImageUtil;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MyMessageDialog extends JDialog {
	public static final ImageIcon SUCCESS = ImageUtil.getImageIconWithSize("success.png", 32);
	public static final ImageIcon FAILED = ImageUtil.getImageIconWithSize("failed.png", 32);
	private final JPanel contentPanel = new JPanel();
	private JLabel lblIcon;
	private JLabel lblMsg;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MyMessageDialog dialog = new MyMessageDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MyMessageDialog() {
		setBounds(100, 100, 381, 129);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblIcon = new JLabel("");
		lblIcon.setBounds(12, 11, 40, 40);
		lblIcon.setHorizontalAlignment(JLabel.CENTER);
		contentPanel.add(lblIcon);
		
		lblMsg = new JLabel("<html>New<br> label</html>");
		lblMsg.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		lblMsg.setBounds(64, 11, 289, 40);
		contentPanel.add(lblMsg);
		
		JButton btnNewButton = new JButton("\u0110\u00F3ng l\u1EA1i");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disposeDialog();
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 12));
		btnNewButton.setBackground(BaseUI.btnBG);
		btnNewButton.setBounds(274, 56, 79, 25);
		contentPanel.add(btnNewButton);
	}
	
	private void disposeDialog() {
		this.dispose();
	}
	
	public static void showDialog(Window parent, String  msg, String title, ImageIcon icon) {
		new MyMessageDialog().show(parent, msg, title, icon);
	}
	
	private void show(Window parent, String  msg, String title, ImageIcon icon) {
		this.setModal(true);
		this.setLocationRelativeTo(parent);
		this.setIconImage(icon.getImage());
		lblIcon.setIcon(icon);
		this.setTitle(title);
		lblMsg.setText("<html>" + msg + "</html>");
		this.setVisible(true);
	}
}
