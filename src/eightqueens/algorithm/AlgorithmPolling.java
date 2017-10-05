package eightqueens.algorithm;

import eightqueens.ui.CoreUI;

public class AlgorithmPolling extends Thread{
    private CoreUI mainUI;
    private boolean isSolving = false;
    private boolean paused = false;

    public AlgorithmPolling(CoreUI mainUI){
        this.mainUI = mainUI;
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
        mainUI.updateCurrentBoardStateInUI(row, col, valid);
    }

    public void test(){

    }
    public void run(){
    }
}
