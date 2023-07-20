package miniprojet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddAttendance {
	Connection con;
	DefaultTableModel model = new DefaultTableModel();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void addView() throws SQLException {
		connect();
		JFrame frame = new JFrame();
		Font text = new Font("Times New Roman", Font.PLAIN, 18);
		Font btn = new Font("Times New Roman", Font.BOLD, 20);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();

		// x pour fermer l'appli
		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#37474F"));
		x.setBounds((int)dim.getWidth()-20, 10, 100, 20);
		x.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});


		// button BACK
		JLabel back = new JLabel("< BACK");
		back.setForeground(Color.decode("#070a29"));
		back.setFont(new Font("Times New Roman", Font.BOLD, 17));
		back.setBounds(18, 10, 100, 20);
		frame.getContentPane().add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});


		// Panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, (int)dim.getWidth()-20, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(panel);

		// TABLE
		@SuppressWarnings("serial")
		JTable table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("ID");
		model.addColumn("NOM");
		model.addColumn("STATUS");
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		JScrollPane scPane = new JScrollPane(table);
		scPane.setBounds(535, 240, 546, 316);
		frame.getContentPane().add(scPane);

		// DATE-
		JLabel dt = new JLabel("DATE : ");
		dt.setFont(text);
		dt.setBounds(85, 102, 75, 20);
		dt.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(dt);
		JTextField dtbox = new JTextField();
		dtbox.setBounds(155, 100, 150, 25);
		dtbox.setBackground(Color.decode("#DEE4E7"));
		dtbox.setFont(text);
		dtbox.setForeground(Color.decode("#37474F"));
		String dateInString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		dtbox.setText(dateInString);
		frame.getContentPane().add(dtbox);

		// CLASSE
		JLabel classes = new JLabel("CLASSE : ");
		classes.setFont(text);
		classes.setBounds(330, 102, 100, 20);
		classes.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(classes);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox clss = new JComboBox(classEt());
		clss.setBounds(440, 102, 50, 25);
		frame.getContentPane().add(clss);

		// ALREADY ADDED
		JLabel txt = new JLabel("");
		txt.setFont(text);
		txt.setBounds(125, 525, 350, 20);
		txt.setForeground(Color.red);
		frame.getContentPane().add(txt);

		// VIEW BUTTON
		JButton view = new JButton("VIEW");
		view.setBounds(500, 178, 125, 50);
		view.setFont(btn);
		view.setBackground(Color.decode("#DEE4E7"));
		view.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(view);
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (check(String.valueOf(clss.getSelectedItem()), dtbox.getText()))
						txt.setText("Attendance Already marked!!!");
					else
						tblupdt(String.valueOf(clss.getSelectedItem()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// ABSENT BUTTON
		JButton ab = new JButton("ABSENT");
		ab.setBounds(650, 178, 125, 50);
		ab.setFont(btn);
		ab.setBackground(Color.decode("#DEE4E7"));
		ab.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(ab);
		ab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.setValueAt("Absent", table.getSelectedRow(), 2);
			}
		});

		// PRESENT BUTTON
		JButton pre = new JButton("PRESENT");
		pre.setBounds(850, 178, 125, 50);
		pre.setFont(btn);
		pre.setBackground(Color.decode("#DEE4E7"));
		pre.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(pre);
		pre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print(table.getSelectedRow());
				table.setValueAt("Present", table.getSelectedRow(), 2);
			}
		});

		// SUBMIT BUTTON
		JButton sbmt = new JButton("SUBMIT");
		sbmt.setBounds(1000, 178, 125, 50);
		sbmt.setFont(btn);
		sbmt.setBackground(Color.decode("#DEE4E7"));
		sbmt.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(sbmt);
		sbmt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					try {
						addItem(Integer.parseInt(String.valueOf(table.getValueAt(i, 0))),
								String.valueOf(table.getValueAt(i, 2)), dtbox.getText(),
								String.valueOf(clss.getSelectedItem()));
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
					}
				}
				for (int i = 0; i < model.getRowCount(); i++) {
					model.removeRow(i);
					model.setRowCount(0);
				}
			}
		});

		frame.setSize((int)dim.getWidth(), (int)dim.getHeight());
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#070a29"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void connect() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		con = DriverManager.getConnection(url, user, pass);
	}

	public ResultSet dbSearch(String classes) throws SQLException {
		String str1 = "SELECT * from Etudiant where class = '" + classes + "'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		return rst;
	}

	public String[] classEt() throws SQLException {
		String str1 = "SELECT libelle from Classe";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		String[] rt = new String[25];
		int i = 0;
		while (rst.next()) {
			rt[i] = rst.getString("libelle");
			i++;
		}
		return rt;
	}

	public void tblupdt(String classes) {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			model.setRowCount(0);
		}
		try {
			ResultSet res = dbSearch(classes);
			for (int i = 0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getInt("id"), i, 0);
				model.setValueAt(res.getString("nom"), i, 1);
				model.setValueAt("Present", i, 2);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void addItem(int id, String status, String date, String classes) throws SQLException {
		String adding = "INSERT INTO Absence(id_absence,dt,status,class) values(" + id + ", '" + date + "', '" + status
				+ "', '" + classes + "')";
		Statement stm = con.createStatement();
		stm.executeUpdate(adding);
	}

	public boolean check(String classes, String dt) throws SQLException {
		String str1 = "select * from Absence where class = '" + classes + "' AND dt = '" + dt + "'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		if (rst.next())
			return true;
		else
			return false;
	}
}