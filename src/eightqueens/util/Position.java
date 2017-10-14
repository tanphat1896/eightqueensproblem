package eightqueens.util;

import java.awt.Point;

import eightqueens.ui.panel.BaseBoard;

public class Position {
	private int row;
	private int col;
	
	public Position() {
		// TODO Auto-generated constructor stub
		row = col = 0;
	}
	
	public Position(int row, int col) {
		this.col = col;
		this.row = row;
	}
	
	public Position(Point p, int sizeOfCell) {
		row = (int)p.getY()/sizeOfCell;
		int x = Config.useLeftBoard ? p.x : p.x - BaseBoard.GAP;
		col = x/sizeOfCell;
	}
	
	public Point toPoint(int sizeOfCell) {
		return new Point(col*sizeOfCell, row*sizeOfCell);
	}
	
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	public String toString() {
		return row + ", " + col;
	}
}
