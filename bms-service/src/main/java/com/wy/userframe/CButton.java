package com.wy.userframe;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	public CButton(String text) {
		setText(text);
		setToolTipText(text);
	}
	
	public CButton(String text,String iconPath) {
		this(text,iconPath,null);
	}
	
	public CButton(String text,String iconPath,ActionListener l) {
		setText(text);
		setToolTipText(text);
		setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		addActionListener(l);
	}
}