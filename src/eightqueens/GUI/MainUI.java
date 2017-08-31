package eightqueens.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import eightqueens.algorithm.AlgorithmPolling;
import eightqueens.algorithm.NonRecursiveBacktracking;
import eightqueens.algorithm.RecursiveBacktracking;
import eightqueens.util.Position;

@SuppressWarnings("serial")
public class MainUI extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 700;
	
	private RecursiveBacktracking recursiveBacktrackingAlg;
	private NonRecursiveBacktracking nonRecursiveBacktracking;
	private AlgorithmPolling polling;
	private int numberOfQueens = 8;


    private String letter[] = new String[numberOfQueens];
	
	public MainUI() {
		// TODO Auto-generated constructor stub
		initUI();
		initComponents();
		initActions();
//		initData();
        polling = new AlgorithmPolling(this);
        board.setPolling(polling);
	}
	
	private void initData() {
//        board.reinitBoard(numberOfQueens);
//		if (recursionBacktrackingAlg != null) {
//            recursionBacktrackingAlg.terminate();
//            recursionBacktrackingAlg = null;
//        }
//		recursionBacktrackingAlg = new RecursiveBacktracking(board, polling, numberOfQueens);
        board.reinitBoard(numberOfQueens);
        letter = new String[numberOfQueens];
        for (int i = 0; i < numberOfQueens; i++)
        	letter[i] = (char)(65 + i) + "";
		if (nonRecursiveBacktracking != null) {
            nonRecursiveBacktracking.terminate();
            nonRecursiveBacktracking = null;
        }
        nonRecursiveBacktracking = new NonRecursiveBacktracking(board, polling, numberOfQueens);
		setAlgorithmStepDelay();
	}

	private void terminateCurrentOperation(){
//	    if (recursionBacktrackingAlg != null && polling.isRunning()){
//            polling.startNewAlgorithm();
//	        recursionBacktrackingAlg.terminate();
//        }
    }

    private void setAlgorithmStepDelay(){
	    int delay;
	    try {
            delay = Integer.parseInt(tfDelay.getText());
        } catch (NumberFormatException e){
	        delay = 100;
        }
        nonRecursiveBacktracking.setStepDelay(delay);
    }

    private void beginAlgorithm(){
        initData();
        polling.beginSolving();
        nonRecursiveBacktracking.start();
        toggleComponents(false);
    }

    private void stopAlgorithm(){
        if (polling.isSolving()){
            board.setInspectionCell(null, false);
            nonRecursiveBacktracking.terminate();
            toggleComponents(true);
            polling.init();
        }
    }

    private void toggleComponents(boolean isEnableBtnSolve){
        btnSolve.setEnabled(isEnableBtnSolve);
        btnClearBoard.setEnabled(isEnableBtnSolve);
        cboNumber.setEnabled(isEnableBtnSolve);
        tfDelay.setEnabled(isEnableBtnSolve);
        btnPause.setEnabled(!isEnableBtnSolve);
        btnNextSolution.setEnabled(!isEnableBtnSolve);
        btnStop.setEnabled(!isEnableBtnSolve);
    }

    private void togglePause(){
        if (polling.paused()){
            polling.resumeSolving();
            btnPause.setText("Pause");
            synchronized (nonRecursiveBacktracking){
                nonRecursiveBacktracking.notify();
            }
        } else {
            polling.pauseSolving();
            btnPause.setText("Resume");
        }
    }

    public void updateCurrentBoardStateInUI(int row, int col, boolean valid){
    	if (col != -1)
    		board.setInspectionCell(new Position(row, col), valid);
        board.repaint();
        lblRowVal.setText(row + 1 + "");
        if (valid) {
        	lblColSolution[row].setText(letter[col]);
            lblColVal.setText(letter[col]);
        } else {
        	lblColSolution[row].setText("NA");;
        }
    }

	/**
	 * Components
	 */
	private Board board;
	private JPanel pnControl;
	private JComboBox<Integer> cboNumber;
	private JButton btnSolve;
	private JButton btnClearBoard;
	private JButton btnPause;
	private JButton btnStop;
	private JButton btnNextSolution;
	private JTextField tfDelay;


    private JPanel pnBoardState;
    private JLabel lblRow, lblCol;
    private JLabel lblRowVal, lblColVal;

    private JPanel pnSolution;
    private JLabel lblRowSolution[];
    private JLabel lblColSolution[];

    /**
     * Measurement
     */
    private int controlHeight = 30;
    private int controlWidth = 100;
    private int labelSolutionSize = 35;
