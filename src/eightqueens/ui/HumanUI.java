package eightqueens.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import eightqueens.ui.panel.HumanBoard;
import eightqueens.util.ImageUtil;
import eightqueens.util.Result;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class HumanUI extends BaseUI {
	private HumanBoard hmBoard;
	private boolean disabledMsg = false;
	
	private JCheckBox cbDisabledMsg;
	
	private JPanel pnHumanAction;
	private JButton btnClearBoard;
	private JButton btnRemoveQueen;

	public HumanUI() {
		super();
		initBoard();
		initComponents();
		initAction();
	}

	private void initBoard() {
		hmBoard = new HumanBoard(this);
		hmBoard.setBounds(5, 50, hmBoard.getWidth(), hmBoard.getHeight());
		getContentPane().add(hmBoard);
	}

	@Override
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {
        lblColSolution[row].setText(valid ? letter[col] : "");
	}
	
	
	public void notifyFinish(Result result) {
		if (result == Result.FOUND) {
			changeLabelSolutionAppearance(result);
			if (!disabledMsg)
				JOptionPane.showMessageDialog(this, "Đã tìm thấy lời giải!");
		}
		if (result == Result.FAILED) {
			changeLabelSolutionAppearance(result);
			if (!disabledMsg)
				JOptionPane.showMessageDialog(this, "Đã hết ô để đặt cờ, hãy thử lại!");
		}
		changeLabelSolutionAppearance(result);
	}
	
	private void initComponents() {
		pnBoard.setSize(230, 114);
		
		cbDisabledMsg = new JCheckBox();
		cbDisabledMsg.setText("Tắt thông báo");
		cbDisabledMsg.setBounds(12, 86, 206, 18);
		pnBoard.add(cbDisabledMsg);
		
		pnHumanAction = new JPanel(null);
		pnHumanAction.setBorder(new TitledBorder(null, "Hành động", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnHumanAction.setBounds(550, 252, 230, 84);
		btnClearBoard = new JButton("Làm mới bàn cờ");
		btnClearBoard.setBounds(10, 21, 134, 23);
		btnRemoveQueen = new JButton("Xóa quân hậu");
		btnRemoveQueen.setBounds(10, 48, 134, 23);
		pnHumanAction.add(btnClearBoard);
		pnHumanAction.add(btnRemoveQueen);
		
		getContentPane().add(pnHumanAction);
	}

	private void initAction() {
		cboNumber.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				numberOfQueens = Integer.parseInt(e.getItem().toString());
				hmBoard.reinitBoard(numberOfQueens);
				initData();
				initLabelState();
			}
		});
		
		cbDisabledMsg.addChangeListener(new ChangeListener() {	
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbDisabledMsg.isSelected())
					disabledMsg = true;
				else disabledMsg = false;
			}
		});
		
		btnRemoveQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pnHumanAction.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				hmBoard.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				hmBoard.setRemoving(true);
			}
		});
		
		btnClearBoard.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hmBoard.reinitBoard(numberOfQueens);
				initLabelState();
			}
		});
		
		initActionBtnCustomBoard();
		
		initActionColorSafeCell();
	}
	
	private void initActionBtnCustomBoard() {
		btnBgWood.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				updateBoardWithBackground("wood");
			}
		});
		
		btnBgBlue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBoardWithBackground("blue");
			}
		});
		
		btnBgGreen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBoardWithBackground("green");
			}
		});
		
		btnCustomBg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(null, "Chọn màu", Color.WHITE);
				if (c == Color.WHITE)
					return;
				hmBoard.setUseColorBg(true);
				hmBoard.setBgColor(c);
				hmBoard.repaint();
			}
		});
		
		btnBasicQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("basic");
				hmBoard.repaint();
			}
		});
		
		btnFlatQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("flat");
				hmBoard.repaint();
			}
		});
		
		btnAeroQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("aero");
				hmBoard.repaint();
			}
		});
	}
	
	private void updateBoardWithBackground(String name) {
		ImageUtil.reinitBackground(name);
		hmBoard.setUseColorBg(false);
		hmBoard.repaint();
	}
	
	private void initActionColorSafeCell() {
		cbShowSafeCell.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (cbShowSafeCell.isSelected()) {
					hmBoard.setHintSafeCell(true);
					lblColorSafeCell.setEnabled(true);
				} else {
					hmBoard.setHintSafeCell(false);
					lblColorSafeCell.setEnabled(false);
				}
				hmBoard.repaint();
			}
		});
		
		lblColorSafeCell.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Color c = JColorChooser.showDialog(null, "Chọn màu", lblColorSafeCell.getBackground());
				lblColorSafeCell.setBackground(c);
				hmBoard.setSafeCellColor(c);
				hmBoard.repaint();
			}
		});
	}

	public static void main(String[] args) {
		new HumanUI().setVisible(true);
	}
}
