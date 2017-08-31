package eightqueens.algorithm;

import eightqueens.GUI.Board;

import javax.swing.*;

public class NonRecursiveBacktracking extends Thread {
    private Board board;
    private boolean dangerCol[];
    private boolean dangerDMinus[];
    private boolean dangerDPlus[];
    private int N;
    private int numOfSolution = 0;
    private int row = 0;
    private int startCol = -1;
    /**
     * UI represent
     */
    private int stepDelay = 100;
    private AlgorithmPolling polling;
    private boolean running = true;

    public NonRecursiveBacktracking(Board board, AlgorithmPolling polling, int N) {
        // TODO Auto-generated constructor stub
        this.board = board;
        this.polling = polling;
        dangerCol = new boolean[N];
        dangerDMinus = new boolean[2 * N - 1];
        dangerDPlus = new boolean[2 * N - 1];
        this.N = N;
    }

    public void solve() {
        int col;
        boolean found = false;
        boolean queenIsPlaced = false;
        while (!found){
            if (!running)
                break;
            queenIsPlaced = false;
            for (col = startCol + 1; col < N; col++) {
                checkingPause();
                if (!polling.isSolving())
                    break;
                polling.updateCurrentBoardState(row, col, false);
                beginSleep();
                if (canPlaceAt(row, col)) {
                    polling.updateCurrentBoardState(row, col, true);
                    beginSleep();
                    board.placedQueenAt(row, col);
                    board.repaint();

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
            if (queenIsPlaced){
//                break;
                row++;
                if (row >= N){
                    polling.pauseSolving();
                    if (!polling.paused()) {
                    	revertLastValidColumn(col);
                        continue;
                    }
                }
                startCol = -1;
            } else {
                startCol = getLastPlacedCol();
                if (startCol == -1)
                    found = true;
                else {
                    revertLastValidColumn(startCol);
                }
            }
        }
    }

    private void checkingPause(){
        try{
            if (polling.paused()){
                this.wait();
            }
        } catch (InterruptedException e){
//            e.printStackTrace();
        }

    }

    private int getLastPlacedCol(){
        return board.getLastPlacedCol();
    }

    private boolean canPlaceAt(int row, int col){
        return !this.dangerCol[col] && !dangerDMinus[row - col + N - 1] && !dangerDPlus[row + col];
    }

    private void revertLastValidColumn(int lastValidCol){
        row--;
        startCol = lastValidCol;
        board.placedQueenAt(row, -1);
        board.repaint();
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
        numOfSolution++;
        JOptionPane.showMessageDialog(null, "Total solution found: " + numOfSolution);
    }

    private void finishSolving(){
        polling.finishSolving();
    }

    private void beginSleep() {
        try {
            Thread.sleep(stepDelay);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }

    public void terminate(){
        if (!polling.isSolving())
            return;
        polling.finishSolving();
//        synchronized (this){
            running = false;
//            this.notify();
            this.interrupt();
//        }
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
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
}
