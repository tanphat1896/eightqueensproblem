package eightqueens.algorithm;

import eightqueens.ui.panel.CompBoard;

public class NonRecBacktrackingExt extends Backtracking {
    private int row = 0;
    private int startCol = -1;

    public NonRecBacktrackingExt(CompBoard compBoard, ProcessPolling polling) {
        super(compBoard, polling);
    }

    private void solve() {
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
                    checkPause();
                    if (compBoard != null) {
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

    private void revertLastValidColumn(int lastValidCol){
        row--;
        startCol = lastValidCol;
        compBoard.placedQueenAt(row, -1);
        compBoard.repaint();
        polling.updateCurrentBoardState(row, -1, false);
        beginSleep();
        makeDanger(row, startCol, false);
    }
    

    private int getLastPlacedCol(){
    	if (compBoard == null)
    		return -1;
        return compBoard.getLastPlacedCol();
    }

    
    private void startAlgorithm() {
        synchronized (this){
            solve();
        }
    }

    @Override
    public void run() {
    	if (Thread.interrupted())
    		return;
        startAlgorithm();
    }
}
