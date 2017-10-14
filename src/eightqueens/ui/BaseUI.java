package eightqueens.ui;

import java.awt.*;
import javax.swing.*;

import eightqueens.util.Config;
import eightqueens.util.ImageUtil;
import eightqueens.util.Result;
import eightqueens.util.Util;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class BaseUI extends JFrame {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 720;

    protected String letter[] = new String[Config.totalQueen];
	
	public BaseUI() {
		initUI();
		initComponents();
		initActions();
		initData();
	}
	
	protected void initData() {
		letter = new String[Config.totalQueen];
        for (int i = 0; i < Config.totalQueen; i++)
        	letter[i] = (char)(65 + i) + "";
	}
	
	public void updateCurrentBoardStateInUI(int row, int col, boolean valid) {};

	/**
	 * Components
	 */
	protected JLabel lblTitleGame;
	
//	private Board board;
	protected JPanel pnBoardOperation;
	protected JComboBox<Integer> cboNumber;

	protected JPanel pnInspectQueen;
	protected JLabel lblRow, lblCol;
    protected JLabel lblRowVal, lblColVal;

    protected JPanel pnSolution;
    protected JLabel lblRowSolution[];
    protected JLabel lblColSolution[];
    protected int labelSolutionSize = 35;
    protected int labelSolutionFontSize = 18;
    
    protected JPanel pnBoardCustomization;
    protected JButton btnBgWood;
    protected JButton btnBgBlue;
    protected JButton btnBgGreen;
    protected JButton btnCustomBg;
    protected JButton btnBasicQueen;
    protected JButton btnFlatQueen;
    protected JButton btnAeroQueen;
    
    protected JCheckBox cbShowSafeCell;
    protected JCheckBox cbShowDangerCell;
    protected JLabel lblColorSafeCell;

    /**
     * Measurement
     */
    public static final int CONTROL_WIDTH = 100;
    public static final int CONTROL_HEIGHT = 25;
    public static final Color btnBG = new Color(65, 120, 200);
    public static final Color btnFG = Color.white;
    
    protected static final Color BORDER_COLOR = Color.GRAY;
    protected static final Color TEXT_FG = new Color(43, 87, 154);
    
    protected static final Color FG_SUCCESS = TEXT_FG;//new Color(0, 123, 0);
    protected static final Color FG_DANGER = new Color(255, 0, 0);
    
    protected static final Font COMMON_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 12);
    protected static final Font BORDER_TITLE_FONT = new Font("Segoe UI Semibold", Font.PLAIN, 15);
    private JLabel label;
    private JLabel label_1;

	/**
	 * Init GUI
	 */
	private void initUI() {
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Phần mềm giải bài toán Tám quân Hậu");
		this.setResizable(false);
		getContentPane().setLayout(null);
	}

	private void initComponents() {
		this.setIconImage(ImageUtil.getImageIcon("icon.png").getImage());
		
		lblTitleGame = new JLabel("Giải bài toán Tám quân hậu");
		lblTitleGame.setForeground(TEXT_FG);
		lblTitleGame.setHorizontalAlignment(JLabel.CENTER);
		lblTitleGame.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 20));
		lblTitleGame.setBounds(15, 10, 765, 28);
		getContentPane().add(lblTitleGame);
		
		initPanelBoardCustomization();
		initPanelBoardData();
        initPanelInspectQueen();
        initPanelSolution();
    }
	
	private void initPanelBoardCustomization() {
		pnBoardCustomization = new JPanel(null);
		pnBoardCustomization.setBorder(new TitledBorder(new LineBorder(BORDER_COLOR), "Tùy chọn bàn cờ", TitledBorder.LEADING, TitledBorder.TOP, COMMON_FONT, TEXT_FG));
		pnBoardCustomization.setBounds(550, 283, 230, 230);
		
		JLabel lblColor = new JLabel("Màu nền");
		lblColor.setFont(COMMON_FONT);
		lblColor.setBounds(10, 22, 100, 15);
		pnBoardCustomization.add(lblColor);
		
		btnBgWood = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "wood_bg.png")));
		btnBgWood.setBorderPainted(false);
		
		btnBgBlue = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "blue_bg.png")));
		btnBgBlue.setBorderPainted(false);
		
		btnBgGreen = new JButton(new ImageIcon(Util.getResource(Util.IMAGE_FOLDER + "green_bg.png")));
		btnBgGreen.setBorderPainted(false);
		
		btnCustomBg = new JButton("Chọn màu");
		changeButtonAppearance(btnCustomBg, btnBG, btnFG);
		btnBgWood.setBounds(10, 42, 25, 25);
		btnBgBlue.setBounds(47, 42, 25, 25);
		btnBgGreen.setBounds(84, 42, 25, 25);
		btnCustomBg.setBounds(121, 42, 97, 25);
		pnBoardCustomization.add(btnBgWood);
		pnBoardCustomization.add(btnBgBlue);
		pnBoardCustomization.add(btnBgGreen);
		pnBoardCustomization.add(btnCustomBg);
		
		
		JLabel lblQueen = new JLabel("Kiểu quân hậu");
		lblQueen.setFont(COMMON_FONT);
		lblQueen.setBounds(10, 79, 100, 15);
		pnBoardCustomization.add(lblQueen);
		
		btnFlatQueen = new JButton(ImageUtil.getImageIconWithSize("flat_queen.png", 30));
		btnFlatQueen.setBorderPainted(false);
		btnFlatQueen.setContentAreaFilled(false);
		btnFlatQueen.setOpaque(false);
		btnAeroQueen = new JButton(ImageUtil.getImageIconWithSize("aero_queen.png", 30));
		btnAeroQueen.setBorderPainted(false);
		btnAeroQueen.setOpaque(false);
		btnAeroQueen.setContentAreaFilled(false);
		
		btnBasicQueen = new JButton(ImageUtil.getImageIconWithSize("basic_queen.png", 30));
		btnBasicQueen.setBorderPainted(false);
		btnBasicQueen.setOpaque(false);
		btnBasicQueen.setContentAreaFilled(false);
		btnBasicQueen.setBounds(10, 99, 30, 30);
		btnFlatQueen.setBounds(52, 99, 30, 30);
		btnAeroQueen.setBounds(94, 99, 30, 30);
		
		pnBoardCustomization.add(btnBasicQueen);
		pnBoardCustomization.add(btnFlatQueen);
		pnBoardCustomization.add(btnAeroQueen);
		
		cbShowSafeCell = new JCheckBox("Đánh dấu các ô an toàn");
		cbShowSafeCell.setFont(COMMON_FONT);
		cbShowSafeCell.setBounds(10, 142, 170, 20);
		cbShowSafeCell.setSelected(Config.isShowSafeCell);
		lblColorSafeCell = new JLabel();
		lblColorSafeCell.setEnabled(false);
		lblColorSafeCell.setBackground(new Color(145, 216, 145));
		lblColorSafeCell.setOpaque(true);
		lblColorSafeCell.setBounds(185, 140, 25, 25);
		
		pnBoardCustomization.add(lblColorSafeCell);
		pnBoardCustomization.add(cbShowSafeCell);
		
		cbShowDangerCell = new JCheckBox("Đánh dấu các ô nguy hiểm");
		cbShowDangerCell.setFont(COMMON_FONT);
		cbShowDangerCell.setSelected(Config.isShowDangerCell);
		cbShowDangerCell.setBounds(10, 170, 202, 20);
		pnBoardCustomization.add(cbShowDangerCell);		
		
		getContentPane().add(pnBoardCustomization);
	}

    private void initPanelBoardData() {
        pnBoardOperation = new JPanel();
        pnBoardOperation.setLayout(null);
        pnBoardOperation.setBounds(550, 50, 230, 221);
        pnBoardOperation.setBorder(new TitledBorder(new LineBorder(BORDER_COLOR), "Thao t\u00E1c", TitledBorder.LEADING, TitledBorder.TOP, COMMON_FONT, TEXT_FG));
        getContentPane().add(pnBoardOperation);

        JLabel lblNum = new JLabel("Chọn số quân hậu");
        lblNum.setFont(COMMON_FONT);
        lblNum.setBounds(12, 20, 150, 16);
        pnBoardOperation.add(lblNum);
    	cboNumber = new JComboBox<>();
    	cboNumber.setFont(COMMON_FONT);
		cboNumber.setAutoscrolls(true);
		DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
		for (int i = 1; i <= 20; i++)
			if (i != 2 && i != 3)
				model.addElement(i);
		cboNumber.setModel(model);
		cboNumber.setBounds(12, 42, 206, 25);
		cboNumber.setSelectedIndex(5);
		pnBoardOperation.add(cboNumber);
    }

	/**
	 * Khởi tạo panel vị trí quân hậu
	 */
    private void initPanelInspectQueen(){
        pnInspectQueen = new JPanel();
        pnInspectQueen.setVisible(false);
        pnInspectQueen.setLayout(null);
        pnInspectQueen.setBounds(550, 558, 230, 39);
        pnInspectQueen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        getContentPane().add(pnInspectQueen);

        lblRow = new JLabel("Duyệt dòng: ");
        lblRow.setBounds(12, 6, 120, 16);
        pnInspectQueen.add(lblRow);

        lblCol = new JLabel("Duyệt cột: ");
        lblCol.setBounds(12, 23, 120, 11);
        pnInspectQueen.add(lblCol);

        lblRowVal = new JLabel("0");
        lblRowVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblRowVal.setBounds(132, 0, 20, 25);
        pnInspectQueen.add(lblRowVal);

        lblColVal = new JLabel("0");
        lblColVal.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        lblColVal.setBounds(132, 14, 20, 25);
        pnInspectQueen.add(lblColVal);        
    }

    /**
     * Khởi tạo panel hiển thị kết quả
     */
    private void initPanelSolution(){
        pnSolution = new JPanel();
        pnSolution.setFont(new Font("SansSerif", Font.PLAIN, 14));
//        pnSolution.setBorder(new TitledBorder(
//        		new LineBorder(BORDER_COLOR), "V\u1ECB tr\u00ED c\u00E1c qu\u00E2n h\u1EADu", TitledBorder.CENTER, TitledBorder.TOP, 
//        		BORDER_TITLE_FONT, 
//        		new Color(43, 87, 154)
//        		));
        
        pnSolution.setLayout(null);
        pnSolution.setBounds(15,  576, 767, 103);
        getContentPane().add(pnSolution);
        
        JLabel label_2 = new JLabel("");
        label_2.setBorder(new LineBorder(TEXT_FG));
        label_2.setBounds(112, 40, 565, 2);
        getContentPane().add(label_2);

        initLabelState();
    }
    
    protected void initLabelState() {
    	pnSolution.removeAll();
    	JLabel lblCaption = new JLabel("Vị trí các quân hậu");
		lblCaption.setBounds(314, -1, 150, 20);
		lblCaption.setFont(BORDER_TITLE_FONT);
		lblCaption.setHorizontalAlignment(JLabel.CENTER);
		lblCaption.setForeground(TEXT_FG);
		pnSolution.add(lblCaption);
    	
    	label = new JLabel("");
    	label.setBorder(new LineBorder(BORDER_COLOR));
    	label.setBounds(0, 12, 323, 1);
    	pnSolution.add(label);
    	
    	label_1 = new JLabel("");
    	label_1.setBorder(new LineBorder(BORDER_COLOR));
    	label_1.setBounds(455, 12, 310, 1);
    	pnSolution.add(label_1);
		
    	lblColSolution = new JLabel[Config.totalQueen];
    	lblRowSolution = new JLabel[Config.totalQueen];
    	JLabel lblRowCaption = new JLabel("Hàng");
    	JLabel lblColCaption = new JLabel("Cột");
    	
    	int totalWidth = Config.totalQueen*labelSolutionSize;
    	int beginX = (pnSolution.getWidth() - totalWidth)/2 - 10;
    	int beginY = 30;
    	
    	lblRowCaption.setBounds(beginX, beginY, labelSolutionSize, labelSolutionSize);
    	lblRowCaption.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    	lblRowCaption.setHorizontalAlignment(JLabel.CENTER);
    	lblColCaption.setBounds(beginX, beginY + labelSolutionSize - 1, labelSolutionSize, labelSolutionSize);
    	lblColCaption.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    	lblColCaption.setHorizontalAlignment(JLabel.CENTER);
    	pnSolution.add(lblRowCaption);
    	pnSolution.add(lblColCaption);
    	
    	beginX += labelSolutionSize - 1;
    	
    	for (int i = 0; i < Config.totalQueen; i++){
    	    lblRowSolution[i] = new JLabel((i+1) + "");
    	    lblRowSolution[i].setBounds(beginX + i*labelSolutionSize - i, beginY, labelSolutionSize, labelSolutionSize);
    	    lblRowSolution[i].setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    	    lblRowSolution[i].setFont(new Font("SansSerif", Font.PLAIN, labelSolutionFontSize));
    	    lblRowSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    pnSolution.add(lblRowSolution[i]);
    	    
    	    lblColSolution[i] = new JLabel("");	
    	    lblColSolution[i].setHorizontalAlignment(JLabel.CENTER);
    	    lblColSolution[i].setFont(new Font("SansSerif", Font.BOLD, labelSolutionFontSize));
    	    lblColSolution[i].setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    	    lblColSolution[i].setBounds(beginX + i*labelSolutionSize - i, beginY + labelSolutionSize - 1, labelSolutionSize, labelSolutionSize);
    	    pnSolution.add(lblColSolution[i]);
        }
    	pnSolution.revalidate();
    	pnSolution.repaint();
//    	System.out.println(lblColSolution.length);
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
     * Thay đổi kiểu nút
     * @param btn
     */
	public static void changeButtonAppearance(JButton btn, Color bg, Color fg){
		btn.setHorizontalAlignment(JButton.CENTER);
	    btn.setOpaque(true);
	    btn.setForeground(fg);
	    btn.setBackground(bg);
	    btn.setBorderPainted(false);
	    btn.setFocusPainted(false);
	    btn.setFont(COMMON_FONT);
	    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
//	    btn.setBorder(BorderFactory.createLineBorder(fg, 2));
    }
	
	/**
	 * Đổi font hiển thị
	 * @param cpn
	 * @param style
	 * @param size
	 */
	public static void changeFontRenderer(JComponent cpn, int style, int size) {
		cpn.setFont(new Font("SansSerif", style, size));
	}
	
	public void enableButton(JButton btn) {
		btn.setEnabled(true);
		changeButtonAppearance(btn, btnBG, btnFG);
	}
	
	public void disableButton(JButton btn) {
		btn.setEnabled(false);
		changeButtonAppearance(btn, Color.lightGray, Color.DARK_GRAY);
	}

    /**
	 * Init action
	 */
	private void initActions() {
		
	}
}
