package miniprojet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.*;

public class Login {

	int usr = 0;

	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("static-access")
	public void loginView() {
		JFrame frame = new JFrame();
		Font text = new Font("Times New Roman", Font.PLAIN, 20);
		Home hm = new Home();
		TeacherView tview = new TeacherView();
		StudentView sview = new StudentView();
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();


		// x pour fermer l'appli
		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#DEE4E7"));
		x.setBounds((int)dim.getWidth()-20, 20, 100, 20);
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
		min.setForeground(Color.decode("#DEE4E7"));
		min.setBounds((int)dim.getWidth()-40, 10, 100, 20);
		min.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(min);
		min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}
		});

		// LOGIN TEXT
		JLabel lgn = new JLabel("LOGIN");
		lgn.setForeground(Color.decode("#DEE4E7"));
		lgn.setBounds(700, 113, 350, 75);
		lgn.setFont(new Font("Times New Roman", Font.BOLD, 75));
		frame.getContentPane().add(lgn);

		// login text
		JLabel user = new JLabel("Login :");
		user.setForeground(Color.decode("#DEE4E7"));
		user.setBounds(670, 255, 100, 20);
		user.setFont(text);
		frame.getContentPane().add(user);

		// USER FIELD
		JTextField username = new JTextField();
		username.setBounds(670, 285, 360, 35);
		username.setBackground(Color.decode("#DEE4E7"));
		username.setForeground(Color.decode("#37474F"));
		username.setFont(new Font("Times New Roman", Font.BOLD, 15));
		frame.getContentPane().add(username);

		// Password texte
		JLabel pass = new JLabel("Password :");
		pass.setForeground(Color.decode("#DEE4E7"));
		pass.setBounds(670, 355, 100, 20);
		pass.setFont(text);
		frame.getContentPane().add(pass);

		// PASSWORD FIELD
		JPasswordField password = new JPasswordField();
		password.setBounds(670, 385, 360, 35);
		password.setBackground(Color.decode("#DEE4E7"));
		password.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(password);

		// WARNING
		JLabel warning = new JLabel();
		warning.setForeground(Color.RED);
		warning.setBounds(625, 450, 250, 20);
		warning.setHorizontalAlignment(warning.CENTER);
		frame.getContentPane().add(warning);

		// button LOGIN
		JButton login = new JButton("LOGIN");
		login.setBounds(640, 496, 200, 50);
		login.setFont(new Font("Times New Roman", Font.BOLD, 20));
		login.setBackground(Color.decode("#DEE4E7"));
		login.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(login);
		login.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int res = dbCheck(username.getText(), password.getText());
					if (res == 0) {
						warning.setText("NO USER FOUND!!!");
						username.setText("");
						password.setText("");
					} else if (res == -1) {
						warning.setText("WRONG PASSWORD!!!");
						username.setText("");
						password.setText("");
					} else {
						if (res == 1)
							hm.homeView(usr);
						else if (res == 2)
							tview.tcView(usr);
						else if (res == 3)
							sview.stView(usr);
						frame.dispose();
					}
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
		
		JButton btnResest = new JButton("RESEST");
		btnResest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				password.setText(null);
				username.setText(null);
			}
		});
		btnResest.setForeground(new Color(55, 71, 79));
		btnResest.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnResest.setBackground(new Color(222, 228, 231));
		btnResest.setBounds(850, 496, 200, 50);
		frame.getContentPane().add(btnResest);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public int dbCheck(String name, String password) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		String str = "SELECT * FROM Responsable WHERE login = '" + name + "'";
		Connection con = DriverManager.getConnection(url, user, pass);
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str);
		if (rst.next()) {
			if (rst.getString("password").equals(password)) {
				usr = rst.getInt("id");
				return rst.getInt("prio");
			} else
				return -1;
		} else {
			return 0;
		}
	}
}