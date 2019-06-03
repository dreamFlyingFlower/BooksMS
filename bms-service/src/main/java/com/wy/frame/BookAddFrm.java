//package com.wy.frame;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//import javax.swing.ButtonGroup;
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JInternalFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JRadioButton;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.LayoutStyle.ComponentPlacement;
//import javax.swing.UIManager;
//import javax.swing.border.LineBorder;
//
//import com.wy.entity.Book;
//import com.wy.entity.Booktype;
//import com.wy.page.BooktypePage;
//import com.wy.service.BookService;
//import com.wy.service.BooktypeService;
//import com.wy.userframe.ResetButton;
//import com.wy.utils.SpringContextUtils;
//import com.wy.utils.StrUtils;
//
//public class BookAddFrm extends JInternalFrame {
//	private static final long serialVersionUID = 1L;
//
//	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
//	private JTextField bookNameTxt;
//	private JTextField authorTxt;
//	private JRadioButton manJrb;
//	private JRadioButton femaleJrb;
//	private JRadioButton unknownJrb;
//	private final ButtonGroup buttonGroup = new ButtonGroup();
//	private JTextField priceTxt;
//	private JComboBox<Booktype> bookTypeJcb;
//	private JTextArea bookDescTxt;
//
//	private BooktypeService booktypeService;
//	private BookService bookService;
//
//	/**
//	 * Create the frame.
//	 */
//	public BookAddFrm() {
//		this.bookService = (BookService) SpringContextUtils.getBean("bookService");
//		this.booktypeService = (BooktypeService) SpringContextUtils.getBean("booktypeService");
//
//		setFrameIcon(new ImageIcon(BookAddFrm.class.getResource("/images/leo.jpg")));
//		setClosable(true);
//		setIconifiable(true);
//		setTitle("图书添加");
//		try {
//			UIManager.setLookAndFeel(lookAndFeel_win);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		setBounds(100, 100, 450, 469);
//
//		JLabel label = new JLabel("图书名称：");
//
//		bookNameTxt = new JTextField();
//		bookNameTxt.setColumns(10);
//
//		JLabel label_1 = new JLabel("图书作者：");
//
//		authorTxt = new JTextField();
//		authorTxt.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent e) {
//				authorTxtFocusLost(e);
//			}
//		});
//		authorTxt.setColumns(10);
//
//		JLabel label_2 = new JLabel("作者性别：");
//
//		manJrb = new JRadioButton("男");
//		buttonGroup.add(manJrb);
//		manJrb.setSelected(true);
//
//		femaleJrb = new JRadioButton("女");
//		buttonGroup.add(femaleJrb);
//
//		JLabel label_3 = new JLabel("图书价格：");
//
//		priceTxt = new JTextField();
//		priceTxt.setColumns(10);
//
//		JLabel label_4 = new JLabel("描述：");
//
//		bookDescTxt = new JTextArea();
//		bookDescTxt.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
//
//		JButton button = new JButton("添加");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				bookAddActiionPerformed(e);
//			}
//		});
//		button.setIcon(new ImageIcon(BookAddFrm.class.getResource("/images/add.png")));
//
//		ResetButton button_1 = new ResetButton(this, "images/reset.png", new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				resetValue();
//			}
//		});
//
//		JLabel label_5 = new JLabel("图书类别：");
//
//		bookTypeJcb = new JComboBox<Booktype>();
//
//		unknownJrb = new JRadioButton("不明");
//		buttonGroup.add(unknownJrb);
//		GroupLayout groupLayout = new GroupLayout(getContentPane());
//		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup().addGap(26)
//						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addGroup(
//										groupLayout.createSequentialGroup()
//												.addComponent(button, GroupLayout.PREFERRED_SIZE,
//														100, GroupLayout.PREFERRED_SIZE)
//												.addGap(176).addComponent(
//														button_1, GroupLayout.DEFAULT_SIZE,
//														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
//										.addGroup(groupLayout
//												.createSequentialGroup().addComponent(label_4)
//												.addPreferredGap(
//														ComponentPlacement.RELATED)
//												.addComponent(bookDescTxt))
//										.addGroup(Alignment.LEADING, groupLayout
//												.createSequentialGroup()
//												.addGroup(groupLayout
//														.createParallelGroup(Alignment.LEADING,
//																false)
//														.addGroup(groupLayout
//																.createSequentialGroup()
//																.addComponent(label)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED)
//																.addComponent(
//																		bookNameTxt,
//																		GroupLayout.PREFERRED_SIZE,
//																		113,
//																		GroupLayout.PREFERRED_SIZE))
//														.addGroup(groupLayout
//																.createSequentialGroup()
//																.addComponent(label_2)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED)
//																.addComponent(manJrb)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED)
//																.addComponent(femaleJrb)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED)
//																.addComponent(unknownJrb))
//														.addGroup(groupLayout
//																.createSequentialGroup()
//																.addComponent(label_5)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED)
//																.addComponent(bookTypeJcb, 0,
//																		GroupLayout.DEFAULT_SIZE,
//																		Short.MAX_VALUE)))
//												.addGap(45)
//												.addGroup(groupLayout
//														.createParallelGroup(Alignment.TRAILING)
//														.addComponent(label_1)
//														.addComponent(label_3))
//												.addPreferredGap(ComponentPlacement.RELATED)
//												.addGroup(groupLayout
//														.createParallelGroup(Alignment.LEADING)
//														.addGroup(Alignment.TRAILING, groupLayout
//																.createSequentialGroup()
//																.addComponent(authorTxt,
//																		GroupLayout.PREFERRED_SIZE,
//																		96,
//																		GroupLayout.PREFERRED_SIZE)
//																.addPreferredGap(
//																		ComponentPlacement.RELATED))
//														.addComponent(priceTxt,
//																GroupLayout.PREFERRED_SIZE, 96,
//																GroupLayout.PREFERRED_SIZE)))))
//						.addGap(20)));
//		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup().addGap(29)
//						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//								.addComponent(label)
//								.addComponent(bookNameTxt, GroupLayout.PREFERRED_SIZE,
//										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//								.addComponent(label_1)
//								.addComponent(authorTxt, GroupLayout.PREFERRED_SIZE,
//										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//						.addGap(45)
//						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//								.addComponent(label_2).addComponent(manJrb).addComponent(femaleJrb)
//								.addComponent(priceTxt, GroupLayout.PREFERRED_SIZE,
//										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//								.addComponent(unknownJrb).addComponent(label_3))
//						.addGap(33)
//						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//								.addComponent(label_5).addComponent(bookTypeJcb,
//										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
//										GroupLayout.PREFERRED_SIZE))
//						.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
//						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addComponent(label_4).addComponent(bookDescTxt,
//										GroupLayout.PREFERRED_SIZE, 138,
//										GroupLayout.PREFERRED_SIZE))
//						.addGap(32).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//								.addComponent(button).addComponent(button_1))
//						.addGap(30)));
//		getContentPane().setLayout(groupLayout);
//		this.initBookTypeJcb(new BooktypePage());
//	}
//
//	/**
//	 * 作者文本框焦点失去事件处理
//	 * @param evt
//	 */
//	private void authorTxtFocusLost(FocusEvent evt) {
//		if (StrUtils.isBlank(authorTxt.getText())) {
//			this.unknownJrb.setSelected(true);
//		}
//	}
//
//	/**
//	 * 图书添加事件处理
//	 * @param evt
//	 */
//	private void bookAddActiionPerformed(ActionEvent evt) {
//		String bookName = bookNameTxt.getText();
//		String author = authorTxt.getText();
//		String price = priceTxt.getText();
//		String bookDesc = bookDescTxt.getText();
//		if (StrUtils.isBlank(bookName)) {
//			JOptionPane.showMessageDialog(null, "图书名称不可为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}
//		if (StrUtils.isBlank(price)) {
//			JOptionPane.showMessageDialog(null, "图书价格不可为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
//			return;
//		}
//		String sex = "";
//		if (manJrb.isSelected()) {
//			sex = "男";
//		} else if (femaleJrb.isSelected()) {
//			sex = "女";
//		} else if (unknownJrb.isSelected()) {
//			sex = "不明";
//		}
//		if (StrUtils.isBlank(author)) {
//			sex = "不明";
//			author = "佚名";
//		}
//		int booktypeId = ((Booktype) bookTypeJcb.getSelectedItem()).getBooktypeId();
//		Book book = Book.builder().bookName(bookName).author(author).sex(sex)
//				.price(new BigDecimal(price)).booktypeId(booktypeId).description(bookDesc).build();
//
//		try {
//			if (bookService.hasValue("bookName", book.getBookName())) {
//				JOptionPane.showMessageDialog(null, "图书已存在", "提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
//			Book result = bookService.add(book);
//			if (!Objects.isNull(result)) {
//				resetValue();
//				JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			} else {
//				JOptionPane.showMessageDialog(null, "添加失败", "提示", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "添加异常，请稍后重试！", "提示",
//					JOptionPane.INFORMATION_MESSAGE);
//		}
//	}
//
//	/**
//	 * 初始化图书类别下拉框
//	 */
//	@SuppressWarnings("unchecked")
//	private void initBookTypeJcb(BooktypePage booktypePage) {
//		try {
//			List<Map<String, Object>> datas = (List<Map<String, Object>>) booktypeService
//					.getPages(booktypePage).getData();
//			for (Map<String, Object> data : datas) {
//				this.bookTypeJcb
//						.addItem(Booktype.builder().booktypeId((Integer) data.get("booktype_id"))
//								.booktypeName((String) data.get("booktype_name"))
//								.booktypeDesc(String.valueOf(data.get("booktype_desc"))).build());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 重置表单数据
//	 */
//	private void resetValue() {
//		this.bookNameTxt.setText("");
//		this.authorTxt.setText("");
//		this.manJrb.setSelected(true);
//		this.priceTxt.setText("");
//		this.bookDescTxt.setText("");
//		if (bookTypeJcb.getItemCount() > 0) {
//			this.bookTypeJcb.setSelectedIndex(0);
//		}
//	}
//}