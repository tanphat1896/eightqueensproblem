package eightqueens.ui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JLabel;

import eightqueens.ui.HumanUI;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;
import eightqueens.util.Result;
import java.awt.Font;

@SuppressWarnings("serial")
public class HumanBoard extends BaseBoard {
	private HumanUI hmUI;

	private boolean isRemoving = false;

	/**
	 * Movement
	 */
	private boolean isDragging = false;
	private Position oldPos;
	private Point draggingPoint;
	private int distanceX;
	private int distanceY;

	/**
	 * UI Component
	 * 
	 * @param hmUI
	 */
	private JLabel lblRemoveQueen;

	/**
	 * @wbp.parser.constructor
	 */
	public HumanBoard(HumanUI hmUI) {
		super();
		this.hmUI = hmUI;
		initLabelRemoveQueen();
		initAction();
	}

	public HumanBoard(HumanUI hmUI, int totalQueen) {
		super(totalQueen);
		this.hmUI = hmUI;
		initLabelRemoveQueen();
		initAction();
	}

	private void checkDangerCell() {
		for (int row = 0; row < numberOfQueens; row++) {
			int col = placedQueens[row];
			if (col < 0)
				continue;
			for (int i = 0; i < numberOfQueens; i++) {
				if (!painted[i][col]) {
					if (!hasQueen(i, col)) {
						painted[i][col] = true;
					}
				}
				if (!painted[row][i]) {
					if (!hasQueen(row, i)) {
						painted[row][i] = true;
					}
				}
				if (row + i < numberOfQueens) {
					if (col + i < numberOfQueens)
						if (!painted[row + i][col + i] && !hasQueen(row + i, col + i)) {
							painted[row + i][col + i] = true;

						}
					if (col - i >= 0)
						if (!painted[row + i][col - i] && !hasQueen(row + i, col - i)) {
							painted[row + i][col - i] = true;
						}
				}
				if (row - i >= 0) {
					if (col + i < numberOfQueens)
						if (!painted[row - i][col + i] && !hasQueen(row - i, col + i)) {
							painted[row - i][col + i] = true;
						}
					if (col - i >= 0)
						if (!painted[row - i][col - i] && !hasQueen(row - i, col - i)) {
							painted[row - i][col - i] = true;
						}
				}
			}
		}
	}

	public boolean noSafeCellLeft() {
		for (int i = 0; i < numberOfQueens; i++) {
			for (int j = 0; j < numberOfQueens; j++) {
				if (!painted[i][j])
					if (placedQueens[i] < 0)
						return false;
			}
		}
		return true;
	}

	public boolean allQueensPlaced() {
		for (int i = 0; i < numberOfQueens; i++)
			if (placedQueens[i] < 0)
				return false;
		return true;
	}

	public void checkFinish() {
		checkDangerCell();
		if (allQueensPlaced()) {
			hmUI.notifyFinish(Result.FOUND);
			return;
		}
		if (noSafeCellLeft() && !allQueensPlaced()) {
			hmUI.notifyFinish(Result.FAILED);
			return;
		}
		hmUI.notifyFinish(Result.NOTFOUND);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawDraggingQueen(g);
	}

	private void calculateDistance(Point p1, Point p2) {
		distanceX = Math.abs(p1.x - p2.x);
		distanceY = Math.abs(p1.y - p2.y);
	}

	private void clearDragging() {
		lblRemoveQueen.setVisible(false);
		oldPos = null;
		distanceX = distanceY = 0;
		isDragging = false;
	}

	/**
	 * Event
	 */
	private void handleMousePressed(Point p) {
		if (p.getX() >= boardBoundary || p.getY() >= boardBoundary)
			return;
		Position pos = new Position(p, sizeOfCell);
		if (isRemoving) {
			handleRemoveQueen(pos);
			return;
		}
		if (!hasQueen(pos)) {
			if (!isSafe(pos)) {
				hmUI.notifyPutQueenAtDangerCell();
				return;
			}
			placeQueen(pos);
			hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), true);
		} else {
			lblRemoveQueen.setVisible(true);
			oldPos = pos;
			calculateDistance(pos.toPoint(sizeOfCell), p);
			removeQueen(pos);
			isDragging = true;
			draggingPoint = pos.toPoint(sizeOfCell);
			hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), false);
			// System.out.println(sizeOfCell + ", " + distanceX + ", " + distanceY);
		}
		repaint();
		checkFinish();
	}

	private void handleRemoveQueen(Position pos) {
		if (!hasQueen(pos))
			return;
		removeQueen(pos);
		repaint();
		hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), false);
		hmUI.notifyFinish(Result.NOTFOUND);
		if (noQueenPlaced()) {
			hmUI.stopRemoveQueen();
		}
	}

	private void handleMouseReleased(Point p) {
		if (!isDragging)
			return;
		releaseDraggingQueen(p);
		repaint();
		checkFinish();
	}

	private void releaseDraggingQueen(Point p) {
		if (p.getX() < boardBoundary && p.getY() < boardBoundary && p.getX() >= 0 && p.getY() >= 0) {
			Position pos = new Position(p, sizeOfCell);
			if (isSafe(pos)) {
				placeQueen(pos);
				hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), true);
			} else {
				placeQueen(oldPos);
				hmUI.updateCurrentBoardStateInUI(oldPos.getRow(), oldPos.getCol(), true);
			}
		}

		/**
		 * Thay vì đặt về ô cũ, kéo ra khỏi biên == xóa quân cờ đó
		 */
		// else {
		// placeQueen(oldPos);
		// hmUI.updateCurrentBoardStateInUI(oldPos.getRow(), oldPos.getCol(), true);
		// }
		clearDragging();
	}

	private void handleMouseDragged(Point p) {
		if (!isDragging)
			return;
		draggingPoint = new Point(p.x - distanceX, p.y - distanceY);
		repaint();
	}

	private void initLabelRemoveQueen() {
		lblRemoveQueen = new JLabel("Kéo quân hậu ra khỏi bàn cờ để xóa nó!");
		lblRemoveQueen.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblRemoveQueen.setVisible(false);
		lblRemoveQueen.setHorizontalAlignment(JLabel.CENTER);
		lblRemoveQueen.setForeground(Color.RED);
		lblRemoveQueen.setBounds(10, EDGE_SIZE + GAP - 20, 520, 20);
		this.add(lblRemoveQueen);
	}

	private void initAction() {
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// borderedPos = new Position(e.getPoint(), sizeOfCell);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				handleMouseReleased(e.getPoint());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				handleMousePressed(e.getPoint());
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				handleMouseDragged(e.getPoint());
			}
		});
	}

	/**
	 * Paint method
	 */

	private void drawDraggingQueen(Graphics g) {
		if (!isDragging)
			return;
		super.drawImage(g, draggingPoint, ImageUtil.queen);
	}

	/**
	 * Setter & getter
	 */

	public void setRemoving(boolean isRemoving) {
		this.isRemoving = isRemoving;
	}

	public boolean isRemoving() {
		return isRemoving;
	}
	
	public void setPlacedQueens(int[] placedQueens) {
		this.placedQueens = placedQueens;
		for (int i = 0; i < numberOfQueens; i++)
			hmUI.updateCurrentBoardStateInUI(i, placedQueens[i], true);
	}
}
