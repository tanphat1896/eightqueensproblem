package eightqueens.ui.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.JPanel;

import eightqueens.algorithm.ProcessPolling;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;

@SuppressWarnings("serial")
public class Board extends JPanel {
	/**
	 * Board config
	 */
	public static final int EDGE_SIZE = 500;
	public static final int GAP = 40;
	private static final Color TEXT_COLOR = Color.BLACK;
	private static final Color LINE_COLOR = Color.GRAY;
	private static final Color SAFE_COLOR = new Color(170, 194, 237);
//	private static final Color SAFE_COLOR = new Color(165, 255, 43);

	/**
	 * Board data
	 */
	private ProcessPolling polling;
	private int sizeOfCell;
	private int boardBoundary;
	private int numberOfQueens = 8;
	private boolean[][] painted;
	private int[] placedQueens;

	/**
	 * Queens movement
	 */
//	private Position borderedPos;
	private Position oldPosition;

	private Position inspectionCell;
	private boolean validCell;

	private Point draggingPoint;
	private boolean isDragging;
//	private boolean isMoving;
	private int distanceX;
	private int distanceY;

	public Board() {
		// TODO Auto-generated constructor stub
		initUI();
		initAction();
		initData();
	}

	public Board(int numOfQueens) {
		// TODO Auto-generated constructor stub
        this.numberOfQueens = numOfQueens;
		initUI();
		initAction();
		initData();
		
	}

	private void initUI() {
		this.setSize(EDGE_SIZE + GAP, EDGE_SIZE + GAP);
	}

