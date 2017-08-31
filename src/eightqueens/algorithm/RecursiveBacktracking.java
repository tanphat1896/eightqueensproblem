package eightqueens.algorithm;

import eightqueens.GUI.Board;

public class RecursiveBacktracking extends Thread{
    private Board board;
    private boolean dangerCol[];
    private boolean dangerDMinus[];
    private boolean dangerDPlus[];
    private int N;
    private int stepDelay = 100;
    private AlgorithmPolling polling;
    private boolean running = true;

    public RecursiveBacktracking(Board board, AlgorithmPolling polling, int N) {
        // TODO Auto-generated constructor stub
        this.board = board;
        this.polling = polling;
        dangerCol = new boolean[N];
        dangerDMinus = new boolean[2 * N - 1];
        dangerDPlus = new boolean[2 * N - 1];
        this.N = N;
    }

    public void solve(int row) {
        if (!running)
            return;
        for (int col = 0; col < N; col++) {
            if (canPlaceAt(row, col)) {
                board.placedQueenAt(row, col);
                board.repaint();
                makeDanger(row, col, true);
                beginSleep();
                if (row < N - 1)
                    solve(row + 1);
                else
                    waitingUntilNextOperation();
                if (!running)
                    return;
                board.placedQueenAt(row, -1);
                makeDanger(row, col, false);
            }
        }
    }

    private boolean canPlaceAt(int row, int col){
        return !this.dangerCol[col] && !dangerDMinus[row - col + N - 1] && !dangerDPlus[row + col];
    }

    private void makeDanger(int row, int col, boolean dangerous) {
        this.dangerCol[col] = dangerous;
        dangerDMinus[row - col + N - 1] = dangerous;
        dangerDPlus[row + col] = dangerous;
    }

    private void beginSleep() {
        try {
            Thread.sleep(stepDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitingUntilNextOperation() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void terminateAlgorithm(){
//        if (!polling.isRunning())
//            return;
////        synchronized (this){
//            running = false;
////            this.notify();
//            this.interrupt();
////        }
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
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
