package com.wy.frame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.wy.common.SpringContext;
import com.wy.entity.Book;
import com.wy.entity.Booktype;
import com.wy.service.BookService;
import com.wy.service.BooktypeService;
import com.wy.utils.Result;
import com.wy.utils.StrUtils;

public class BookFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	private String lookAndFeel_win = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	private JTextField s_bookNameTxt;
	private JTextField s_authorTxt;
	private JTable bookTable;
	private JComboBox<Booktype> s_bookTypeJcb;
	private JComboBox<Booktype> bookTypeJcb;
	private JTextField bookIdTxt;
	private JTextField bookNameTxt;
	private JRadioButton manJrb;
	private JRadioButton femaleJrb;
	private JRadioButton unknownJrb;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField priceTxt;
	private JTextField authorTxt;
	private JTextArea bookDescTxt;

	private BooktypeService booktypeService;
	private BookService bookService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookFrm frame = new BookFrm();
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
	public BookFrm() {

		this.bookService = (BookService) SpringContext.getBean("bookService");
		this.booktypeService = (BooktypeService) SpringContext.getBean("booktypeService");
		setFrameIcon(new ImageIcon(BookFrm.class.getResource("/images/leo.jpg")));
		setClosable(true);
		setIconifiable(true);
		setTitle("图书管理");
		try {
			UIManager.setLookAndFeel(lookAndFeel_win);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBounds(100, 100, 614, 670);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u641C\u7D22\u6761\u4EF6", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JScrollPane scrollPane = new JScrollPane();

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u8868\u5355\u64CD\u4F5C", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										578, Short.MAX_VALUE)
								.addComponent(scrollPane, Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
								.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(24)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 74,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 200,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
						.addContainerGap()));

		JLabel lblNewLabel = new JLabel("编号：");

		bookIdTxt = new JTextField();
		bookIdTxt.setEditable(false);
		bookIdTxt.setColumns(10);

		JLabel label_3 = new JLabel("图书名称：");

		bookNameTxt = new JTextField();
		bookNameTxt.setColumns(10);

		JLabel label_4 = new JLabel("作者性别：");

		manJrb = new JRadioButton("男");
		buttonGroup.add(manJrb);
		manJrb.setSelected(true);

		femaleJrb = new JRadioButton("女");
		buttonGroup.add(femaleJrb);

		unknownJrb = new JRadioButton("不明");
		buttonGroup.add(unknownJrb);

		JLabel label_5 = new JLabel("价格：");

		priceTxt = new JTextField();
		priceTxt.setColumns(10);

		JLabel label_6 = new JLabel("图书作者：");

		authorTxt = new JTextField();
		authorTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				authorTxtFocusLost(e);
			}
		});
		authorTxt.setColumns(10);

		JLabel label_7 = new JLabel("图书类别：");

		bookTypeJcb = new JComboBox<Booktype>();

		JLabel label_8 = new JLabel("图书描述：");

		bookDescTxt = new JTextArea();
		bookDescTxt.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));

		JButton button_1 = new JButton("修改");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookUpdateActionPerformed(e);
			}
		});
		button_1.setIcon(new ImageIcon(BookFrm.class.getResource("/images/modify.png")));

		JButton button_2 = new JButton("删除");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookDeleteActionPerformed(e);
			}
		});
		button_2.setIcon(new ImageIcon(BookFrm.class.getResource("/images/delete.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addGroup(gl_panel_1
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(bookIdTxt, GroupLayout.PREFERRED_SIZE,
														57, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_5)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(priceTxt, 0, 0, Short.MAX_VALUE)))
								.addGap(32)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_3)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(bookNameTxt,
														GroupLayout.PREFERRED_SIZE, 134,
														GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_6)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(authorTxt)))
								.addGap(40)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_4)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(manJrb)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(femaleJrb)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(unknownJrb))
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(label_7)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(bookTypeJcb, 0, 119,
														Short.MAX_VALUE))))
						.addGroup(gl_panel_1.createSequentialGroup().addComponent(label_8)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(bookDescTxt, GroupLayout.DEFAULT_SIZE, 486,
										Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 123,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 127,
										GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(bookIdTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(unknownJrb).addComponent(femaleJrb)
								.addComponent(manJrb).addComponent(label_4)
								.addComponent(bookNameTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3))
						.addGap(40)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_5)
								.addComponent(priceTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6)
								.addComponent(authorTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_7)
								.addComponent(bookTypeJcb, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(27)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(label_8).addComponent(bookDescTxt,
										GroupLayout.PREFERRED_SIZE, 106,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(button_1).addComponent(button_2))
						.addContainerGap()));
		panel_1.setLayout(gl_panel_1);

		bookTable = new JTable();
		bookTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				bookTableMousePressed(e);
			}
		});
		bookTable.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0",
						"\u56FE\u4E66\u4F5C\u8005", "\u4F5C\u8005\u6027\u522B",
						"\u56FE\u4E66\u4EF7\u683C", "\u56FE\u4E66\u63CF\u8FF0",
						"\u56FE\u4E66\u7C7B\u522B" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false,
					false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		bookTable.getColumnModel().getColumn(0).setPreferredWidth(65);
		bookTable.getColumnModel().getColumn(5).setPreferredWidth(132);
		bookTable.getColumnModel().getColumn(6).setPreferredWidth(79);
		scrollPane.setViewportView(bookTable);

		JLabel label = new JLabel("图书名称：");

		s_bookNameTxt = new JTextField();
		s_bookNameTxt.setColumns(10);

		JLabel label_1 = new JLabel("作者：");

		s_authorTxt = new JTextField();
		s_authorTxt.setColumns(10);

		JLabel label_2 = new JLabel("图书类别：");

		s_bookTypeJcb = new JComboBox<Booktype>();

		JButton button = new JButton("查询");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookSearchActionPerformed(e);
			}
		});
		button.setIcon(new ImageIcon(BookFrm.class.getResource("/images/search.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(s_bookNameTxt, GroupLayout.PREFERRED_SIZE, 89,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(label_1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(s_authorTxt, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(label_2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(s_bookTypeJcb, GroupLayout.PREFERRED_SIZE, 74,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(button, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(label)
						.addComponent(s_bookNameTxt, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(s_authorTxt, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2)
						.addComponent(s_bookTypeJcb, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
				.addContainerGap(15, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);

		this.initBookType("search");
		this.initBookType("modify");
		this.initBookTable(new Book());
	}

	/**
	 * 图书删除事件处理
	 * @param evt
	 */
	private void bookDeleteActionPerformed(ActionEvent evt) {
		String bookId = bookIdTxt.getText();
		if (StrUtils.isBlank(bookId)) {
			JOptionPane.showMessageDialog(null, "请选择要删除的图书类别信息！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int choose = JOptionPane.showConfirmDialog(null, "删除这条图书类别信息？", "确定删除",
				JOptionPane.OK_CANCEL_OPTION);
		if (choose == 0) {
			try {
				int row = bookService.delete(bookId);
				if (row == 1) {
					this.resetValue();
					this.initBookTable(new Book());
					JOptionPane.showMessageDialog(null, "删除成功", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					JOptionPane.showMessageDialog(null, "删除失败", "提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "删除异常，请稍后重试", "警告",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/**
	 * 图书信息修改事件处理
	 * @param evt
	 */
	private void bookUpdateActionPerformed(ActionEvent evt) {
		String bookId = bookIdTxt.getText();
		String bookName = bookNameTxt.getText();
		String author = authorTxt.getText();
		String price = priceTxt.getText();
		String bookDesc = bookDescTxt.getText();
		if (StrUtils.isBlank(bookId)) {
			JOptionPane.showMessageDialog(null, "请选择要修改的图书信息！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (StrUtils.isBlank(bookName)) {
			JOptionPane.showMessageDialog(null, "图书名称不可为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (StrUtils.isBlank(price)) {
			JOptionPane.showMessageDialog(null, "图书价格不可为空！", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		String sex = "";
		if (manJrb.isSelected()) {
			sex = "男";
		} else if (femaleJrb.isSelected()) {
			sex = "女";
		} else if (unknownJrb.isSelected()) {
			sex = "不明";
		}
		if (StrUtils.isBlank(author) || author.equals("佚名")) {
			sex = "不明";
			author = "佚名";
		}
		int booktypeId = ((Booktype) bookTypeJcb.getSelectedItem()).getBooktypeId();
		Book book = Book.builder().bookId(Integer.parseInt(bookId)).bookName(bookName)
				.author(author).sex(sex).price(new BigDecimal(price)).booktypeId(booktypeId)
				.description(bookDesc).build();

		try {
			int row = bookService.update(book);
			if (row == 1) {
				resetValue();
				JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			} else {
				JOptionPane.showMessageDialog(null, "修改失败", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "修改异常，请稍后重试！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 表格行点击事件处理
	 * @param evt
	 */
	private void bookTableMousePressed(MouseEvent evt) {
		int row = bookTable.getSelectedRow();
		bookIdTxt.setText((String) bookTable.getValueAt(row, 0));
		bookNameTxt.setText((String) bookTable.getValueAt(row, 1));
		authorTxt.setText((String) bookTable.getValueAt(row, 2));
		String sex = (String) bookTable.getValueAt(row, 3);
		if (sex.equals("男")) {
			manJrb.setSelected(true);
		} else if (sex.equals("女")) {
			femaleJrb.setSelected(true);
		} else if (sex.equals("不明")) {
			unknownJrb.setSelected(true);
		}
		priceTxt.setText((String) bookTable.getValueAt(row, 4));
		bookDescTxt.setText((String) bookTable.getValueAt(row, 5));
		String bookTypeName = (String) bookTable.getValueAt(row, 6);
		for (int i = 0; i < bookTypeJcb.getItemCount(); i++) {
			Booktype item = (Booktype) bookTypeJcb.getItemAt(i);
			if (item.getBooktypeName().equals(bookTypeName)) {
				bookTypeJcb.setSelectedIndex(i);
				break;
			}
		}
	}

	/**
	 * 作者文本框焦点失去事件处理
	 * @param evt
	 */
	private void authorTxtFocusLost(FocusEvent evt) {
		if (StrUtils.isBlank(authorTxt.getText())) {
			this.unknownJrb.setSelected(true);
		}
	}

	/**
	 * 查询事件处理
	 * @param evt
	 */
	private void bookSearchActionPerformed(ActionEvent evt) {
		String bookName = s_bookNameTxt.getText();
		String author = s_authorTxt.getText();
		int bookTypeId = ((Booktype) s_bookTypeJcb.getSelectedItem()).getBooktypeId();
		Book book = new Book();
		book.setBookName(bookName);
		book.setAuthor(author);
		book.setBooktypeId(bookTypeId);
		this.initBookTable(book);
	}

	/**
	 * 初始化表格数据
	 * @param book
	 */
	@SuppressWarnings("unchecked")
	public void initBookTable(Book book) {
		DefaultTableModel dtm = (DefaultTableModel) bookTable.getModel();
		dtm.setRowCount(0); // 设置行数为0，即为表清空
		try {
			Result list = bookService.getList(book);
			List<Book> datas = (List<Book>) list.getData();
			for (Book data : datas) {
				Vector<String> v = new Vector<>();
				v.add(data.getBookId().toString());
				v.add(data.getBookName());
				v.add(data.getAuthor());
				v.add(data.getSex());
				v.add(data.getPrice().toString());
				v.add(data.getDescription());
				dtm.addRow(v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化图书类别下拉框
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	public void initBookType(String type) {
		Booktype bookType = null;
		try {
			List<Booktype> result = (List<Booktype>) booktypeService.getList(new Booktype())
					.getData();
			if ("search".equals(type)) {
				bookType = new Booktype();
				bookType.setBooktypeName("请选择");
				bookType.setBooktypeId(-1);
				s_bookTypeJcb.addItem(bookType);
			}
			for (Booktype booktype : result) {
				bookType = new Booktype();
				bookType.setBooktypeId(booktype.getBooktypeId());
				bookType.setBooktypeName(booktype.getBooktypeName());
				if ("search".equals(type)) {
					s_bookTypeJcb.addItem(bookType);
				} else if ("modify".equals(type)) {
					bookTypeJcb.addItem(bookType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重置表单数据
	 */
	private void resetValue() {
		this.bookIdTxt.setText("");
		this.bookNameTxt.setText("");
		this.authorTxt.setText("");
		this.manJrb.setSelected(true);
		this.priceTxt.setText("");
		this.bookDescTxt.setText("");
		if (bookTypeJcb.getItemCount() > 0) {
			this.bookTypeJcb.setSelectedIndex(0);
		}
	}
}
