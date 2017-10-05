package eightqueens.ui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import eightqueens.ui.panel.HumanBoard;
import eightqueens.util.Position;
import eightqueens.util.Result;

@SuppressWarnings("serial")
public class HumanUI extends BaseUI {
	private HumanBoard hmBoard;

	public HumanUI() {
		super();
		initBoard();
		initAction();
	}

	private void initBoard() {
		hmBoard = new HumanBoard(this);
		hmBoard.setBounds(5, 5, hmBoard.getWidth(), hmBoard.getHeight());
		getContentPane().add(hmBoard);
	}

	private void initAction() {
		cboNumber.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				numberOfQueens = Integer.parseInt(e.getItem().toString());
				hmBoard.reinitBoard(numberOfQueens);
				initLabelState();
			}
		});
	}

	@Override
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {
        lblColSolution[row].setText(valid ? letter[col] : "");
	}
	
	
	public void notifyFinish(Result result) {
		if (result == Result.FOUND) {
			changeLabelSolutionAppearance(result);
			JOptionPane.showMessageDialog(this, "Đã tìm thấy lời giải!");
		}
		if (result == Result.FAILED) {
			changeLabelSolutionAppearance(result);
			JOptionPane.showMessageDialog(this, "Đã hết ô để đặt cờ, hãy thử lại!");
		}
	}

	public static void main(String[] args) {
		new HumanUI().setVisible(true);
	}
}
