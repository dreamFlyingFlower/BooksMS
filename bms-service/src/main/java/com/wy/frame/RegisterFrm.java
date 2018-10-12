package com.wy.frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.wy.common.SpringContext;
import com.wy.entity.User;
import com.wy.service.UserService;
import com.wy.userframe.ResetButton;
import com.wy.utils.StrUtils;

public class RegisterFrm extends JFrame {
	private static final long serialVersionUID = 1L;

	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private JPanel contentPane;
	private JTextField usernameTxt;
	private JPasswordField passwordTxt;
	private JPasswordField checkpasswordTxt;

	private UserService userService;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				RegisterFrm registerFrm = new RegisterFrm();
				registerFrm.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterFrm() {
		userService = (UserService) SpringContext.getBean("userService");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(RegisterFrm.class.getResource("/images/leo.jpg")));
		setTitle("新用户注册");
		try {
			UIManager.setLookAndFeel(lookAndFeel_win);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("图书管理系统");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 23));
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/images/logo.png")));

		JLabel lblNewLabel_1 = new JLabel("新用户名：");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/images/userName.png")));

		JLabel lblNewLabel_2 = new JLabel("密 码：");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("/images/password.png")));

		usernameTxt = new JTextField();
		usernameTxt.setToolTipText("请输入用户名");
		usernameTxt.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		usernameTxt.setColumns(10);

		passwordTxt = new JPasswordField();
		passwordTxt.setToolTipText("请输入密码");
		passwordTxt.setFont(new Font("微软雅黑", Font.PLAIN, 9));
		passwordTxt.setColumns(16);
		
		ResetButton reset = new ResetButton(this);

		JButton registerBtn = new JButton("注册");
		registerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerActionPerformed(e);
			}
		});
		registerBtn.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/leo.jpg")));
		registerBtn.setToolTipText("注册");

		JButton loginBtn = new JButton("登录");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toLoginActionPerformed(e);
			}
		});
		loginBtn.setToolTipText("登录");
		loginBtn.setIcon(new ImageIcon(RegisterFrm.class.getResource("/images/login.png")));

		JLabel label = new JLabel("确认密码：");
		label.setIcon(new ImageIcon(RegisterFrm.class.getResource("/images/password.png")));
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		checkpasswordTxt = new JPasswordField();
		checkpasswordTxt.setToolTipText("请再次输入密码");
		checkpasswordTxt.setFont(new Font("微软雅黑", Font.PLAIN, 9));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(81)
						.addComponent(lblNewLabel).addContainerGap(105, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(66)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(registerBtn).addGap(18)
										.addComponent(loginBtn, GroupLayout.DEFAULT_SIZE, 79,
												Short.MAX_VALUE)
										.addGap(18).addComponent(reset))
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(lblNewLabel_1).addComponent(label)
												.addComponent(lblNewLabel_2))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_contentPane
												.createParallelGroup(Alignment.LEADING)
												.addComponent(checkpasswordTxt,
														GroupLayout.DEFAULT_SIZE, 185,
														Short.MAX_VALUE)
												.addComponent(passwordTxt, GroupLayout.DEFAULT_SIZE,
														185, Short.MAX_VALUE)
												.addComponent(usernameTxt, GroupLayout.DEFAULT_SIZE,
														185, Short.MAX_VALUE))))
						.addGap(57)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblNewLabel).addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1).addComponent(usernameTxt,
										GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(checkpasswordTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(33)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(registerBtn).addComponent(reset)
								.addComponent(loginBtn))
						.addGap(68)));
		contentPane.setLayout(gl_contentPane);

		this.setLocationRelativeTo(null); // 设置窗体启动位置居中
	}

	/**
	 * 跳转登录界面事件处理
	 * @param evt
	 */
	private void toLoginActionPerformed(ActionEvent evt) {
		this.dispose();
		new LoginFrm().setVisible(true);
	}

	/**
	 * 注册事件处理
	 * @param evt
	 */
	private void registerActionPerformed(ActionEvent evt) {
		String username = this.usernameTxt.getText();
		String password = String.valueOf(this.passwordTxt.getPassword());
		String checkpassword = String.valueOf(this.checkpasswordTxt.getPassword());
		if (StrUtils.isBlank(username)) {
			JOptionPane.showMessageDialog(null, "用户名不能为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (StrUtils.isBlank(password)) {
			JOptionPane.showMessageDialog(null, "密码不能为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (StrUtils.isBlank(checkpassword) || !checkpassword.equals(password)) {
			JOptionPane.showMessageDialog(null, "两次密码输入不匹配！", "警告",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		try {
			User result = userService.add(user);
			if (!Objects.isNull(result)) {
				this.resetValue();
				JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "注册失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "注册异常，请稍后重试！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	private void resetValue() {
		this.usernameTxt.setText("");
		this.passwordTxt.setText("");
	}
}