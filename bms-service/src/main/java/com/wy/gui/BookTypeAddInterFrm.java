package com.wy.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.annotation.Autowired;

import com.wy.entity.Booktype;
import com.wy.service.BooktypeService;
import com.wy.utils.StrUtils;

public class BookTypeAddInterFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private JTextField bookTypeNameTxt;
	private JTextArea bookTypeDescTxt;

	@Autowired
	private BooktypeService booktypeService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeAddInterFrm frame = new BookTypeAddInterFrm();
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
	public BookTypeAddInterFrm() {
		getContentPane().setFont(new Font("微软雅黑", Font.PLAIN, 12));
		setFrameIcon(new ImageIcon(BookTypeAddInterFrm.class.getResource("/images/leo.jpg")));
		setClosable(true);
		setIconifiable(true);
		setTitle("图书类别添加");
		try {
			UIManager.setLookAndFeel(lookAndFeel_win);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 476, 362);

		JLabel label = new JLabel("图书类别名：");

		JLabel label_1 = new JLabel("图书类别描述：");

		bookTypeNameTxt = new JTextField();
		bookTypeNameTxt.setToolTipText("请输入图书类别名");
		bookTypeNameTxt.setColumns(10);

		bookTypeDescTxt = new JTextArea();
		bookTypeDescTxt.setToolTipText("请添加图书类别描述");

		JButton addBtn = new JButton("添加");
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookTypeAddActionPerformed(e);
			}
		});
		addBtn.setIcon(new ImageIcon(BookTypeAddInterFrm.class.getResource("/images/add.png")));

		JButton resetBtn = new JButton("重置");
		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetValueActionPerformed(e);
			}
		});
		resetBtn.setIcon(new ImageIcon(BookTypeAddInterFrm.class.getResource("/images/reset.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(84)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(addBtn)
										.addPreferredGap(ComponentPlacement.RELATED, 141,
												Short.MAX_VALUE)
										.addComponent(resetBtn))
								.addGroup(groupLayout.createSequentialGroup().addComponent(label_1)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(bookTypeDescTxt, GroupLayout.PREFERRED_SIZE,
												207, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addComponent(label)
										.addGap(18).addComponent(bookTypeNameTxt,
												GroupLayout.PREFERRED_SIZE, 205,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(81, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(54)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(bookTypeNameTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(53)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1).addComponent(bookTypeDescTxt,
										GroupLayout.PREFERRED_SIZE, 105,
										GroupLayout.PREFERRED_SIZE))
						.addGap(38)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(addBtn).addComponent(resetBtn))
						.addContainerGap(37, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);

		// 设置文本区域边框
		bookTypeDescTxt.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));

	}

	/**
	 * 图书类别添加事件处理
	 * @param evt
	 */
	private void bookTypeAddActionPerformed(ActionEvent evt) {
		String booktypeName = bookTypeNameTxt.getText();
		String booketypeDesc = bookTypeDescTxt.getText();
		if (StrUtils.isBlank(booktypeName)) {
			JOptionPane.showMessageDialog(null, "图书类别名称不可为空！", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Booktype bookType = Booktype.builder().booktypeName(booktypeName)
				.booketypeDesc(booketypeDesc).build();
		try {
			if (booktypeService.hasValue("booktypeName", booktypeName)) {
				resetValue();
				JOptionPane.showMessageDialog(null, "图书类别已存在", "提示",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Booktype result = booktypeService.add(bookType);
			if (!Objects.isNull(result)) {
				resetValue();
				JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "添加失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "添加异常，请稍后重试！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 重置事件处理
	 * @param evt
	 */
	private void resetValueActionPerformed(ActionEvent evt) {
		this.resetValue();
	}

	/**
	 * 重置表单数据
	 */
	private void resetValue() {
		this.bookTypeNameTxt.setText("");
		this.bookTypeDescTxt.setText("");
	}
}
