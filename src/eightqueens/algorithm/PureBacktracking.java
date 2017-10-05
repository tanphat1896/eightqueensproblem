package eightqueens.algorithm;

/**
 * Giải thuật tìm số lời giải cho N quân hậu <br>
 * Sử dụng đệ quy
 * @author TanPhat
 *
 */
public class PureBacktracking {
	private int totalQueen = 8;
	private int totalSolution = 0;
	private int[] solution;
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
    	dangerCol = new boolean[totalQueen];
    	dangerDMinus = new boolean[2*totalQueen - 1];
    	dangerDPlus = new boolean[2*totalQueen - 1];
    }
    
    private boolean canPlaceAt(int row, int col) {
    	return !dangerCol[col] &&
    			!dangerDMinus[row - col - 1 + totalQueen] &&
    			!dangerDPlus[row + col];
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
                else totalSolution++;
                removeQueen(row, col);
            }
    	}
    }
    
    public void startAlgorithm() {
    	solve(0);
    }
    
    public static void main(String[] args) {
		PureBacktracking p = new PureBacktracking(13);
		p.startAlgorithm();
		System.out.println(p.getTotalSolution());
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
    
    
}
