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

public class Home {

	/**
	 * @wbp.parser.entryPoint
	 */
	public void homeView(int id) throws SQLException {
		JFrame frame = new JFrame();
		Font btn = new Font("Times New Roman", Font.BOLD, 20);
		Admin adm = new Admin();
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

		// - pour minimize
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
		// -----------------------------------------------------------

		// button etudiant
		JButton students = new JButton("Etudiant");
		students.setBounds(599, 305, 400, 60);
		students.setFont(btn);
		students.setBackground(Color.decode("#DEE4E7"));
		students.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(students);
		students.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Students std = new Students();
				try {
					std.studentView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// button ADD ATTENDANCE
		JButton addattendance = new JButton("ADD ATTENDANCE");
		addattendance.setBounds(599, 375, 400, 60);
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

		// button EDIT ATTENDANCE
		JButton editattendance = new JButton("EDIT ATTENDANCE");
		editattendance.setBounds(599, 445, 400, 60);
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

		// button Enseignant
		JButton teacher = new JButton("Enseignant");
		teacher.setBounds(599, 235, 400, 60);
		teacher.setFont(new Font("Times New Roman", Font.BOLD, 20));
		teacher.setBackground(Color.decode("#DEE4E7"));
		teacher.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(teacher);
		teacher.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Teachers teacher = new Teachers();
				try {
					teacher.teachersView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// button responsable

		JButton admin = new JButton("Responsable");
		admin.setBounds(599, 165, 400, 60);
		admin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		admin.setBackground(Color.decode("#DEE4E7"));
		admin.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(admin);
		admin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					adm.adminView();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// button classe
		JButton classes = new JButton("CLASSE");
		classes.setBounds(599, 95, 400, 60);
		classes.setFont(new Font("Times New Roman", Font.BOLD, 20));
		classes.setBackground(Color.decode("#DEE4E7"));
		classes.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(classes);
		classes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Class classroom = new Class();
				classroom.classView();
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

		// button Graph
		JButton graph = new JButton("Graph");
		graph.setForeground(new Color(7, 10, 41));
		graph.setFont(new Font("Times New Roman", Font.BOLD, 20));
		graph.setBackground(new Color(222, 228, 231));
		graph.setBounds(599, 515, 400, 60);
		frame.getContentPane().add(graph);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		graph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphe graph = new graphe();
				graph.graphView();
			}
		});
		// button email
				
				
				JButton btnEmail = new JButton("Email");
				btnEmail.setForeground(new Color(7, 10, 41));
				btnEmail.setFont(new Font("Times New Roman", Font.BOLD, 20));
				btnEmail.setBackground(new Color(222, 228, 231));
				btnEmail.setBounds(599, 585, 400, 60);
				frame.getContentPane().add(btnEmail);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				btnEmail.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						email email = new email();
						email.viewmail();
					}
				});

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