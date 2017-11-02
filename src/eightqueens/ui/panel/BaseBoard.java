package eightqueens.ui.panel;

import java.awt.*;
import java.util.*;
import javax.swing.JPanel;

import eightqueens.util.Config;
import eightqueens.util.GUIUtil;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;

@SuppressWarnings("serial")
public class BaseBoard extends JPanel {
	/**
	 * Board config
	 */
	public static final int EDGE_SIZE = 480;
	public static final int GAP = 40;
	public static final Color TEXT_COLOR = Color.darkGray;
	public static final Color WRAP_LINE_COLOR = GUIUtil.BORDER_PANEL;
	public static final Color LINE_COLOR = Color.white;
	
	protected boolean useLightBg = false;
	protected boolean useColorBg = false;
	
	protected Color bgColorLight = new Color(255, 255, 255);
	protected Color safeCellColor = new Color(145, 216, 145);
	protected Color bgColor = new Color(0, 128, 192);

	/**
	 * Board data
	 */
	protected int sizeOfCell;
	protected int boardBoundary;
	protected int numberOfQueens = 8;
	protected boolean[][] painted;
	protected int[] placedQueens;

	protected boolean isHintSafeCell = false;

	public BaseBoard() {
		initUI();
		initData();
	}

	public BaseBoard(int numOfQueens) {
		this.numberOfQueens = numOfQueens;
		initUI();
		initData();
	}

	private void initUI() {
		this.setLayout(null);
		this.setSize(EDGE_SIZE + GAP + 5, EDGE_SIZE + GAP + 1);
		this.setBackground(GUIUtil.MAIN_BG);
	}

	public void reinitBoard(int numOfQueen) {
		this.numberOfQueens = numOfQueen;
		initData();
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBoard(g);
		putPlacedQueens(g);
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

	public int getLastPlacedCol() {
		int i = numberOfQueens - 1;
		while (placedQueens[i--] == -1)
			if (i < 0)
				break;
		return placedQueens[i + 1];
	}

	protected boolean isSafe(Position pos) {
		return !hasQueen(pos) && !painted[pos.getRow()][pos.getCol()];
	}

	protected boolean hasQueen(Position pos) {
		return placedQueens[pos.getRow()] == pos.getCol();
	}

	protected boolean hasQueen(int row, int col) {
		return placedQueens[row] == col;
	}

	protected void placeQueen(Position pos) {
		placedQueens[pos.getRow()] = pos.getCol();
	}

	protected void removeQueen(Position pos) {
		placedQueens[pos.getRow()] = -1;
	}

	/**
	 * Kiểm tra xem bàn cờ có quân hậu nào không
	 * 
	 * @return boolean
	 */
	public boolean noQueenPlaced() {
		for (int i = 0; i < numberOfQueens; i++) {
			if (placedQueens[i] >= 0)
				return false;
		}
		return true;
	}

	public boolean allQueensPlaced() {
		for (int i = 0; i < numberOfQueens; i++)
			if (placedQueens[i] < 0)
				return false;
		return true;
	}
	
	/**
	 * Paint Methods
	 */

	private void drawBoard(Graphics g) {
		drawGrid(g);
		drawBackground(g, useColorBg);
		drawIndex(g);
	}

	private void drawGrid(Graphics g) {
		g.setColor(LINE_COLOR);
		if (Config.useLeftBoard)
			drawGridToTheLeft(g);
		else
			drawGridToTheRight(g);
	}

	private void drawGridToTheLeft(Graphics g) {
		for (int i = 1; i < numberOfQueens; i++) {
			g.drawLine(0, i * sizeOfCell, boardBoundary, i * sizeOfCell);
			g.drawLine(i * sizeOfCell, 0, i * sizeOfCell, boardBoundary);
		}
		g.setColor(WRAP_LINE_COLOR);
		g.drawLine(0, 0, boardBoundary, 0);
		g.drawLine(0, 0, 0, boardBoundary);
		g.drawLine(boardBoundary, 0, boardBoundary, boardBoundary);
		g.drawLine(0, boardBoundary, boardBoundary, boardBoundary);
	}

	private void drawGridToTheRight(Graphics g) {
		for (int i = 1; i < numberOfQueens; i++) {
			g.drawLine(GAP, i * sizeOfCell, boardBoundary + GAP, i * sizeOfCell);
			g.drawLine(i * sizeOfCell + GAP, 0, i * sizeOfCell + GAP, boardBoundary);
		}
		
		g.setColor(WRAP_LINE_COLOR);
		g.drawLine(GAP, 0, boardBoundary + GAP, 0);
		g.drawLine(GAP, 0, GAP, boardBoundary);
		g.drawLine(boardBoundary + GAP, 0, boardBoundary + GAP, boardBoundary);
		g.drawLine(GAP, boardBoundary, boardBoundary + GAP, boardBoundary);
	}

	private void drawBackground(Graphics g, boolean withColor) {
		for (int i = 0; i < numberOfQueens; i++)
			for (int j = 0; j < numberOfQueens; j++) {
				if ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) {
					if (!withColor) {
						Image img = ImageUtil.darkBG;
//						Image img = (Config.boardBgImg != null) ? Config.boardBgImg : ImageUtil.darkBG;
						g.drawImage(img, i * sizeOfCell + 1 + (Config.useLeftBoard ? 0 : GAP), j * sizeOfCell + 1, null);
					} else {
						Color bg = (Config.boardBgColor != null) ? Config.boardBgColor : bgColor;
						g.setColor(bg);
						g.fillRect(i * sizeOfCell + 1 + (Config.useLeftBoard ? 0 : GAP), j * sizeOfCell + 1, sizeOfCell - 1,
								sizeOfCell - 1);
					}
				} else if (useLightBg) {
					/**
					 * Tô thêm các ô sáng cho bàn cờ
					 */
					g.setColor(bgColorLight);
					g.fillRect(i * sizeOfCell + 1 + (Config.useLeftBoard ? 0 : GAP ), j * sizeOfCell + 1, sizeOfCell - 1,
							sizeOfCell - 1);
				}
			}
	}

