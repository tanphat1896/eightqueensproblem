package eightqueens.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import eightqueens.algorithm.AlgorithmPolling;
import eightqueens.algorithm.NonRecursiveBacktracking;
import eightqueens.algorithm.PureBacktracking;
import eightqueens.algorithm.RecursiveBacktracking;
import eightqueens.ui.panel.Board;
import eightqueens.util.Position;
import eightqueens.util.Result;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class BaseUI extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 700;
	
	protected int numberOfQueens = 8;

    protected String letter[] = new String[numberOfQueens];
	
	public BaseUI() {
		// TODO Auto-generated constructor stub
		initUI();
		initComponents();
		initActions();
		initData();
	}
	
	private void initData() {
		letter = new String[numberOfQueens];
        for (int i = 0; i < numberOfQueens; i++)
        	letter[i] = (char)(65 + i) + "";
	}
	
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {};

	/**
	 * Components
	 */
//	private Board board;
	private JPanel pnBoard;
	protected JComboBox<Integer> cboNumber;

    private JPanel pnBoardState;
    private JLabel lblRow, lblCol;
    protected JLabel lblRowVal, lblColVal;

    private JPanel pnSolution;
    protected JLabel lblRowSolution[];
    protected JLabel lblColSolution[];
    protected int labelSolutionSize = 30;

    /**
     * Measurement
     */
    public static final int CONTROL_WIDTH = 100;
    public static final int CONTROL_HEIGHT = 25;
    private Color colorBtn = new Color(43, 87, 154);
//    private Color btnColor = new Color(0, 131, 235);
//    private Color btnColor = new Color(105, 183, 0);
    
    private static final Color FG_SUCCESS = new Color(0, 255, 0);
    private static final Color FG_DANGER = new Color(200, 0, 0);

	/**
	 * Init GUI
	 */
	private void initUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Phần mềm giải bài toán Tám quân Hậu");
		this.setResizable(false);
		getContentPane().setLayout(null);
	}

	private void initComponents() {
//		board = new Board();
//		board.setBounds(5, 5, board.getWidth(), board.getHeight());
//		getContentPane().add(board);
		initPanelControl();
        initPanelBoardState();
        initPanelSolution();
    }

    private void initPanelControl() {
        pnBoard = new JPanel();
        pnBoard.setLayout(null);
        pnBoard.setBounds(550, 5, 230, 91);
        pnBoard.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "B\u00E0n c\u1EDD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        getContentPane().add(pnBoard);

        JLabel lblNum = new JLabel("Chọn số quân hậu");
        lblNum.setBounds(12, 26, 206, 16);
        pnBoard.add(lblNum);
    	cboNumber = new JComboBox<>();
		cboNumber.setAutoscrolls(true);
		DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
		for (int i = 1; i <= 20; i++)
			if (i != 2 && i != 3)
				model.addElement(i);
		cboNumber.setModel(model);
		cboNumber.setBounds(12, 54, 206, 25);
		cboNumber.setSelectedIndex(5);
		pnBoard.add(cboNumber);
    }

	private void changeButtonAppearance(JButton btn){
//	    btn.setOpaque(true);
//	    btn.setForeground(Color.white);
//	    btn.setBackground(btnColor);
//	    btn.setBorderPainted(false);
//	    btn.setFocusPainted(false);
    }

    private void initPanelBoardState(){
        pnBoardState = new JPanel();
        pnBoardState.setLayout(null);
        pnBoardState.setBounds(550, 200, 230, 200);
        pnBoardState.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(pnBoardState);

        lblRow = new JLabel("Duyá»‡t dÃ²ng: ");
        lblRow.setBounds(10, 5, 120, 25);
        pnBoardState.add(lblRow);

        lblCol = new JLabel("Duyá»‡t cá»™t: ");
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
    
    protected void initLabelState() {
    	pnSolution.removeAll();
    	JLabel lblSolutionCaption = new JLabel("Duyệt các quân hậu đã đặt");
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
    	    lblRowSolution[i].setFont(new Font("Consolas", Font.PLAIN, 15));
    	    lblRowSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    pnSolution.add(lblRowSolution[i]);
    	    
    	    lblColSolution[i] = new JLabel("");	
    	    lblColSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    lblColSolution[i].setFont(new Font("Consolas", Font.PLAIN, 15));
    	    lblColSolution[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	    lblColSolution[i].setBounds(i*labelSolutionSize - i, 2*labelSolutionSize - 1 - 5, labelSolutionSize, labelSolutionSize);
    	    pnSolution.add(lblColSolution[i]);
        }
    	pnSolution.revalidate();
    	pnSolution.repaint();
    }
    
    public void changeLabelSolutionAppearance(Result result) {
    	if (result == Result.FOUND) {
    		for (JLabel jLabel : lblColSolution) {
				jLabel.setForeground(FG_SUCCESS);
			}
    	} else {
    		for (JLabel jLabel : lblColSolution) {
				jLabel.setForeground(FG_DANGER);
			}
    	}
    }

    /**
	 * Init action
	 */
	private void initActions() {
//		cboNumber.addItemListener(new ItemListener() {
//			@Override
//			public void itemStateChanged(ItemEvent e) {
//                numberOfQueens = Integer.parseInt(e.getItem().toString());
//                board.reinitBoard(numberOfQueens);
//                initLabelState();
//			}
//		});
	}
}
