package miniprojet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentView {

	Connection con;
	JFrame frame = new JFrame();
	DefaultTableModel model = new DefaultTableModel();
	Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * @wbp.parser.entryPoint
	 */
	public void stView(int id) throws SQLException {

		// x pour fermer l'appli
		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#070a29"));
		x.setBounds((int)dim.getWidth()-20, 10, 100, 20);
		x.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		// - pour minimize
		JLabel min = new JLabel("_");
		min.setForeground(Color.decode("#070a29"));
		min.setBounds(1480, 0, 100, 20);
		frame.getContentPane().add(min);
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}
		});

		// Panel
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, (int)dim.getWidth(), 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(panel);

		// Welcome
		JLabel welcome = new JLabel("Welcome " + getUser(id) + ",");
		welcome.setForeground(Color.decode("#DEE4E7"));
		welcome.setBounds(10, 50, 250, 20);
		welcome.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(welcome);

		// TABLE
		JTable table = new JTable();
		model = (DefaultTableModel) table.getModel();
		model.addColumn("DATE");
		model.addColumn("STATUS");
		JScrollPane scPane = new JScrollPane(table);
		scPane.setBounds(535, 234, 546, 316);
		table.setFont(new Font("Times New Roman", Font.BOLD, 20));
		table.setRowHeight(50);
		frame.getContentPane().add(scPane);

		// INFO
		JLabel totalclass = new JLabel("TOTAL DES COURS :");
		totalclass.setBounds(128, 180, 215, 20);
		totalclass.setForeground(Color.decode("#DEE4E7"));
		totalclass.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(totalclass);
		JLabel ttbox = new JLabel("");
		ttbox.setBounds(353, 180, 37, 20);
		ttbox.setForeground(Color.decode("#DEE4E7"));
		ttbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(ttbox);
		JLabel classAtt = new JLabel("COURS SUIVI :");
		classAtt.setBounds(400, 180, 142, 20);
		classAtt.setForeground(Color.decode("#DEE4E7"));
		classAtt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(classAtt);
		JLabel atbox = new JLabel("");
		atbox.setBounds(535, 180, 37, 20);
		atbox.setForeground(Color.decode("#DEE4E7"));
		atbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(atbox);
		JLabel classAbs = new JLabel("COURS MANQUÉS :");
		classAbs.setBounds(566, 180, 187, 20);
		classAbs.setForeground(Color.decode("#DEE4E7"));
		classAbs.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(classAbs);
		JLabel mtbox = new JLabel("");
		mtbox.setBounds(763, 180, 37, 20);
		mtbox.setForeground(Color.decode("#DEE4E7"));
		mtbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(mtbox);
		JLabel AttPer = new JLabel("POURCENTAGE DE FRÉQUENCE :");
		AttPer.setBounds(808, 180, 333, 20);
		AttPer.setForeground(Color.decode("#DEE4E7"));
		AttPer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(AttPer);
		JLabel prbox = new JLabel("");
		prbox.setBounds(1126, 180, 68, 20);
		prbox.setForeground(Color.decode("#DEE4E7"));
		prbox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		frame.getContentPane().add(prbox);

		// SETVALUES
		int[] arr = stat(4);
		ttbox.setText(String.valueOf(arr[0]));
		atbox.setText(String.valueOf(arr[1]));
		mtbox.setText(String.valueOf(arr[2]));
		prbox.setText(String.valueOf(arr[3]) + "%");

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

	public String getUser(int id) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		con = DriverManager.getConnection(url, user, pass);
		String str = "SELECT nom FROM Responsable WHERE id = " + id;
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		rst.next();
		return rst.getString("nom");
	}

	public void tblupdt(int id) {
		try {
			ResultSet res = dbSearch(id);
			for (int i = 0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getString("dt"), i, 0);
				model.setValueAt(res.getString("status"), i, 1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public int[] stat(int id) throws SQLException {
		String str = "SELECT COUNT(*) AS pre FROM Absence WHERE id_absence = " + id + " AND status = 'Present'";
		String str2 = "SELECT COUNT(*) AS abs FROM Absence WHERE id_absence = " + id + " AND status = 'Absent'";
		int[] x = new int[4];
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		rst.next();
		x[1] = rst.getInt("pre");
		rst = stm.executeQuery(str2);
		rst.next();
		x[2] = rst.getInt("abs");
		x[0] = x[1] + x[2];
		x[3] = (x[1] * 100) / x[0];
		tblupdt(id);
		return x;
	}

	public ResultSet dbSearch(int id) throws SQLException {
		String str1 = "SELECT * from Absence where id_absence = " + id + " ORDER BY dt desc";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		return rst;
	}
}