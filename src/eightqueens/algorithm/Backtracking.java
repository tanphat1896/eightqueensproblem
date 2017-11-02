package eightqueens.algorithm;

import eightqueens.ui.panel.CompBoard;
import eightqueens.util.Config;

public abstract class Backtracking extends Thread{
	protected CompBoard compBoard;
	protected boolean dangerCol[];
	protected boolean dangerDMinus[];
	protected boolean dangerDPlus[];
	protected int N = 8;
	/**
     * UI represent
     */
	protected ProcessPolling polling;
	protected PureBacktracking pureBT;
    
	protected int totalSolutionFound = 0;
	protected int totalSolutionWithNQueen = 0;
	
	public Backtracking(CompBoard compBoard, ProcessPolling polling) {
		this.compBoard = compBoard;
        this.polling = polling;
        this.N = Config.totalQueen;
        dangerCol = new boolean[N];
        dangerDMinus = new boolean[2 * N - 1];
        dangerDPlus = new boolean[2 * N - 1];
        pureBT = new PureBacktracking(N);
        calculateTotalSolutionWithNQueen();
	}
	
	protected void calculateTotalSolutionWithNQueen() {
    	if (N > 12)
    		return;
    	pureBT.startAlgorithm();
    	totalSolutionWithNQueen = pureBT.getTotalSolution();
    }
	
	protected void checkPause(){
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
	
	protected boolean canPlaceAt(int row, int col){
        return !this.dangerCol[col] && !dangerDMinus[row - col + N - 1] && !dangerDPlus[row + col];
    }

	protected void makeDanger(int row, int col, boolean dangerous) {
        this.dangerCol[col] = dangerous;
        dangerDMinus[row - col + N - 1] = dangerous;
        dangerDPlus[row + col] = dangerous;
    }

	protected void foundASolution(){
        polling.pauseSolving();
        totalSolutionFound++;
        polling.notifyFoundASolution(totalSolutionFound, totalSolutionWithNQueen);
        if (totalSolutionWithNQueen > 0 && totalSolutionFound == totalSolutionWithNQueen) {
        	polling.finishSolving();
        	polling.notifyFoundAllSolution();
        }
    }

	protected void beginSleep() {
    	if (Thread.interrupted())
    		return;
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

 
	
	public abstract void run();
}
