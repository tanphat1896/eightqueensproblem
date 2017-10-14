package eightqueens.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import eightqueens.algorithm.NonRecursiveBacktracking;
import eightqueens.algorithm.ProcessPolling;
import eightqueens.algorithm.WriteSolutionToFile;
import eightqueens.ui.dialog.MyMessageDialog;
import eightqueens.ui.panel.CompBoard;
import eightqueens.util.Config;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;

import javax.swing.JSlider;

@SuppressWarnings("serial")
public class ComputerUI extends BaseUI {
	private NonRecursiveBacktracking backtrack;
	private ProcessPolling polling;

	public ComputerUI() {
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				stopSolve();
				WelcomeUI.invoke();
			}
		});
		initBoard();
		initComponents();
		initAction();
		polling = new ProcessPolling(this);
	}

	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {
		if (col != -1)
    		compBoard.setInspectionCell(new Position(row, col), valid);
		compBoard.repaint();
		lblColSolution[Config.totalQueen - row - 1].setText(valid ? letter[col] : "");
	};

	public void notifyFoundASolution(int idx, int total) {
		disableButton(btnPause);
		enableButton(btnNextSolution);
        WriteSolutionToFile.writeToFile("loi_giai_" + Config.totalQueen + "_hau.txt", compBoard.getPlacedQueens(), idx);
		MyMessageDialog.showDialog(this, "Tìm được lời giải thứ " + idx + (total == 0 ? "": " trong " + total + " lời giải"), 
				"Thông báo", 
				MyMessageDialog.SUCCESS);
	}
	
	public void notifyFoundAllSolution() {
		disableButton(btnNextSolution);
		stopSolve();
		MyMessageDialog.showDialog(this, "Đã tìm xong tất cả các lời giải", "Thông báo", MyMessageDialog.SUCCESS);
	}
	
	private void beginSolve() {
		toggleControl(true);
		prepareSolvingData();
		WriteSolutionToFile.destroy("loi_giai_" + Config.totalQueen + "_hau.txt");
		polling.beginSolving();
		backtrack.start();
	}

	private void prepareSolvingData() {
		compBoard.reinitBoard(Config.totalQueen);
		if (backtrack != null) {
			backtrack.cleanThreadData();
			backtrack.interrupt();
			backtrack = null;
			polling.interrupt();
			polling = null;
		}
		polling = new ProcessPolling(this);
		backtrack = new NonRecursiveBacktracking(compBoard, polling);
	}

	private void stopSolve() {
		if (polling.isSolving()) {
			backtrack.terminate();
			polling.init();
		}
		toggleControl(false);
	}

	private void pauseAndResumeSolving(boolean paused) {
		if (!polling.isSolving())
			return;
		if (paused) {
			polling.pauseSolving();
			enableButton(btnResume);
			disableButton(btnPause);
		} else {
			polling.resumeSolving();
			disableButton(btnResume);
			enableButton(btnPause);
			synchronized (backtrack) {
				backtrack.notify();
			}
		}
	}

	@SuppressWarnings("unused")
	private void reinitForNewSolving() {
		compBoard.reinitBoard(Config.totalQueen);
		initData();
		initLabelState();
	}

	private void toggleControl(boolean isSolving) {
		if (isSolving) {
			disableButton(btnSolve);
			disableButton(btnClearBoard);
			enableButton(btnStop);
			enableButton(btnPause);
			cboNumber.setEnabled(false);
		} else {
			enableButton(btnSolve);
			enableButton(btnClearBoard);
			disableButton(btnStop);
			disableButton(btnPause);
			disableButton(btnResume);
			disableButton(btnNextSolution);
			cboNumber.setEnabled(true);
		}
	}

	/**
	 * Component
	 */

	private CompBoard compBoard;

	private JButton btnClearBoard;
	private JButton btnSolve;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnResume;
	private JButton btnNextSolution;

	private JLabel lblDelay;
	private JLabel lblDelayValue;
	private JSlider sliderDelay;
	private JTextField tfDelay;
	private JButton btnChangeInputDelay;
	private JCheckBox cbShowInspectedCell;

	private void initBoard() {
		compBoard = new CompBoard(this);
		compBoard.setBounds(15, 55, compBoard.getWidth(), compBoard.getHeight());
		getContentPane().add(compBoard);
	}

	private void initComponents() {
		lblTitleGame.setText("Giải bài toán Tám quân hậu bằng giải thuật Quay lui");
		pnBoardOperation.setBounds(550, 50, 230, 272);
		pnBoardCustomization.setBounds(550, 334, 230, 230);

		initTimer();

		btnClearBoard = new JButton("Làm mới bàn cờ");
		changeButtonAppearance(btnClearBoard, btnBG, btnFG);
		btnClearBoard.setBounds(12, 231, 206, 28);

		btnSolve = new JButton("Bắt đầu");
		changeButtonAppearance(btnSolve, btnBG, btnFG);
		btnSolve.setBounds(12, 132, 100, 28);

		btnStop = new JButton("Kết thúc");
		changeButtonAppearance(btnStop, btnBG, btnFG);
		btnStop.setBounds(118, 132, 100, 28);

		btnPause = new JButton("Tạm dừng");
		changeButtonAppearance(btnPause, btnBG, btnFG);
		btnPause.setBounds(12, 165, 100, 28);

		btnResume = new JButton("Tiếp tục");
		changeButtonAppearance(btnResume, btnBG, btnFG);
		btnResume.setBounds(118, 165, 100, 28);

		btnNextSolution = new JButton("Lời giải tiếp theo");
		changeButtonAppearance(btnNextSolution, btnBG, btnFG);
		btnNextSolution.setBounds(12, 198, 206, 28);

		pnBoardOperation.add(btnClearBoard);
		pnBoardOperation.add(btnSolve);
		pnBoardOperation.add(btnStop);
		pnBoardOperation.add(btnPause);
		pnBoardOperation.add(btnResume);
		pnBoardOperation.add(btnNextSolution);

		disableButton(btnStop);
		disableButton(btnNextSolution);
		disableButton(btnPause);
		disableButton(btnResume);
		
		
		cbShowInspectedCell = new JCheckBox("Đánh dấu ô đang kiểm tra");
		cbShowInspectedCell.setFont(COMMON_FONT);
		cbShowInspectedCell.setBounds(10, 197, 202, 21);
		pnBoardCustomization.add(cbShowInspectedCell);
	}

	private void initTimer() {
		lblDelay = new JLabel("Tốc độ tìm (mili giây):");
		lblDelay.setFont(COMMON_FONT);
		lblDelay.setBounds(12, 75, 138, 15);

		lblDelayValue = new JLabel("100");
		lblDelayValue.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblDelayValue.setBounds(146, 75, 39, 15);

		btnChangeInputDelay = new JButton(ImageUtil.getImageIconWithSize("reload.png", 15));
		btnChangeInputDelay.setBorder(null);
		btnChangeInputDelay.setContentAreaFilled(false);
		btnChangeInputDelay.setBorderPainted(false);

		btnChangeInputDelay.setBounds(203, 75, 15, 15);

		pnBoardOperation.add(lblDelay);
		pnBoardOperation.add(lblDelayValue);
		pnBoardOperation.add(btnChangeInputDelay);

		sliderDelay = new JSlider();
		sliderDelay.setFont(new Font("Dialog", Font.PLAIN, 8));
		sliderDelay.setMajorTickSpacing(1);
		sliderDelay.setValue(100);
		sliderDelay.setMaximum(500);
		sliderDelay.setBounds(5, 95, 220, 25);

		tfDelay = new JTextField("100");
		tfDelay.setFont(COMMON_FONT);
		tfDelay.setVisible(false);
		tfDelay.setBounds(12, 95, 206, 25);

		pnBoardOperation.add(tfDelay);
		pnBoardOperation.add(sliderDelay);

	}

	private void initAction() {
		cboNumber.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Config.totalQueen = Integer.parseInt(e.getItem().toString());
				compBoard.reinitBoard(Config.totalQueen);
				initData();
				initLabelState();
			}
		});

		btnClearBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compBoard.reinitBoard(Config.totalQueen);
				initLabelState();
			}
		});

		initActionBtnControl();

		initActionTimer();

		initActionBtnCustomBoard();

		initActionColorSafeCell();
	}

	private void initActionTimer() {
		btnChangeInputDelay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Config.isUseSliderTimer = !Config.isUseSliderTimer;
				sliderDelay.setVisible(Config.isUseSliderTimer);
				tfDelay.setVisible(!Config.isUseSliderTimer);
			}
		});

		sliderDelay.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				lblDelayValue.setText(sliderDelay.getValue() + "");
				Config.solveSpeed = sliderDelay.getValue();
			}
		});

		tfDelay.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = tfDelay.getText();
				if (txt.length() >= 1 && txt.matches("^[0-9]{0,4}$")) {
					Config.solveSpeed = Integer.parseInt(txt);
					lblDelayValue.setText(txt);
				} else {
					Config.solveSpeed = 100;
					lblDelayValue.setText("100");
				}
			}
		});
	}

	private void initActionBtnControl() {
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				beginSolve();
			}
		});

		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopSolve();
			}
		});

		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseAndResumeSolving(true);
			}
		});
		
		btnResume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseAndResumeSolving(false);
			}
		});

		btnNextSolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pauseAndResumeSolving(false);
			}
		});
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
				compBoard.setUseColorBg(true);
				compBoard.setBgColor(c);
				compBoard.repaint();
			}
		});

		btnBasicQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("basic");
				compBoard.repaint();
			}
		});

		btnFlatQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("flat");
				compBoard.repaint();
			}
		});

		btnAeroQueen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageUtil.reinitQueen("aero");
				compBoard.repaint();
			}
		});

		cbShowDangerCell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Config.isShowDangerCell = cbShowDangerCell.isSelected();
				compBoard.repaint();
			}
		});
		
		cbShowInspectedCell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Config.isShowInspectedCell = cbShowInspectedCell.isSelected();
				compBoard.repaint();
			}
		});
	}

	private void updateBoardWithBackground(String name) {
		ImageUtil.reinitBackground(name);
		compBoard.setUseColorBg(false);
		compBoard.repaint();
	}

	private void initActionColorSafeCell() {
		cbShowSafeCell.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbShowSafeCell.isSelected()) {
					compBoard.setHintSafeCell(true);
					lblColorSafeCell.setEnabled(true);
				} else {
					compBoard.setHintSafeCell(false);
					lblColorSafeCell.setEnabled(false);
				}
				compBoard.repaint();		
			}
		});

		lblColorSafeCell.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Color c = JColorChooser.showDialog(null, "Chọn màu", lblColorSafeCell.getBackground());
				lblColorSafeCell.setBackground(c);
				compBoard.setSafeCellColor(c);
				compBoard.repaint();
			}
		});
	}

	public static void main(String[] args) {
		new ComputerUI().setVisible(true);
	}
}
