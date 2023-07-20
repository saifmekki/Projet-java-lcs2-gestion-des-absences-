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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Admin {

	DefaultTableModel model = new DefaultTableModel();
	Font text = new Font("Times New Roman", Font.PLAIN, 18);
	Connection con;
	int check;
	JButton edit;
	JButton delete;
	JButton add;

	
	public void adminView() throws NumberFormatException, SQLException {
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
		panel.setBounds(0, 0, (int)dim.getWidth(), 35);
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
		model.addColumn("LOGIN");
		model.addColumn("NOM");
		tblupdt();
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		JScrollPane scPane = new JScrollPane(table);
		scPane.setBounds(535, 240, 546, 316);
		frame.getContentPane().add(scPane);

		// ID
		JLabel id = new JLabel("ID : ");
		id.setFont(text);
		id.setBounds(117, 60, 40, 20);
		id.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(id);
		JTextField idbox = new JTextField();
		idbox.setBounds(167, 58, 50, 25);
		idbox.setBackground(Color.decode("#DEE4E7"));
		idbox.setFont(text);
		idbox.setForeground(Color.decode("#070a29"));
		idbox.setEditable(false);
		frame.getContentPane().add(idbox);

		// login
		JLabel user = new JLabel("LOGIN : ");
		user.setFont(text);
		user.setBounds(88, 120, 87, 20);
		user.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(user);
		JTextField username = new JTextField();
		username.setBounds(200, 113, 199, 35);
		username.setBackground(Color.decode("#DEE4E7"));
		username.setFont(text);
		username.setForeground(Color.decode("#070a29"));
		username.setEditable(false);
		frame.getContentPane().add(username);

		// nom
		JLabel nm = new JLabel("NOM : ");
		nm.setFont(text);
		nm.setBounds(438, 120, 79, 20);
		nm.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(nm);
		JTextField name = new JTextField();
		name.setBounds(514, 113, 199, 35);
		name.setBackground(Color.decode("#DEE4E7"));
		name.setFont(text);
		name.setForeground(Color.decode("#070a29"));
		name.setEditable(false);
		frame.getContentPane().add(name);

		// PASSWORD
		JLabel pass = new JLabel("PASSWORD : ");
		pass.setFont(text);
		pass.setBounds(750, 120, 138, 20);
		pass.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(pass);
		JTextField password = new JTextField();
		password.setBounds(890, 113, 199, 35);
		password.setBackground(Color.decode("#DEE4E7"));
		password.setFont(text);
		password.setForeground(Color.decode("#070a29"));
		password.setEditable(false);
		frame.getContentPane().add(password);

		// SAVE BUTTON
		JButton save = new JButton("SAVE");
		save.setBounds(1000, 178, 125, 50);
		save.setFont(btn);
		save.setBackground(Color.decode("#DEE4E7"));
		save.setForeground(Color.decode("#070a29"));
		save.setEnabled(false);
		frame.getContentPane().add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (check == 1) {
					try {
						adder(Integer.parseInt(idbox.getText()), username.getText(), name.getText(),
								password.getText());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else if (check == 2) {
					save.setEnabled(false);
					try {
						if (password.getText().equals(""))
							editor(Integer.parseInt(idbox.getText()), username.getText(), name.getText());
						else
							editor(Integer.parseInt(idbox.getText()), username.getText(), name.getText(),
									password.getText());
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				try {
					idbox.setText(String.valueOf(getid()));
					edit.setEnabled(false);
					delete.setEnabled(false);
					name.setText("");
					username.setText("");
					password.setText("");
					while (model.getRowCount() > 0)
						model.removeRow(0);
					tblupdt();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// EDIT BUTTON
		edit = new JButton("EDIT");
		edit.setBounds(650, 178, 125, 50);
		edit.setFont(btn);
		edit.setEnabled(false);
		edit.setBackground(Color.decode("#DEE4E7"));
		edit.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(edit);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				edit.setEnabled(false);
				save.setEnabled(true);
				check = 2;
				username.setEditable(true);
				name.setEditable(true);
				password.setEditable(true);
			}
		});

		// ADD BUTTON
		add = new JButton("ADD");
		add.setBounds(500, 178, 125, 50);
		add.setFont(btn);
		add.setBackground(Color.decode("#DEE4E7"));
		add.setForeground(Color.decode("#070a29"));
		frame.getContentPane().add(add);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add.setEnabled(false);
				save.setEnabled(true);
				delete.setEnabled(false);
				username.setEditable(true);
				name.setEditable(true);
				password.setEditable(true);
				check = 1;
				try {
					idbox.setText(String.valueOf(getid()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// DELETE BUTTON
		delete = new JButton("DELETE");
		delete.setBounds(850, 178, 125, 50);
		delete.setFont(btn);
		delete.setBackground(Color.decode("#DEE4E7"));
		delete.setForeground(Color.decode("#070a29"));
		delete.setEnabled(false);
		frame.getContentPane().add(delete);
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				username.setEditable(false);
				name.setEditable(false);
				password.setEditable(false);
				edit.setEnabled(false);
				add.setEnabled(true);
				try {
					deleter(Integer.parseInt(idbox.getText()));
					idbox.setText(String.valueOf(getid()));
					name.setText("");
					username.setText("");
					password.setText("");
					while (model.getRowCount() > 0)
						model.removeRow(0);
					tblupdt();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		// TABLE ACTION
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				password.setText("");
				idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
				username.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
				name.setText(String.valueOf(table.getModel().getValueAt(row, 2)));
				edit.setEnabled(true);
				username.setEditable(false);
				password.setEditable(false);
				name.setEditable(false);
				save.setEnabled(false);
				delete.setEnabled(true);
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

	public void tblupdt() {
		try {
			ResultSet res = dbSearch();
			for (int i = 0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getInt("id"), i, 0);
				model.setValueAt(res.getString("login"), i, 1);
				model.setValueAt(res.getString("nom"), i, 2);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public ResultSet dbSearch() throws SQLException {
		String str1 = "SELECT * FROM Responsable WHERE prio = 1";
		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		con = DriverManager.getConnection(url, user, pass);
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		return rst;
	}

	public int getid() throws SQLException {
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery("SELECT MAX(id) from Responsable");
		if (rst.next()) {
			return rst.getInt("MAX(id)") + 1;
		} else {
			return 1;
		}
	}

	public void adder(int id, String user, String name, String password) throws SQLException {
		String adding = "insert into Responsable (id,login,nom,password,prio) values (" + id + ", '" + user + "', '"
				+ name + "', '" + password + "', 1)";
		Statement stm = con.createStatement();
		stm.executeUpdate(adding);
	}

	public void deleter(int id) throws SQLException {
		String del = "DELETE FROM Responsable WHERE id = " + id;
		Statement stm = con.createStatement();
		stm.executeUpdate(del);
	}

	public void editor(int id, String username, String name, String password) throws SQLException {
		String update = "UPDATE Responsable SET login = '" + username + "', nom = '" + name + "', password = '"
				+ password + "'WHERE id = " + id;
		Statement stm = con.createStatement();
		stm.executeUpdate(update);
	}

	public void editor(int id, String username, String name) throws SQLException {
		String update = "UPDATE Responsable SET login = '" + username + "', nom = '" + name + "' WHERE id = " + id;
		Statement stm = con.createStatement();
		stm.executeUpdate(update);
	}
}