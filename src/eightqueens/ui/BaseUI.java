package eightqueens.ui;

import java.awt.*;
import javax.swing.*;

import eightqueens.util.ImageUtil;
import eightqueens.util.Result;
import eightqueens.util.Util;

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
	
	protected void initData() {
		letter = new String[numberOfQueens];
        for (int i = 0; i < numberOfQueens; i++)
        	letter[i] = (char)(65 + i) + "";
	}
	
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {};

	/**
	 * Components
	 */
	protected JLabel lblTitleGame;
	
//	private Board board;
	protected JPanel pnBoard;
	protected JComboBox<Integer> cboNumber;

	protected JPanel pnInspectQueen;
	protected JLabel lblRow, lblCol;
    protected JLabel lblRowVal, lblColVal;

    protected JPanel pnSolution;
    protected JLabel lblRowSolution[];
    protected JLabel lblColSolution[];
    protected int labelSolutionSize = 30;
    
    protected JPanel pnBoardCustomization;
    protected JButton btnBgWood;
    protected JButton btnBgBlue;
    protected JButton btnBgGreen;
    protected JButton btnCustomBg;
    protected JButton btnBasicQueen;
    protected JButton btnFlatQueen;
    protected JButton btnAeroQueen;
    
    protected JCheckBox cbShowSafeCell;
    protected JLabel lblColorSafeCell;

    /**
     * Measurement
     */
    public static final int CONTROL_WIDTH = 100;
    public static final int CONTROL_HEIGHT = 25;
    protected Color colorBtn = new Color(43, 87, 154);
    
    protected static final Color FG_SUCCESS = new Color(0, 200, 0);
    protected static final Color FG_DANGER = new Color(255, 0, 0);

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
		
		lblTitleGame = new JLabel("Giải bài toán Tám quân hậu");
		lblTitleGame.setForeground(SystemColor.textHighlight);
		lblTitleGame.setHorizontalAlignment(JLabel.CENTER);
		lblTitleGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTitleGame.setBounds(10, 10, 775, 34);
		getContentPane().add(lblTitleGame);
		
		initPanelBoardCustomization();
		initPanelBoardData();
        initPanelInspectQueen();
        initPanelSolution();
    }
	
	private void initPanelBoardCustomization() {
		pnBoardCustomization = new JPanel(null);
		pnBoardCustomization.setBorder(new TitledBorder(null, "Tùy biến", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnBoardCustomization.setBounds(550, 55, 234, 178);
		
		JLabel lblColor = new JLabel("Màu nền");
		lblColor.setBounds(10, 22, 100, 15);
		pnBoardCustomization.add(lblColor);
		
		btnBgWood = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "wood_bg.png")));
		btnBgBlue = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "blue_bg.png")));
		btnBgGreen = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "green_bg.png")));
		btnCustomBg = new JButton("Chọn màu");
		btnCustomBg.setContentAreaFilled(false);
		btnCustomBg.setOpaque(false);
		btnBgWood.setBounds(10, 42, 25, 25);
		btnBgBlue.setBounds(47, 42, 25, 25);
		btnBgGreen.setBounds(84, 42, 25, 25);
		btnCustomBg.setBounds(121, 42, 91, 25);
		pnBoardCustomization.add(btnBgWood);
		pnBoardCustomization.add(btnBgBlue);
		pnBoardCustomization.add(btnBgGreen);
		pnBoardCustomization.add(btnCustomBg);
		
		
		JLabel lblQueen = new JLabel("Kiểu quân hậu");
		lblQueen.setBounds(10, 79, 100, 15);
		pnBoardCustomization.add(lblQueen);
		
		btnFlatQueen = new JButton(ImageUtil.getImageIconWithSize("flat_queen.png", 30));
		btnFlatQueen.setContentAreaFilled(false);
		btnFlatQueen.setOpaque(false);
		btnAeroQueen = new JButton(ImageUtil.getImageIconWithSize("aero_queen.png", 30));
		btnAeroQueen.setOpaque(false);
		btnAeroQueen.setContentAreaFilled(false);
		btnBasicQueen = new JButton(ImageUtil.getImageIconWithSize("basic_queen.png", 30));
		btnBasicQueen.setOpaque(false);
		btnBasicQueen.setContentAreaFilled(false);
		btnBasicQueen.setBounds(10, 99, 30, 30);
		btnFlatQueen.setBounds(52, 99, 30, 30);
		btnAeroQueen.setBounds(94, 99, 30, 30);
		pnBoardCustomization.add(btnBasicQueen);
		pnBoardCustomization.add(btnFlatQueen);
		pnBoardCustomization.add(btnAeroQueen);
		
		cbShowSafeCell = new JCheckBox("Tô màu các ô an toàn");
		cbShowSafeCell.setBounds(10, 142, 151, 20);
		lblColorSafeCell = new JLabel();
		lblColorSafeCell.setEnabled(false);
		lblColorSafeCell.setBackground(new Color(145, 216, 145));
		lblColorSafeCell.setOpaque(true);
		lblColorSafeCell.setBounds(163, 140, 25, 25);
		pnBoardCustomization.add(lblColorSafeCell);
		pnBoardCustomization.add(cbShowSafeCell);
		
		
		getContentPane().add(pnBoardCustomization);
	}

    private void initPanelBoardData() {
        pnBoard = new JPanel();
        pnBoard.setLayout(null);
        pnBoard.setBounds(550, 245, 230, 90);
        pnBoard.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "B\u00E0n c\u1EDD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        getContentPane().add(pnBoard);

        JLabel lblNum = new JLabel("Chọn số quân hậu");
        lblNum.setBounds(12, 27, 206, 16);
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

    @SuppressWarnings("unused")
	private void changeButtonAppearance(JButton btn){
//	    btn.setOpaque(true);
//	    btn.setForeground(Color.white);
//	    btn.setBackground(btnColor);
//	    btn.setBorderPainted(false);
//	    btn.setFocusPainted(false);
    }

    private void initPanelInspectQueen(){
        pnInspectQueen = new JPanel();
        pnInspectQueen.setLayout(null);
        pnInspectQueen.setBounds(550, 474, 230, 74);
        pnInspectQueen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(pnInspectQueen);

        lblRow = new JLabel("Duyá»‡t dÃ²ng: ");
        lblRow.setBounds(10, 5, 120, 25);
        pnInspectQueen.add(lblRow);

        lblCol = new JLabel("Duyá»‡t cá»™t: ");
        lblCol.setBounds(10, 35, 120, 25);
        pnInspectQueen.add(lblCol);

        lblRowVal = new JLabel("0");
        lblRowVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblRowVal.setBounds(130, 5, 20, 25);
        pnInspectQueen.add(lblRowVal);

        lblColVal = new JLabel("0");
        lblColVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblColVal.setBounds(130, 35, 20, 25);
        pnInspectQueen.add(lblColVal);        
    }

    private void initPanelSolution(){
        pnSolution = new JPanel();
        pnSolution.setLayout(null);
        pnSolution.setBounds(10,  576, 775, 84);
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
    	System.out.println(lblColSolution.length);
    }
    
    public void changeLabelSolutionAppearance(Result result) {
    	if (result == Result.FOUND) {
    		for (JLabel jLabel : lblColSolution) {
				jLabel.setForeground(FG_SUCCESS);
			}
    	} else 
    		if (result == Result.FAILED) {
    			for (JLabel jLabel : lblColSolution) {
    				jLabel.setForeground(FG_DANGER);
    			}
    		} else {
    			for (JLabel jLabel : lblColSolution) {
    				jLabel.setForeground(Color.black);
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
