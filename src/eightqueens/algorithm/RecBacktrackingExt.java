package eightqueens.algorithm;

import eightqueens.ui.panel.CompBoard;

public class RecBacktrackingExt extends Backtracking{

    public RecBacktrackingExt(CompBoard compBoard, ProcessPolling polling) {
        super(compBoard, polling);
    }

    public void solve(int row) {
        for (int col = 0; col < N; col++) {
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
                makeDanger(row, col, true);
//                beginSleep();
                if (row >= N - 1){
                    foundASolution();
                } else {
                	solve(row+1);
                }
                if (compBoard != null) {
                	compBoard.placedQueenAt(row, -1);
                    makeDanger(row, col, false);
                }
            }
        }
    }

    private void startAlgorithm() {
        synchronized (this){
            solve(0);
        }
    }

    @Override
    public void run() {
        startAlgorithm();
    }

}
