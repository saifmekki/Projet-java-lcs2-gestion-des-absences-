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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeacherView {
	/**
	 * @wbp.parser.entryPoint
	 */
	public void tcView(int id) throws SQLException {

		JFrame frame = new JFrame();
		Font btn = new Font("Times New Roman", Font.BOLD, 20);
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();

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

		// Minimizer
		JLabel min = new JLabel("_");
		min.setForeground(Color.decode("#070a29"));
		min.setBounds((int)dim.getWidth()-40, 0, 100, 20);
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

		// ADD ATTENDANCE
		JButton addattendance = new JButton("ADD ATTENDANCE");
		addattendance.setBounds(599, 260, 650, 60);
		addattendance.setFont(btn);
		addattendance.setBackground(Color.decode("#DEE4E7"));
		addattendance.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(addattendance);
		addattendance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAttendance addatt = new AddAttendance();
				try {
					addatt.addView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// EDITATTENDANCE
		JButton editattendance = new JButton("EDIT ATTENDANCE");
		editattendance.setBounds(599, 349, 650, 60);
		editattendance.setFont(btn);
		editattendance.setBackground(Color.decode("#DEE4E7"));
		editattendance.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(editattendance);
		editattendance.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditAttendance editatt = new EditAttendance();
				try {
					editatt.editView();
				} catch (SQLException e1) {
					e1.printStackTrace();
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

	public String getUser(int id) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		Connection con = DriverManager.getConnection(url, user, pass);
		String str = "SELECT nom FROM Responsable WHERE id = " + id;
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		rst.next();
		return rst.getString("nom");
	}
}