//package com.wy.frame;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.awt.SystemColor;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.ImageIcon;
//import javax.swing.JDesktopPane;
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.UIManager;
//import javax.swing.border.EmptyBorder;
//
//public class HomeFrm extends JFrame {
//	private static final long serialVersionUID = 1L;
//	
//	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//	private JPanel contentPane;
//	private JDesktopPane desktop = null;
//	
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HomeFrm frame = new HomeFrm();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public HomeFrm() {
//		setIconImage(Toolkit.getDefaultToolkit().getImage(HomeFrm.class.getResource("/images/frmicon.png")));
//		setTitle("图书管理系统");
//		try {
//			UIManager.setLookAndFeel(lookAndFeel_win);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 572, 442);
//		
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//		
//		JMenu menu = new JMenu("基本数据维护");
//		menu.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/base.png")));
//		menuBar.add(menu);
//		
//		JMenu menu_2 = new JMenu("图书类别管理");
//		menu_2.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/bookTypeManager.png")));
//		menu.add(menu_2);
//		
//		JMenuItem menuItem = new JMenuItem("图书类别添加");
//		menuItem.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BooktypeAddFrm bookTypeAddInterFrm = new BooktypeAddFrm();
//				bookTypeAddInterFrm.setVisible(true);
//				desktop.add(bookTypeAddInterFrm);
//			}
//		});
//		menuItem.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/add.png")));
//		menu_2.add(menuItem);
//		
//		JMenuItem menuItem_1 = new JMenuItem("图书类别维护");
//		menuItem_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BooktypeFrm bookTypeManageInterFrm = new BooktypeFrm();
//				bookTypeManageInterFrm.setVisible(true);
//				desktop.add(bookTypeManageInterFrm);
//			}
//		});
//		menuItem_1.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/edit.png")));
//		menu_2.add(menuItem_1);
//		
//		JMenu menu_3 = new JMenu("图书管理");
//		menu_3.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/bookManager.png")));
//		menu.add(menu_3);
//		
//		JMenuItem menuItem_2 = new JMenuItem("图书添加");
//		menuItem_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BookAddFrm bookAddInterFrm = new BookAddFrm();
//				bookAddInterFrm.setVisible(true);
//				desktop.add(bookAddInterFrm);
//			}
//		});
//		menuItem_2.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/add.png")));
//		menu_3.add(menuItem_2);
//		
//		JMenuItem menuItem_4 = new JMenuItem("图书维护");
//		menuItem_4.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BookFrm bookManageInterFrm = new BookFrm();
//				bookManageInterFrm.setVisible(true);
//				desktop.add(bookManageInterFrm);
//			}
//		});
//		menuItem_4.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/edit.png")));
//		menu_3.add(menuItem_4);
//		
//		JMenuItem menuItem_3 = new JMenuItem(" 安全退出");
//		menuItem_3.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int result = JOptionPane.showConfirmDialog(null, "退出图书管理系统？","确定退出", 
//						JOptionPane.OK_CANCEL_OPTION);
//				if(result == 0) {
//					dispose(); //销毁窗体，安全退出
//				}
//			}
//		});
//		menuItem_3.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/exit.png")));
//		menu.add(menuItem_3);
//		
//		JMenu menu_1 = new JMenu("关于我");
//		menu_1.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/about.png")));
//		menuBar.add(menu_1);
//		
//		JMenuItem mntmjustzero = new JMenuItem("关于我");
//		mntmjustzero.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				AboutFrm aboutFrm = new AboutFrm();
//				aboutFrm.setVisible(true);
//				desktop.add(aboutFrm);
//			}
//		});
//		mntmjustzero.setIcon(new ImageIcon(HomeFrm.class.getResource("/images/aboutMe.jpg")));
//		menu_1.add(mntmjustzero);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(new BorderLayout(0, 0));
//		
//		desktop = new JDesktopPane();
//		desktop.setBackground(SystemColor.menu);
//		contentPane.add(desktop, BorderLayout.CENTER);
//		
//		setExtendedState(JFrame.MAXIMIZED_BOTH); //设置JFrame初始状态为最大化
//	}
//}