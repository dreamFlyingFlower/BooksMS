package com.wy.userframe;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;

/**
 * 获得一个界面上所有的输入控件,然后将其置空
 * @author paradiseWy
 *
 */
public class ResetButton extends JButton {

	private static final long serialVersionUID = 1L;

	JFrame jf = null;

	public ResetButton(JFrame jf) {
		this(jf, "重置", true);
	}

	public ResetButton(JFrame jf, String text) {
		this(jf, text, true);
	}

	public ResetButton(JFrame jf, String text, boolean showToolTip) {
		this(jf, text, showToolTip, text);
	}

	public ResetButton(JFrame jf, String text, boolean showToolTip, String toolTip) {
		this(jf, text, true, text, "images/reset.png");
	}

	/**
	 * 新建一个重置文本的按钮
	 * @param jf 主控件
	 * @param text 按钮名称
	 * @param showToolTip 鼠标放到按钮上是否显示文字
	 * @param toolTip 显示自定义文字
	 * @param iconPath 重置图标路径,必须是本项目内
	 */
	public ResetButton(JFrame jf, String text, boolean showToolTip, String toolTip,
			String iconPath) {
		this.jf = jf;
		setText(text);
		if (showToolTip) {
			setToolTipText(toolTip);
		}
		setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		addActionListener(addActionListener());
	}

	/**
	 * 重置动作,只重置文本内容
	 * @return
	 */
	private ActionListener addActionListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<JTextComponent> texts = getTexts();
				for (JTextComponent text : texts) {
					text.setText("");
				}
			}
		};
	}

	/**
	 * 获得jframe上所有的文本输入控件
	 * @return
	 */
	private List<JTextComponent> getTexts() {
		Component[] components = jf.getRootPane().getContentPane().getComponents();
		List<JTextComponent> jtcs = new ArrayList<>();
		for (Component c : components) {
			if (c instanceof JTextComponent) {
				jtcs.add((JTextComponent) c);
			}
		}
		return jtcs;
	}
}