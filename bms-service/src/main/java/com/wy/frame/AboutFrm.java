package com.wy.frame;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * 关于
 * @author paradiseWy
 */
public class AboutFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AboutFrm frame = new AboutFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AboutFrm() {
		getContentPane().setBackground(new Color(245, 222, 179));
		setFrameIcon(new ImageIcon(AboutFrm.class.getResource("/images/aboutMe.jpg")));
		setClosable(true);
		setIconifiable(true);
		setTitle("关于我");
		try {
			UIManager.setLookAndFeel(lookAndFeel_win);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 221, 377);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AboutFrm.class.getResource("/images/jz.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(label)
						.addContainerGap(118, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout
						.createSequentialGroup().addContainerGap().addComponent(label,
								GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(85, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}
}