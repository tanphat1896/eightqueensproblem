package eightqueens.algorithm;

import eightqueens.ui.ComputerUI;

public class ProcessPolling extends Thread{
    private ComputerUI compUI;
    private boolean isSolving = false;
    private boolean paused = false;

    public ProcessPolling(ComputerUI compUI){
        this.compUI = compUI;
    }

    public void init(){
        isSolving = false;
        paused = false;
    }

    public boolean isSolving(){
        return isSolving;
    }

    public void beginSolving(){
        isSolving = true;
    }

    public void startNewPuzzle(){
        isSolving = false;
    }

    public void finishSolving(){
    	paused = true;
        isSolving = false;
    }

    public boolean paused(){
        return paused;
    }

    public void pauseSolving(){
        paused = true;
    }

    public void resumeSolving(){
        paused = false;
    }

    public void updateCurrentBoardState(int row, int col, boolean valid){
        compUI.updateCurrentBoardStateInUI(row, col, valid);
    }
    
    public void notifyFoundASolution(int idx, int total) {
    	compUI.notifyFoundASolution(idx, total);
    }
    
    public void notifyFoundAllSolution() {
    	compUI.notifyFoundAllSolution();
    }

    public void test(){

    }
    public void run(){}

    public void setCompUI(ComputerUI compUI) {
		this.compUI = compUI;
	}
}
