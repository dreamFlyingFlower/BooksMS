package com.wy.enums;

import javax.swing.JOptionPane;

import com.wy.utils.StrUtils;

public enum SwingTips {

	PLAIN("注意", JOptionPane.PLAIN_MESSAGE),
	INFO("注意", JOptionPane.INFORMATION_MESSAGE),
	QUESTION("疑问", JOptionPane.QUESTION_MESSAGE),
	WARN("警告", JOptionPane.WARNING_MESSAGE),
	ERROR("错误", JOptionPane.ERROR_MESSAGE);

	private String tip;
	private int type;

	SwingTips(String tip, int type) {
		this.tip = tip;
		this.type = type;
	}

	public void propmpt(String msg) {
		JOptionPane.showMessageDialog(null, StrUtils.isBlank(msg) ? "系统错误,请联系管理员!" : msg, tip,
				type);
	}
}