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
import eightqueens.ui.dialog.MyMessageDialog;
import eightqueens.ui.panel.HumanBoard;
import eightqueens.util.Config;
import eightqueens.util.ImageUtil;
import eightqueens.util.Result;
import java.awt.Font;

@SuppressWarnings("serial")
public class HumanUI extends BaseUI {
	private HumanBoard hmBoard;

	private JCheckBox cbDisabledMsg;

	private JButton btnClearBoard;
	private JButton btnCheckPossible;
	private JButton btnShowSolution;
	private JButton btnRemoveQueen;

	public HumanUI() {
		super();
		lblTitleGame.setText("Tìm kiếm lời giải cho bài toán Tám quân hậu");
		pnBoardCustomization.setLocation(555, 316);
		cboNumber.setBounds(12, 42, 145, 25);
		initBoard();
		initComponents();
		initAction();
	}

	@Override
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {
		lblColSolution[Config.totalQueen - row - 1].setText(valid ? letter[col] : "");
	}

	public void notifyFinish(Result result) {
		if (hmBoard.noQueenPlaced())
			disableButton(btnCheckPossible);
		else
			enableButton(btnCheckPossible);

		if (result == Result.FOUND) {
			changeLabelSolutionAppearance(result);
			disableButton(btnCheckPossible);
			if (!Config.messageDisabled)
				MyMessageDialog.showDialog(this, "Đã tìm thấy một lời giải", "Tìm được lời giải", MyMessageDialog.SUCCESS);
		}

		if (result == Result.FAILED) {
			changeLabelSolutionAppearance(result);
			disableButton(btnCheckPossible);
			if (!Config.messageDisabled)
				MyMessageDialog.showDialog(this, "Hết ô an toàn để đặt, hãy thử lại", "Thất bại", MyMessageDialog.FAILED);
		}
		changeLabelSolutionAppearance(result);
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
		pnBoardOperation.setSize(230, 254);

		btnClearBoard = new JButton("Làm mới bàn cờ");
		changeButtonAppearance(btnClearBoard, btnBG, btnFG);
		btnClearBoard.setBounds(12, 75, 145, 25);

		btnCheckPossible = new JButton("Kiểm tra khả thi");
		changeButtonAppearance(btnCheckPossible, btnBG, btnFG);
		btnCheckPossible.setBounds(12, 106, 145, 25);
		disableButton(btnCheckPossible);

		btnShowSolution = new JButton("Hiển thị kết quả");
		changeButtonAppearance(btnShowSolution, btnBG, btnFG);
		btnShowSolution.setBounds(12, 137, 145, 25);

		btnRemoveQueen = new JButton("Xóa nhiều quân hậu");
		changeButtonAppearance(btnRemoveQueen, btnBG, btnFG);
		btnRemoveQueen.setBounds(12, 168, 145, 25);

		JLabel lblTip = new JLabel(
				"<html>Ghi chú: Xóa nhanh các quân hậu <br>bằng cách kéo thả chúng ra khỏi bàn cờ.</html>");
		lblTip.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblTip.setBounds(12, 200, 206, 42);

		pnBoardOperation.add(btnClearBoard);
		pnBoardOperation.add(btnCheckPossible);
		pnBoardOperation.add(btnShowSolution);
		pnBoardOperation.add(btnRemoveQueen);
		pnBoardOperation.add(lblTip);

		cbDisabledMsg = new JCheckBox();
		cbDisabledMsg.setFont(COMMON_FONT);
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
				pb.setCompareSolution(hmBoard.getPlacedQueens());
				if (pb.isPossibleWithSolution())
					MyMessageDialog.showDialog(null, "Có lời giải với hướng đi này!!", "Khả thi", MyMessageDialog.SUCCESS);
		
				else
					MyMessageDialog.showDialog(null, "Không có lời giải nào!!", "Không khả thi", MyMessageDialog.FAILED);
			}
		});

		btnShowSolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PureBacktracking pb = new PureBacktracking(Config.totalQueen);
				pb.setCompareSolution(hmBoard.getPlacedQueens());
				if (pb.isPossibleWithSolution()) {
					hmBoard.setPlacedQueens(pb.getSolution());
					disableButton(btnCheckPossible);
					hmBoard.repaint();
					notifyFinish(Result.FOUND);
				} else
					MyMessageDialog.showDialog(null, "Không có lời giải nào!!", "Không khả thi", MyMessageDialog.FAILED);
			}
		});

		btnRemoveQueen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startRemoveQueen();
			}
		});

		btnClearBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hmBoard.reinitBoard(Config.totalQueen);
				initLabelState();
				disableButton(btnCheckPossible);
			}
		});

		initActionBtnCustomBoard();

		initActionColorSafeCell();
	}

	public void startRemoveQueen() {
		hmBoard.setRemoving(true);
		btnRemoveQueen.setText("Kết thúc xóa");
	}
	
	public void stopRemoveQueen() {
		hmBoard.setRemoving(false);
		btnRemoveQueen.setText("Xóa nhiều quân hậu");
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
