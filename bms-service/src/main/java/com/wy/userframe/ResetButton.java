package com.wy.userframe;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.text.JTextComponent;

/**
 * 获得一个界面上所有的输入控件,然后将其置空
 * @author paradiseWy
 */
public class ResetButton extends JButton {

	private static final long serialVersionUID = 1L;

	Container container = null;

	public ResetButton(Container container) {
		this(container, "重置", true);
	}

	public ResetButton(Container container, String text) {
		this(container, text, true);
	}

	public ResetButton(Container container, String iconPath, ActionListener l) {
		this.container = container;
		setText("重置");
		setToolTipText("重置");
		setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		addActionListener(l);
	}

	public ResetButton(Container container, String text, boolean showToolTip) {
		this(container, text, showToolTip, text);
	}

	public ResetButton(Container container, String text, boolean showToolTip, String toolTip) {
		this(container, text, true, text, "images/reset.png");
	}

	/**
	 * 新建一个重置文本的按钮
	 * @param jf 主控件
	 * @param text 按钮名称
	 * @param showToolTip 鼠标放到按钮上是否显示文字
	 * @param toolTip 显示自定义文字
	 * @param iconPath 重置图标路径,必须是本项目内
	 */
	public ResetButton(Container container, String text, boolean showToolTip, String toolTip,
			String iconPath) {
		this.container = container;
		setText(text);
		if (showToolTip) {
			setToolTipText(toolTip);
		}
		setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		addActionListener(addActionListener());
	}

	/**
	 * 自定义按钮事件
	 * @param jf 主控件
	 * @param text 按钮名称
	 * @param showToolTip 鼠标放到按钮上是否显示文字
	 * @param toolTip 显示自定义文字
	 * @param iconPath 重置图标路径,必须是本项目内
	 * @param l 自定义事件
	 */
	public ResetButton(Container container, String text, boolean showToolTip, String toolTip,
			String iconPath, ActionListener l) {
		this.container = container;
		setText(text);
		if (showToolTip) {
			setToolTipText(toolTip);
		}
		setIcon(new ImageIcon(getClass().getClassLoader().getResource(iconPath)));
		addActionListener(l);
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
		Component[] components = container.getComponents();
		List<JTextComponent> jtcs = new ArrayList<>();
		for (Component c : components) {
			if (c instanceof JTextComponent) {
				jtcs.add((JTextComponent) c);
			}
		}
		return jtcs;
	}
}