package com.wy.userframe;

import java.awt.Font;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建一个自定义按钮,字体默认微软雅黑,样式无,大小12,无图片
	 * @param text 按钮显示的文字
	 */
	public CLabel(String text) {
		this(text, "微软雅黑");
	}

	/**
	 * 创建一个自定义按钮,字体默认微软雅黑,样式无,大小12
	 * @param url 按钮标签的图片地址
	 * @param text 按钮显示的文字
	 */
	public CLabel(URL url, String text) {
		this(url, text, "微软雅黑");
	}

	/**
	 * 创建一个自定义按钮,字体默认微软雅黑,样式无,大小12,无图片
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 */
	public CLabel(String text, String fontFamily) {
		this(text, fontFamily, Font.PLAIN);
	}

	/**
	 * 创建一个自定义按钮,字体默认微软雅黑,样式无,大小12,默认图片label.png
	 * @param url 按钮标签的图片地址
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 */
	public CLabel(URL url, String text, String fontFamily) {
		this(url, text, fontFamily, Font.PLAIN);
	}

	/**
	 * 创建一个自定义的按钮,默认类型微软雅黑
	 * @param text 按钮显示的文字
	 * @param fontStyle 字体的样式,斜体加粗等
	 * @param fontSize 字体的大小
	 */
	public CLabel(String text, int fontStyle, int fontSize) {
		this(text, "微软雅黑", fontStyle, fontSize);
	}

	/**
	 * 创建一个自定义的按钮,默认类型微软雅黑
	 * @param url 按钮标签的图片地址
	 * @param text 按钮显示的文字
	 * @param fontStyle 字体的样式,斜体加粗等
	 * @param fontSize 字体的大小
	 */
	public CLabel(URL url, String text, int fontStyle, int fontSize) {
		this(url, text, "微软雅黑", fontStyle, fontSize);
	}

	/**
	 * 创建一个自定义的按钮,字体默认是12
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 * @param fontStyle 字体的样式,斜体加粗等
	 */
	public CLabel(String text, String fontFamily, int fontStyle) {
		this(text, fontFamily, fontStyle, 12);
	}

	/**
	 * 创建一个自定义的按钮,字体默认是12
	 * @param url 按钮标签的图片地址
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 * @param fontStyle 字体的样式,斜体加粗等
	 */
	public CLabel(URL url, String text, String fontFamily, int fontStyle) {
		this(url, text, fontFamily, fontStyle, 12);
	}

	/**
	 * 创建一个自定义的按钮
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 * @param fontStyle 字体的样式,斜体加粗等
	 * @param fontSize 文字的大小
	 */
	public CLabel(String text, String fontFamily, int fontStyle, int fontSize) {
		setText(text);
		setToolTipText(text);
		setFont(new Font(fontFamily, fontStyle, fontSize));
	}

	/**
	 * 创建一个自定义的按钮
	 * @param url 按钮标签的图片地址
	 * @param text 按钮显示的文字
	 * @param fontFamily 文字的类型
	 * @param fontStyle 字体的样式,斜体加粗等
	 * @param fontSize 文字的大小
	 */
	public CLabel(URL url, String text, String fontFamily, int fontStyle, int fontSize) {
		setText(text);
		setToolTipText(text);
		setFont(new Font(fontFamily, fontStyle, fontSize));
		setIcon(new ImageIcon(url));
	}

	/**
	 * 创建一个自定义的按钮
	 * @param text 按钮显示的文字
	 * @param font 字体
	 */
	public CLabel(String text, Font font) {
		setText(text);
		setToolTipText(text);
		setFont(font);
	}
}