package eightqueens.ui.panel;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import eightqueens.ui.HumanUI;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;
import eightqueens.util.Result;

@SuppressWarnings("serial")
public class HumanBoard extends BaseBoard {
	private HumanUI hmUI;
	/**
	 * Movement
	 */
	private boolean isDragging = false;
	private Position oldPos;
	private Point draggingPoint;
	private int distanceX;
	private int distanceY;

	public HumanBoard(HumanUI hmUI) {
		super();
		this.hmUI = hmUI;
		initAction();
	}

	public HumanBoard(HumanUI hmUI, int totalQueen) {
		super(totalQueen);
		this.hmUI = hmUI;
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
		if (allQueensPlaced())
			hmUI.notifyFinish(Result.FOUND);
		if (noSafeCellLeft() && !allQueensPlaced())
			hmUI.notifyFinish(Result.FAILED);
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
		if (!hasQueen(pos)) {
			if (!isSafe(pos))
				return;
			placeQueen(pos);
			hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), true);
		} else {
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

	private void handleMouseReleased(Point p) {
		if (!isDragging)
			return;
		if (p.getX() < boardBoundary && p.getY() < boardBoundary) {
			Position pos = new Position(p, sizeOfCell);
			if (isSafe(pos)) {
				placeQueen(pos);
				hmUI.updateCurrentBoardStateInUI(pos.getRow(), pos.getCol(), true);
			} else {
				placeQueen(oldPos);
				hmUI.updateCurrentBoardStateInUI(oldPos.getRow(), oldPos.getCol(), true);
			}
		} else {
			placeQueen(oldPos);
			hmUI.updateCurrentBoardStateInUI(oldPos.getRow(), oldPos.getCol(), true);
		}
		clearDragging();
		repaint();
		checkFinish();
	}

	private void handleMouseDragged(Point p) {
		if (!isDragging)
			return;
		draggingPoint = new Point(p.x - distanceX, p.y - distanceY);
		repaint();
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
}
