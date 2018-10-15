package com.wy.userframe;

import java.awt.Font;

import javax.swing.JTextField;

public class CTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public CTextField(String tipText) {
		this(tipText, "微软雅黑");
	}

	public CTextField(String tipText, String fontFamily) {
		this(tipText, fontFamily, Font.PLAIN);
	}

	public CTextField(String tipText, int fontSize) {
		this(tipText, "微软雅黑", Font.PLAIN, fontSize);
	}

	public CTextField(String tipText, int fontStyle, int fontSize) {
		this(tipText, "微软雅黑", fontStyle, fontSize);
	}

	public CTextField(String tipText, String fontFamily, int fontStyle) {
		this(tipText, fontFamily, fontStyle, 12);
	}

	public CTextField(String tipText, String fontFamily, int fontStyle, int fontSize) {
		this(tipText, fontFamily, fontStyle, fontSize, 16);
	}

	public CTextField(String tipText, String fontFamily, int fontStyle, int fontSize, int columns) {
		setToolTipText(tipText);
		setFont(new Font(fontFamily, fontStyle, fontSize));
		setColumns(columns);
	}

	public CTextField(String tipText, Font font) {
		setToolTipText(tipText);
		setFont(font);
	}
}