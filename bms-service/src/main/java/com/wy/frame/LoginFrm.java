package com.wy.frame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.wy.common.SpringContext;
import com.wy.entity.User;
import com.wy.enums.SwingTips;
import com.wy.service.UserService;
import com.wy.userframe.CButton;
import com.wy.userframe.CLabel;
import com.wy.userframe.CTextField;
import com.wy.userframe.ResetButton;
import com.wy.utils.StrUtils;

public class LoginFrm extends JFrame {
	private static final long serialVersionUID = 1L;

	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"; // 观感
	private JPanel contentPane;
	private JTextField userNameText;
	private JPasswordField passwordText;

	private UserService userService;

	/**
	 * 仅为测试用
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				LoginFrm loginFrm = new LoginFrm();
				loginFrm.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrm() {
		userService = (UserService) SpringContext.getBean("userService");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(LoginFrm.class.getResource("/images/leo.jpg")));
		setTitle("管理员登录");
		try {
			UIManager.setLookAndFeel(lookAndFeel_win);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 418, 355);
		setFocusable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setFocusable(true);

		CLabel l_title = new CLabel(getClass().getClassLoader().getResource("images/logo.png"),
				"图书管理系统", Font.BOLD, 23);
		CLabel l_username = new CLabel(
				getClass().getClassLoader().getResource("images/userName.png"), "用户名：");
		CLabel l_pwd = new CLabel(getClass().getClassLoader().getResource("images/password.png"),
				"密 码：");
		userNameText = new CTextField("请输入用户名");
		passwordText = new JPasswordField();
		passwordText.setToolTipText("请输入密码");
		passwordText.setFont(new Font("微软雅黑", Font.PLAIN, 9));

		CButton loginBtn = new CButton("登录", "images/login.png", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction(e);
			}
		});
		CButton registerBtn = new CButton("注册", "images/leo.jpg", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registerAction(e);
			}
		});
		ResetButton resetBtn = new ResetButton(this);

		GroupLayout layout = new GroupLayout(contentPane);
		/**
		 * group必须设置一个水平和垂直的group,
		 * 水平的group会将里面的元素以createSequentialGroup为串行(左右),createParallelGroup并行(上下)
		 * 垂直的group会将里面的元素以createSequentialGroup为并行(上下),createParallelGroup串行(左右)
		 * 垂直的group将水平的group分组,并排列城多行,越先添加的在越上面
		 */
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(83).addComponent(l_title)
						.addContainerGap(148, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup().addGap(66)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addComponent(l_pwd)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(passwordText, GroupLayout.DEFAULT_SIZE, 167,
												Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup().addComponent(l_username)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(userNameText, GroupLayout.DEFAULT_SIZE, 186,
												Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup().addComponent(loginBtn)
										.addGap(29).addComponent(registerBtn).addGap(18)
										.addComponent(resetBtn)))
						.addGap(57)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(23).addComponent(l_title).addGap(33)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(l_username).addComponent(userNameText,
										GroupLayout.PREFERRED_SIZE, 19, Short.MAX_VALUE))
						.addGap(37)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(l_pwd)
								.addComponent(passwordText, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(44)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(loginBtn).addComponent(registerBtn)
								.addComponent(resetBtn))
						.addGap(70)));
		contentPane.setLayout(layout);

		this.setLocationRelativeTo(null); // 设置窗体启动位置居中
	}

	/**
	 * 跳转注册界面事件处理
	 * @param evt
	 */
	private void registerAction(ActionEvent evt) {
		this.dispose();
		new RegisterFrm().setVisible(true);
	}

	/**
	 * 登录事件处理
	 * @param evt
	 */
	private void loginAction(ActionEvent evt) {
		String username = this.userNameText.getText();
		String password = String.valueOf(this.passwordText.getPassword());
		if (StrUtils.isBlank(username)) {
			SwingTips.WARN.propmpt("用户名不能为空！");
			return;
		}
		if (StrUtils.isBlank(password)) {
			SwingTips.WARN.propmpt("密码不能为空！");
			return;
		}

		try {
			User user = userService.login(username, password);
			if (user != null) {
				this.dispose(); // 销毁窗体
				new HomeFrm().setVisible(true); // 创建主窗体并设置为可见
			}
		} catch (Exception e) {
			e.printStackTrace();
			SwingTips.ERROR.propmpt(e.getMessage());
		}
	}
}