package eightqueens.algorithm;

import eightqueens.ui.panel.CompBoard;
import eightqueens.util.Config;

public class NonRecursiveBacktracking extends Thread {
    private CompBoard compBoard;
    private boolean dangerCol[];
    private boolean dangerDMinus[];
    private boolean dangerDPlus[];
    private int N = 8;
    private int row = 0;
    private int startCol = -1;
    
    /**
     * UI represent
     */
    private ProcessPolling polling;
    private PureBacktracking pureBT;
    
    private int totalSolutionFound = 0;
    private int totalSolutionWithNQueen = 0;

    public NonRecursiveBacktracking(CompBoard compBoard, ProcessPolling polling) {
        this.compBoard = compBoard;
        this.polling = polling;
        this.N = Config.totalQueen;
        dangerCol = new boolean[N];
        dangerDMinus = new boolean[2 * N - 1];
        dangerDPlus = new boolean[2 * N - 1];
        pureBT = new PureBacktracking(N);
        calculateTotalSolutionWithNQueen();
    }
    
    private void calculateTotalSolutionWithNQueen() {
    	if (N > 12)
    		return;
    	pureBT.startAlgorithm();
    	totalSolutionWithNQueen = pureBT.getTotalSolution();
    }

    public void solve() {
        int col;
        boolean found = false;
        boolean queenIsPlaced = false;
        while (!found && polling.isSolving()){
        	checkPause();
            queenIsPlaced = false;
            for (col = startCol + 1; col < N; col++) {
                checkPause();
                polling.updateCurrentBoardState(row, col, false);
                if (!polling.isSolving())
                    break;
                beginSleep();
                if (canPlaceAt(row, col)) {
                	checkPause();
                    polling.updateCurrentBoardState(row, col, true);
                    beginSleep();
                    if (compBoard != null) {
                    	checkPause();
                        compBoard.placedQueenAt(row, col);
                        compBoard.repaint();
                    }
                    queenIsPlaced = true;
                    makeDanger(row, col, true);
//                    beginSleep();
                    if (row >= N - 1){
//                        found = true;
                        foundASolution();
                    }
                    break;
                }
            }
            if (!polling.isSolving())
            	break;
            if (queenIsPlaced){
//                break;
                row++;
                if (row >= N){
                    polling.pauseSolving();
                    checkPause();
                    if (polling.isSolving()) {
                        revertLastValidColumn(col);
                    }
                    continue;
                }
                startCol = -1;
            } else {
            	checkPause();
                startCol = getLastPlacedCol();
                if (startCol == -1) {
                	found = true;
                }
                else {
                    revertLastValidColumn(startCol);
                }
            }
        }
    }

    private void checkPause(){
    	if (!polling.isSolving())
    		terminate();
    	if (polling.paused()) {
	        synchronized (this) {
	        	try{
	                this.wait();
	            } catch (InterruptedException e){
	                e.printStackTrace();
	            }
			}
    	}

    }

    private int getLastPlacedCol(){
        return compBoard.getLastPlacedCol();
    }

    private boolean canPlaceAt(int row, int col){
        return !this.dangerCol[col] && !dangerDMinus[row - col + N - 1] && !dangerDPlus[row + col];
    }

    private void revertLastValidColumn(int lastValidCol){
        row--;
        startCol = lastValidCol;
        compBoard.placedQueenAt(row, -1);
        compBoard.repaint();
        polling.updateCurrentBoardState(row, -1, false);
        beginSleep();
        makeDanger(row, startCol, false);
    }

    private void makeDanger(int row, int col, boolean dangerous) {
        this.dangerCol[col] = dangerous;
        dangerDMinus[row - col + N - 1] = dangerous;
        dangerDPlus[row + col] = dangerous;
    }

    private void foundASolution(){
        polling.pauseSolving();
        totalSolutionFound++;
        polling.notifyFoundASolution(totalSolutionFound, totalSolutionWithNQueen);
        if (totalSolutionWithNQueen > 0 && totalSolutionFound == totalSolutionWithNQueen) {
        	polling.finishSolving();
        	polling.notifyFoundAllSolution();
        }
    }

    private void beginSleep() {
        try {
            Thread.sleep(Config.solveSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminate(){
        if (!polling.isSolving())
            return;
        polling.finishSolving();
    }

    public void cleanThreadData() {
    	compBoard = null;
    	synchronized (this){
          this.notify();
    	}
    }
    
    private void startAlgorithm() {
        synchronized (this){
            solve();
        }
    }

    @Override
    public void run() {
        startAlgorithm();
    }
    
    public void setCompBoard(CompBoard compBoard) {
		this.compBoard = compBoard;
	}
}
