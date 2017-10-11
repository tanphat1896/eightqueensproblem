package eightqueens.algorithm;

/**
 * Giải thuật tìm số lời giải cho N quân hậu <br>
 * Sử dụng đệ quy
 * 
 * @author TanPhat
 *
 */
public class PureBacktracking {
	private int totalQueen = 4;
	private int totalSolution = 0;
	private int[] solution;
	private int[] compareSolution;
	private boolean dangerCol[];
	private boolean dangerDMinus[];
	private boolean dangerDPlus[];

	public PureBacktracking() {
		initData();
	}

	public PureBacktracking(int totalQueen) {
		this.totalQueen = totalQueen;
		initData();
	}

	private void initData() {
		solution = new int[totalQueen];
		compareSolution = new int[totalQueen];
		for (int i = 0; i < totalQueen; i++) {
			solution[i] = -1;
			compareSolution[i] = -1;
		}
		dangerCol = new boolean[totalQueen];
		dangerDMinus = new boolean[2 * totalQueen - 1];
		dangerDPlus = new boolean[2 * totalQueen - 1];
	}


	private boolean canPlaceAt(int row, int col) {
		return !dangerCol[col] && !dangerDMinus[row - col - 1 + totalQueen] && !dangerDPlus[row + col];
	}

	private void notifyDangerArea(int row, int col) {
		dangerCol[col] = true;
		dangerDMinus[row - col - 1 + totalQueen] = true;
		dangerDPlus[row + col] = true;
	}

	private void notifySafeArea(int row, int col) {
		dangerCol[col] = false;
		dangerDMinus[row - col - 1 + totalQueen] = false;
		dangerDPlus[row + col] = false;
	}

	private void placeQueen(int row, int col) {
		solution[row] = col;
		notifyDangerArea(row, col);
	}

	private void removeQueen(int row, int col) {
		solution[row] = -1;
		notifySafeArea(row, col);
	}

	private void solve(int row) {
		for (int col = 0; col < totalQueen; col++) {
			if (canPlaceAt(row, col)) {
				placeQueen(row, col);
				if (row < totalQueen - 1)
					solve(row + 1);
				else
					totalSolution++;
				removeQueen(row, col);
			}
		}
	}
	
	private boolean found = false;
	private void solveWithExistSolution(int row) {
		if (found)
			return;
		if (row >= totalQueen)
		    return;
		if (compareSolution[row] >= 0)
		    if (row == totalQueen - 1)
		        found = true;
		    else
                solveWithExistSolution(row + 1);
		for (int col = 0; col < totalQueen; col++) {
			if (compareSolution[row] < 0 && canPlaceAt(row, col)) {
				placeQueen(row, col);
				if (row < totalQueen - 1)
					solveWithExistSolution(row + 1);
				else {
					found = true;
					return;
				}
				if (!found)
                    removeQueen(row, col);
			}
		}
	}

	public void startAlgorithm() {
		solve(0);
	}
	
	public boolean isPossibleWithSolution() {
		solveWithExistSolution(0);
		return solutionFound();
	}
	
	public boolean solutionFound() {
		for (int i = 0; i < totalQueen; i++)
			if (solution[i] < 0)
				return false;
		return true;
	}

	public void printSolution() {
		for (int i = 0; i < totalQueen; i++)
			System.out.println("Row " + i + " : " + solution[i]);
	}

	public static void main(String[] args) {
		PureBacktracking p = new PureBacktracking(4);
		p.startAlgorithm();
		p.printSolution();
	}

	public int getTotalQueen() {
		return totalQueen;
	}

	public void setTotalQueen(int totalQueen) {
		this.totalQueen = totalQueen;
	}

	public int getTotalSolution() {
		return totalSolution;
	}

	public void setTotalSolution(int totalSolution) {
		this.totalSolution = totalSolution;
	}

	public int[] getSolution() {
		return solution;
	}

	public void setSolution(int[] solution) {
		this.solution = solution;
	}
	
	public void setCompareSolution(int[] compareSolution) {
		for (int i = 0; i < totalQueen; i++) {
			this.compareSolution[i] = compareSolution[i];
			if (compareSolution[i] >= 0) {
				solution[i] = compareSolution[i];
				notifyDangerArea(i, compareSolution[i]);
			}
//			System.out.println("Row " + i + ": " + compareSolution[i] + " ; " + solution[i]);
		}
	}

}