	private void drawIndex(Graphics g) {
		g.setColor(TEXT_COLOR);
		g.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 15));

		// fix ugly font
		Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
		Graphics2D g2d = (Graphics2D) g;
		if (desktopHints != null) {
			g2d.setRenderingHints(desktopHints);
		}

		if (Config.useLeftBoard)
			drawIndexToTheRight(g);
		else drawIndexToTheLeft(g);
	}
	
	private void drawIndexToTheLeft(Graphics g) {
		g.drawString("A", sizeOfCell / 2 - 5 + GAP, boardBoundary + 25);
		g.drawString("1", 10, (numberOfQueens - 1) * sizeOfCell + sizeOfCell / 2 + 5);

		for (int i = 1; i < numberOfQueens; i++) {
			g.drawString((char) (65 + i) + "", i * sizeOfCell + sizeOfCell / 2 - 5 + GAP, boardBoundary + 25);
			g.drawString("" + (i + 1), 10, (numberOfQueens - i - 1) * sizeOfCell + sizeOfCell / 2 + 5);
		}
	}
	
	private void drawIndexToTheRight(Graphics g) {
		g.drawString("A", sizeOfCell / 2 - 5, boardBoundary + 20);
		g.drawString("1", boardBoundary + 20, (numberOfQueens - 1) * sizeOfCell + sizeOfCell / 2 + 5);

		for (int i = 1; i < numberOfQueens; i++) {
			g.drawString((char) (65 + i) + "", i * sizeOfCell + sizeOfCell / 2 - 5, boardBoundary + 20);
			g.drawString("" + (i + 1), boardBoundary + 20, (numberOfQueens - i - 1) * sizeOfCell + sizeOfCell / 2 + 5);
		}
	}

	protected void drawImage(Graphics g, Point p, Image img) {
		int x = Config.useLeftBoard ? p.x : p.x + GAP; 
		g.drawImage(img, x + 1, p.y + 1, null);
	}

	/**
	 * Mục đích kiểm tra nhiều điều kiện để tránh bị ghi đè nhiều hình lên 1 ô
	 * 
	 * @param g
	 */
	private void drawDangerArea(Graphics g) {
		for (int row = 0; row < numberOfQueens; row++) {
			int col = placedQueens[row];
			if (col < 0)
				continue;
			for (int i = 0; i < numberOfQueens; i++) {
				if (!painted[i][col]) {
					if (!hasQueen(i, col)) {
						painted[i][col] = true;
						if (Config.isShowDangerCell)
							drawImage(g, new Position(i, col).toPoint(sizeOfCell), ImageUtil.danger);
					}
				}
				if (!painted[row][i]) {
					if (!hasQueen(row, i)) {
						painted[row][i] = true;
						if (Config.isShowDangerCell)
							drawImage(g, new Position(row, i).toPoint(sizeOfCell), ImageUtil.danger);
					}
				}
				if (row + i < numberOfQueens) {
					if (col + i < numberOfQueens)
						if (!painted[row + i][col + i] && !hasQueen(row + i, col + i)) {
							painted[row + i][col + i] = true;
							if (Config.isShowDangerCell)
								drawImage(g, new Position(row + i, col + i).toPoint(sizeOfCell), ImageUtil.danger);

						}
					if (col - i >= 0)
						if (!painted[row + i][col - i] && !hasQueen(row + i, col - i)) {
							painted[row + i][col - i] = true;
							if (Config.isShowDangerCell)
								drawImage(g, new Position(row + i, col - i).toPoint(sizeOfCell), ImageUtil.danger);
						}
				}
				if (row - i >= 0) {
					if (col + i < numberOfQueens)
						if (!painted[row - i][col + i] && !hasQueen(row - i, col + i)) {
							painted[row - i][col + i] = true;
							if (Config.isShowDangerCell)
								drawImage(g, new Position(row - i, col + i).toPoint(sizeOfCell), ImageUtil.danger);
						}
					if (col - i >= 0)
						if (!painted[row - i][col - i] && !hasQueen(row - i, col - i)) {
							painted[row - i][col - i] = true;
							if (Config.isShowDangerCell)
								drawImage(g, new Position(row - i, col - i).toPoint(sizeOfCell), ImageUtil.danger);
						}
				}
			}
		}
	}

	private void drawSafeArea(Graphics g) {
		g.setColor(safeCellColor);
		for (int row = 0; row < numberOfQueens; row++)
			for (int col = 0; col < numberOfQueens; col++)
				if (!painted[row][col] && !hasQueen(row, col))
					g.fillRect(col * sizeOfCell + 1 + (Config.useLeftBoard ? 0: GAP), row * sizeOfCell + 1, sizeOfCell - 1, sizeOfCell - 1);
	}

	/**
	 * Vẽ lại các quân cờ đã được đặt lên bàn cờ
	 * 
	 * @param g
	 */
	private void putPlacedQueens(Graphics g) {
		painted = new boolean[numberOfQueens][numberOfQueens];
		for (int row = 0; row < numberOfQueens; row++) {
			int col = placedQueens[row];
			if (col < 0)
				continue;
			Image img = ImageUtil.queen;
//			Image img = (Config.defaultQueen != null) ? Config.defaultQueen : ImageUtil.queen;
			drawImage(g, new Position(row, col).toPoint(sizeOfCell), img);
			drawDangerArea(g);
			if (isHintSafeCell)
				drawSafeArea(g);
		}
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

	public void setUseColorBg(boolean useColorBg) {
		this.useColorBg = useColorBg;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public void setHintSafeCell(boolean isHintSafeCell) {
		this.isHintSafeCell = isHintSafeCell;
	}

	public void setSafeCellColor(Color safeCellColor) {
		this.safeCellColor = safeCellColor;
	}

	public int[] getPlacedQueens() {
		return placedQueens;
	}
}