	private void initAction() {
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
//				borderedPos = new Position(e.getPoint(), sizeOfCell);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				handleMouseReleased(arg0.getPoint());
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				handleMousePressed(arg0.getPoint());
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				handleMouseDragged(e.getPoint());
			}
		});
	}

	public void reinitBoard(int numOfQueen) {
		this.numberOfQueens = numOfQueen;
		initData();
		this.repaint();
	}

	/**
	 * Init data of board
	 */
	private void initData() {
		sizeOfCell = calculateSizeOfCell();
		boardBoundary = sizeOfCell * numberOfQueens;
		ImageUtil.initImage(sizeOfCell - 1);
		painted = new boolean[numberOfQueens][numberOfQueens];
		placedQueens = new int[numberOfQueens];
		for (int i = 0; i < numberOfQueens; i++) {
			placedQueens[i] = -1;
		}
		inspectionCell = null;
	}

    private int calculateSizeOfCell() {
        return numberOfQueens > 0 ? (int) (EDGE_SIZE / numberOfQueens) : 0;
    }

    /**
     * Outer call
     */

    public void placedQueenAt(int row, int col) {
        this.placedQueens[row] = col;
    }

    public int getLastPlacedCol(){
        int i = numberOfQueens-1;
        while (placedQueens[i--] == -1)
            if (i < 0) break;
        return placedQueens[i+1];
    }

    public void setInspectionCell(Position inspectionCell, boolean validCell) {
        this.inspectionCell = inspectionCell;
        this.validCell = validCell;
    }

    /**
	 * Event
	 */
	private void handleMousePressed(Point p) {
		if (polling.isSolving())
			return;
		if (p.getX() >= boardBoundary || p.getY() >= boardBoundary)
			return;
		Position pos = new Position(p, sizeOfCell);
		if (!hasQueen(pos)) {
			if (!isSafe(pos))
				return;
			placeQueen(pos);
		} else {
			oldPosition = pos;
			calculateDistance(pos.toPoint(sizeOfCell), p);
			removeQueen(pos);
			isDragging = true;
			draggingPoint = pos.toPoint(sizeOfCell);
			// System.out.println(sizeOfCell + ", " + distanceX + ", " + distanceY);
		}
		repaint();
	}

	private void handleMouseReleased(Point p) {
		if (!isDragging)
			return;
		if (p.getX() < boardBoundary && p.getY() < boardBoundary) {
			Position pos = new Position(p, sizeOfCell);
			if (isSafe(pos)) {
				placeQueen(pos);
			} else
				placeQueen(oldPosition);
		} else {
			placeQueen(oldPosition);
		}
		clearDragging();
		repaint();
	}

	private void handleMouseDragged(Point p) {
		if (!isDragging)
			return;
		draggingPoint = new Point(p.x - distanceX, p.y - distanceY);
		repaint();
	}

	private void clearDragging() {
		oldPosition = null;
		distanceX = distanceY = 0;
		isDragging = false;

	}

	private void calculateDistance(Point p1, Point p2) {
		distanceX = Math.abs(p1.x - p2.x);
		distanceY = Math.abs(p1.y - p2.y);
	}

	private boolean isSafe(Position pos) {
		return !hasQueen(pos) && !painted[pos.getRow()][pos.getCol()];
	}

	private boolean hasQueen(Position pos) {
		return placedQueens[pos.getRow()] == pos.getCol();
	}

	private boolean hasQueen(int row, int col) {
		return placedQueens[row] == col;
	}

	private void placeQueen(Position pos) {
		placedQueens[pos.getRow()] = pos.getCol();
	}

	private void removeQueen(Position pos) {
		placedQueens[pos.getRow()] = -1;
	}

	/**
	 * Paint Methods
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		putQueens(g);
        drawInspectionCell(g);
		drawDraggingQueen(g);
	}

	private void drawBoard(Graphics g) {
		drawGrid(g);
		drawBackground(g);
		drawIndex(g);
	}

	private void drawGrid(Graphics g) {
		g.setColor(LINE_COLOR);
		// loáº¡i bá»� GAP náº¿u muá»‘n Ä‘Ã¡nh sá»‘ bÃªn pháº£i

		// for (int i = 0; i < numberOfQueens; i++) {
		// g.drawLine(GAP, i*sizeOfCell, boardBoundary + GAP, i*sizeOfCell);
		// g.drawLine(i*sizeOfCell + GAP, 0, i*sizeOfCell + GAP, boardBoundary);
		// }
		// g.drawLine(boardBoundary + GAP + 1, 0, boardBoundary + GAP + 1, boardBoundary
		// + 1);
		// g.drawLine(GAP, boardBoundary + 1, boardBoundary + GAP + 1, boardBoundary +
		// 1);
		for (int i = 0; i < numberOfQueens; i++) {
			g.drawLine(0, i * sizeOfCell, boardBoundary, i * sizeOfCell);
			g.drawLine(i * sizeOfCell, 0, i * sizeOfCell, boardBoundary);
		}
		g.drawLine(boardBoundary, 0, boardBoundary, boardBoundary);
		g.drawLine(0, boardBoundary, boardBoundary, boardBoundary);
	}

	private void drawBackground(Graphics g) {
		for (int i = 0; i < numberOfQueens; i++)
			for (int j = 0; j < numberOfQueens; j++) {
				if ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) {
					g.drawImage(ImageUtil.darkBG, i * sizeOfCell + 1, j * sizeOfCell + 1, null);
					// g.drawImage(ImageUtil.darkBG, i*sizeOfCell + GAP + 1, j*sizeOfCell+1, null);
				}
			}
	}

	private void drawIndex(Graphics g) {
		g.setColor(TEXT_COLOR);
		g.setFont(new Font("Consolas", Font.PLAIN, 15));

		// fix ugly font
		Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
		Graphics2D g2d = (Graphics2D) g;
		if (desktopHints != null) {
			g2d.setRenderingHints(desktopHints);
		}

		// g.drawString("A", sizeOfCell/2 - 5 + GAP, this.getHeight() - 10);
		// g.drawString("1", 10, (numberOfQueens-1)*sizeOfCell + sizeOfCell/2 + 5);
		//
		// for (int i = 1; i < numberOfQueens; i++) {
		// g.drawString((char)(65 + i) + "", i*sizeOfCell + sizeOfCell/2 - 5 + GAP,
		// this.getHeight() - 10);
		// g.drawString("" + (i+1), 10, (numberOfQueens-i-1)*sizeOfCell + sizeOfCell/2 +
		// 5);
		// }
		g.drawString("A", sizeOfCell / 2 - 5, this.getHeight() - 20);
		g.drawString("1", boardBoundary + 20, (numberOfQueens - 1) * sizeOfCell + sizeOfCell / 2 + 5);

		for (int i = 1; i < numberOfQueens; i++) {
			g.drawString((char) (65 + i) + "", i * sizeOfCell + sizeOfCell / 2 - 5, this.getHeight() - 20);
			g.drawString("" + (i + 1), boardBoundary + 20, (numberOfQueens - i - 1) * sizeOfCell + sizeOfCell / 2 + 5);
		}
	}

	protected void drawImage(Graphics g, Point p, Image img) {
		g.drawImage(img, p.x + 1, p.y + 1, null);
	}

	private void drawDangerArea(Graphics g) {
		for (int row = 0; row < numberOfQueens; row++) {
			int col = placedQueens[row];
			if (col < 0)
				continue;
			for (int i = 0; i < numberOfQueens; i++) {
				if (!painted[i][col]) {
					if (!hasQueen(i, col)) {
						painted[i][col] = true;
						drawImage(g, new Position(i, col).toPoint(sizeOfCell), ImageUtil.danger);
					}
				}
				if (!painted[row][i]) {
					if (!hasQueen(row, i)) {
						painted[row][i] = true;
						drawImage(g, new Position(row, i).toPoint(sizeOfCell), ImageUtil.danger);
					}
				}
				if (row + i < numberOfQueens) {
					if (col + i < numberOfQueens)
						if (!painted[row + i][col + i] && !hasQueen(row + i, col + i)) {
							painted[row + i][col + i] = true;
							drawImage(g, new Position(row + i, col + i).toPoint(sizeOfCell), ImageUtil.danger);

						}
					if (col - i >= 0)
						if (!painted[row + i][col - i] && !hasQueen(row + i, col - i)) {
							painted[row + i][col - i] = true;
							drawImage(g, new Position(row + i, col - i).toPoint(sizeOfCell), ImageUtil.danger);
						}
				}
				if (row - i >= 0) {
					if (col + i < numberOfQueens)
						if (!painted[row - i][col + i] && !hasQueen(row - i, col + i)) {
							painted[row - i][col + i] = true;
							drawImage(g, new Position(row - i, col + i).toPoint(sizeOfCell), ImageUtil.danger);
						}
					if (col - i >= 0)
						if (!painted[row - i][col - i] && !hasQueen(row - i, col - i)) {
							painted[row - i][col - i] = true;
							drawImage(g, new Position(row - i, col - i).toPoint(sizeOfCell), ImageUtil.danger);
						}
				}
			}
		}
	}

	private void drawSafeArea(Graphics g) {
		g.setColor(SAFE_COLOR);
		for (int row = 0; row < numberOfQueens; row++)
			for (int col = 0; col < numberOfQueens; col++)
				if (!painted[row][col] && !hasQueen(row, col))
					g.fillRect(col * sizeOfCell + 1, row * sizeOfCell + 1, sizeOfCell - 1, sizeOfCell - 1);
	}

	private void putQueens(Graphics g) {
		painted = new boolean[numberOfQueens][numberOfQueens];
		for (int row = 0; row < numberOfQueens; row++) {
			int col = placedQueens[row];
			if (col < 0)
				continue;
			drawImage(g, new Position(row, col).toPoint(sizeOfCell), ImageUtil.queen);
			drawDangerArea(g);
			drawSafeArea(g);
		}
	}

	private void drawDraggingQueen(Graphics g) {
		if (!isDragging)
			return;
		drawImage(g, draggingPoint, ImageUtil.queen);
	}

	private void drawInspectionCell(Graphics g){
        if (inspectionCell != null){
            if (inspectionCell.getRow() >= numberOfQueens || inspectionCell.getCol() >= numberOfQueens)
                return;
            Image drawing = validCell ? ImageUtil.ok : ImageUtil.poll;
            Point p = inspectionCell.toPoint(sizeOfCell);
            drawImage(g, p, drawing);
            inspectionCell = null;
        }
    }
	
	/**
	 * Setter
	 */
	public void setPolling(ProcessPolling polling) {
		this.polling = polling;
	}

	/**
	 * Getter
	 */

	public int getNumberOfQueen() {
		return numberOfQueens;
	}

	public int getSizeOfCell() {
		return sizeOfCell;
	}
}
