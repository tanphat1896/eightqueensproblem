package eightqueens.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import eightqueens.algorithm.PureBacktracking;
import eightqueens.ui.panel.HumanBoard;
import eightqueens.ui.redefinecomp.MyMessageDialog;
import eightqueens.util.Config;
import eightqueens.util.GUIUtil;
import eightqueens.util.ImageUtil;
import eightqueens.util.Result;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class HumanUI extends BaseUI {
	private HumanBoard hmBoard;

	private JCheckBox cbDisabledMsg;

	private JButton btnClearBoard;
	private JButton btnCheckPossible;
	private JButton btnShowSolution;
	private JButton btnRemoveQueen;
	private JLabel lblCnLi;
	private JLabel lblQueenRemain;
	private JLabel lblNewLabel;

	public HumanUI() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				WelcomeUI.invoke();
			}
		});
		lblTitleGame.setText("Tìm kiếm lời giải cho bài toán Tám quân hậu");
		pnBoardCustomization.setLocation(555, 325);
		cboNumber.setBounds(12, 42, 208, 25);
		initBoard();
		initComponents();
		initAction();
		
	}

	@Override
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {
		updateRemainingQueen();
		lblColSolution[Config.totalQueen - row - 1].setText(valid ? letter[col] : "");
	}
	
	public void updateRemainingQueen() {
		lblQueenRemain.setText("" + hmBoard.getRemainQueen());
	}

	public void notifyFinish(Result result) {
		if (hmBoard.noQueenPlaced())
			disableButton(btnCheckPossible);
		else
			enableButton(btnCheckPossible);

		if (result == Result.FOUND) {
			changeColorLabelSolution(result);
			disableButton(btnCheckPossible);
			if (!Config.messageDisabled)
				MyMessageDialog.showDialog(this, "Đã tìm thấy một lời giải", "Tìm được lời giải", MyMessageDialog.SUCCESS);
		}

		if (result == Result.FAILED) {
			changeColorLabelSolution(result);
			disableButton(btnCheckPossible);
			if (!Config.messageDisabled)
				MyMessageDialog.showDialog(this, "Hết ô an toàn để đặt, hãy thử lại", "Thất bại", MyMessageDialog.FAILED);
		}
		changeColorLabelSolution(result);
	}

	public void notifyPutQueenAtDangerCell() {
		if (Config.isShowDangerCell)
			return;
		if (Config.messageDisabled)
			return;
		MyMessageDialog.showDialog(this, "Không được đặt cờ vào ô nguy hiểm!!!", "Thất bại", MyMessageDialog.FAILED);
	}

	private void initBoard() {
		hmBoard = new HumanBoard(this);
		hmBoard.setBounds(15, 55, hmBoard.getWidth(), hmBoard.getHeight());
		getContentPane().add(hmBoard);
	}

	private void initComponents() {
		pnBoardOperation.setLocation(555, 50);
		pnBoardOperation.setSize(230, 263);

		btnClearBoard = new JButton("<html><center>Làm mới bàn cờ</center></html>");
		GUIUtil.changeButtonAppearance(btnClearBoard);
		btnClearBoard.setBounds(12, 160, 100, 40);

		btnCheckPossible = new JButton("<html><center>Kiểm tra khả thi</center></html>");
//		changeButtonAppearance(btnCheckPossible, btnBG, btnFG);
		GUIUtil.changeButtonAppearance(btnCheckPossible);
		btnCheckPossible.setBounds(12, 113, 100, 40);
		disableButton(btnCheckPossible);

		btnShowSolution = new JButton("<html><center>Hiển thị kết quả</center></html>");
		GUIUtil.changeButtonAppearance(btnShowSolution);
		btnShowSolution.setBounds(120, 113, 100, 40);

		btnRemoveQueen = new JButton("<html><center>Xóa nhiều quân hậu</center></html>");
		GUIUtil.changeButtonAppearance(btnRemoveQueen);
		btnRemoveQueen.setBounds(120, 160, 100, 40);

		JLabel lblTip = new JLabel(
				"<html>Ghi chú: Xóa nhanh các quân hậu <br>bằng cách kéo thả chúng ra khỏi bàn cờ.</html>");
		lblTip.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTip.setBounds(12, 210, 206, 42);

		pnBoardOperation.add(btnClearBoard);
		pnBoardOperation.add(btnCheckPossible);
		pnBoardOperation.add(btnShowSolution);
		pnBoardOperation.add(btnRemoveQueen);
		pnBoardOperation.add(lblTip);
		
		/**
		 * Queen counter
		 */
		
		lblCnLi = new JLabel("Còn lại:");
		lblCnLi.setBounds(12, 80, 48, 16);
		lblCnLi.setFont(GUIUtil.MAIN_FONT);
		pnBoardOperation.add(lblCnLi);
		
		lblQueenRemain = new JLabel("8");
		lblQueenRemain.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblQueenRemain.setBounds(66, 79, 27, 16);
		pnBoardOperation.add(lblQueenRemain);
		
		lblNewLabel = new JLabel(ImageUtil.getImageIconWithSize("basic_queen.png", 32));
		lblNewLabel.setBounds(90, 70, 32, 32);
		pnBoardOperation.add(lblNewLabel);
		lblCnLi.setFont(GUIUtil.MAIN_FONT);
		
		
		
		cbDisabledMsg = new JCheckBox();
		cbDisabledMsg.setFont(GUIUtil.MAIN_FONT);
		cbDisabledMsg.setBackground(GUIUtil.MAIN_BG);
		cbDisabledMsg.setBounds(10, 197, 189, 18);
		pnBoardCustomization.add(cbDisabledMsg);
		cbDisabledMsg.setText("Tắt hết thông báo");
	}

	private void initAction() {
		cboNumber.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Config.totalQueen = Integer.parseInt(e.getItem().toString());
				hmBoard.reinitBoard(Config.totalQueen);
				initData();
				initLabelState();
				updateRemainingQueen();
			}
		});

		cbDisabledMsg.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Config.messageDisabled = cbDisabledMsg.isSelected();
			}
		});

		btnCheckPossible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// pnHumanAction.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				// hmBoard.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				// hmBoard.setRemoving(true);
				PureBacktracking pb = new PureBacktracking(Config.totalQueen);
				pb.setProvidedQueens(hmBoard.getPlacedQueens());
				if (pb.isPossibleWithSolution())
					MyMessageDialog.showDialog(null, "Có lời giải với hướng đi này!!", "Khả thi", MyMessageDialog.SUCCESS);
		
				else
					MyMessageDialog.showDialog(null, "Không có lời giải nào với hướng đi này!!", "Không khả thi", MyMessageDialog.FAILED);
			}
		});

		btnShowSolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PureBacktracking pb = new PureBacktracking(Config.totalQueen);
				pb.setProvidedQueens(hmBoard.getPlacedQueens());
				if (pb.isPossibleWithSolution()) {
					hmBoard.setTotalQueenPlaced(Config.totalQueen);
					hmBoard.setPlacedQueens(pb.getSolution());
					disableButton(btnCheckPossible);
					hmBoard.repaint();
					notifyFinish(Result.FOUND);
				} else
					MyMessageDialog.showDialog(null, "Không có lời giải nào thỏa mãn cách đặt cờ này!!", "Không tìm thấy", MyMessageDialog.FAILED);
			}
		});

		btnRemoveQueen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (hmBoard.isRemoving())
					stopRemoveQueen();
				else startRemoveQueen();
			}
		});

		btnClearBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopRemoveQueen();
				hmBoard.setTotalQueenPlaced(0);
				updateRemainingQueen();
				hmBoard.reinitBoard(Config.totalQueen);
				initLabelState();
				disableButton(btnCheckPossible);
			}
		});

		initActionBtnCustomBoard();

		initActionColorSafeCell();
	}

	public void startRemoveQueen() {
		if (hmBoard.noQueenPlaced())
			return;
		hmBoard.setRemoving(true);
		btnRemoveQueen.setText("Kết thúc xóa");
	}
	
	public void stopRemoveQueen() {
		hmBoard.setRemoving(false);
		btnRemoveQueen.setText("<html><center>Xóa nhiều quân hậu</center></html>");
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

		cbShowDangerCell.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Config.isShowDangerCell = cbShowDangerCell.isSelected();
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
