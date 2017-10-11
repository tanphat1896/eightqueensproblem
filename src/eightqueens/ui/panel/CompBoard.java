package eightqueens.ui.panel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import eightqueens.ui.ComputerUI;
import eightqueens.util.Config;
import eightqueens.util.ImageUtil;
import eightqueens.util.Position;

@SuppressWarnings("serial")
public class CompBoard extends BaseBoard {
	@SuppressWarnings("unused")
	private ComputerUI compUI;

	/**
	 * Queens movement
	 */
	private Position inspectionCell;
	private boolean validCell;
	
	public CompBoard(ComputerUI ui) {
		super();
		this.compUI = ui;
	}

	public CompBoard(ComputerUI ui, int totalQueens) {
		super(totalQueens);
		this.compUI = ui;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawInspectionCell(g);
	}

	private void drawInspectionCell(Graphics g) {
		if (!Config.isShowInspectedCell)
			return;
		if (inspectionCell != null) {
			if (inspectionCell.getRow() >= numberOfQueens || inspectionCell.getCol() >= numberOfQueens)
				return;
			Image drawing = validCell ? ImageUtil.ok : ImageUtil.poll;
			Point p = inspectionCell.toPoint(sizeOfCell);
			drawImage(g, p, drawing);
			inspectionCell = null;
		}
	}
	
	/**
	 * Setter getter
	 */
	public void setInspectionCell(Position inspectionCell, boolean validCell) {
		this.inspectionCell = inspectionCell;
		this.validCell = validCell;
	}
	
}