//    private Color btnColor = new Color(0, 131, 235);
    private Color btnColor = new Color(105, 183, 0);

	/**
	 * Init GUI
	 */
	private void initUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Eight Queens Problem");
		this.setResizable(false);
		getContentPane().setLayout(null);
	}

	private void initComponents() {
		board = new Board();
		board.setBounds(5, 5, board.getWidth(), board.getHeight());
		getContentPane().add(board);
		initPanelControl();
        toggleComponents(true);
        initPanelBoardState();
        initPanelSolution();
    }

    private void initPanelControl() {
        pnControl = new JPanel();
        pnControl.setLayout(null);
        pnControl.setBounds(550, 5, 230, 190);
        pnControl.setBorder(BorderFactory.createLineBorder(Color.gray));
        getContentPane().add(pnControl);

        JLabel lblNum = new JLabel("Số quân hậu");
        lblNum.setBounds(10, 5, controlWidth, controlHeight);
        pnControl.add(lblNum);
    	cboNumber = new JComboBox<>();
		cboNumber.setAutoscrolls(true);
		DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
		for (int i = 1; i <= 20; i++)
			if (i != 2 && i != 3)
				model.addElement(i);
		cboNumber.setModel(model);
		cboNumber.setBounds(110, 5, controlWidth, controlHeight);
		cboNumber.setSelectedIndex(5);
		pnControl.add(cboNumber);

		JLabel lblDelay = new JLabel("Delay/cell");
		lblDelay.setBounds(10, 40, controlWidth, controlHeight);
		pnControl.add(lblDelay);
		tfDelay = new JTextField();
		tfDelay.setBounds(110, 40, controlWidth, controlHeight);
		tfDelay.setText("100");
        pnControl.add(tfDelay);

        /**
         * Split 2 cols
         */
		btnSolve = new JButton("Run");
		changeButtonAppearance(btnSolve);
		btnSolve.setBounds(10, 80, controlWidth, controlHeight);
        pnControl.add(btnSolve);

		btnClearBoard = new JButton("Clear");
        changeButtonAppearance(btnClearBoard);
		btnClearBoard.setBounds(115, 80, controlWidth, controlHeight);
        pnControl.add(btnClearBoard);

		btnStop = new JButton("Stop");
        changeButtonAppearance(btnStop);
		btnStop.setBounds(10, 115, controlWidth, controlHeight);
        pnControl.add(btnStop);

        btnPause = new JButton("Pause");
        changeButtonAppearance(btnPause);
        btnPause.setBounds(115, 115, controlWidth, controlHeight);
        pnControl.add(btnPause);

		btnNextSolution = new JButton("Go next");
		changeButtonAppearance(btnNextSolution);
		btnNextSolution.setBounds(10, 150, controlWidth, controlHeight);
		pnControl.add(btnNextSolution);
    }

	private void changeButtonAppearance(JButton btn){
	    btn.setOpaque(true);
	    btn.setForeground(Color.white);
	    btn.setBackground(btnColor);
	    btn.setBorderPainted(false);
	    btn.setFocusPainted(false);
    }

    private void initPanelBoardState(){
        pnBoardState = new JPanel();
        pnBoardState.setLayout(null);
        pnBoardState.setBounds(550, 200, 230, 200);
        pnBoardState.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(pnBoardState);

        lblRow = new JLabel("Current row: ");
        lblRow.setBounds(10, 5, 120, 25);
        pnBoardState.add(lblRow);

        lblCol = new JLabel("Current col: ");
        lblCol.setBounds(10, 35, 120, 25);
        pnBoardState.add(lblCol);

        lblRowVal = new JLabel("0");
        lblRowVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblRowVal.setBounds(130, 5, 20, 25);
        pnBoardState.add(lblRowVal);

        lblColVal = new JLabel("0");
        lblColVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblColVal.setBounds(130, 35, 20, 25);
        pnBoardState.add(lblColVal);        
    }

    private void initPanelSolution(){
        pnSolution = new JPanel();
        pnSolution.setLayout(null);
        pnSolution.setBounds(20,  540, 775, 100);
        getContentPane().add(pnSolution);

        initLabelState();
    }
    
    private void initLabelState() {
    	pnSolution.removeAll();
    	JLabel lblSolutionCaption = new JLabel("Trạng thái kết quả");
        lblSolutionCaption.setBounds(0, 3, 200, 20);
        pnSolution.add(lblSolutionCaption);
//        lblSolutionCaption.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	lblColSolution = new JLabel[numberOfQueens];
    	lblRowSolution = new JLabel[numberOfQueens];

    	for (int i = 0; i < numberOfQueens; i++){
    	    lblRowSolution[i] = new JLabel(i + 1 + "");
//    	    System.out.println(i*40 + ", ");
    	    lblRowSolution[i].setBounds(i*labelSolutionSize - i, labelSolutionSize - 5, labelSolutionSize, labelSolutionSize);
    	    lblRowSolution[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	    lblRowSolution[i].setFont(new Font("Consolas", Font.PLAIN, 20));
    	    lblRowSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    pnSolution.add(lblRowSolution[i]);
    	    
    	    lblColSolution[i] = new JLabel("NA");	
    	    lblColSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    lblColSolution[i].setFont(new Font("Consolas", Font.PLAIN, 20));
    	    lblColSolution[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	    lblColSolution[i].setBounds(i*labelSolutionSize - i, 2*labelSolutionSize - 1 - 5, labelSolutionSize, labelSolutionSize);
    	    pnSolution.add(lblColSolution[i]);
        }
    	pnSolution.revalidate();
    	pnSolution.repaint();
    }

    /**
	 * Init action
	 */
	private void initActions() {
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                beginAlgorithm();
			}
		});
		cboNumber.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
                numberOfQueens = Integer.parseInt(e.getItem().toString());
                board.reinitBoard(numberOfQueens);
                initLabelState();
			}
		});
		
		tfDelay.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
				    setAlgorithmStepDelay();
				} catch (Exception e2) {
					// TODO: handle exception
//					delay = 100;
				}
			}
		});
		
		btnClearBoard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
                board.reinitBoard(numberOfQueens);
                initLabelState();
			}
		});

		btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAlgorithm();
            }
        });

		btnPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
	}

	public static void main(String[] args) {
		new MainUI().setVisible(true);
	}
}
