package miniprojet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class email {
	private JTextField e_mail;

	/**
	 * @wbp.parser.entryPoint
	 */
	public void viewmail() {
		JFrame frame = new JFrame();
		Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
				frame.getContentPane().setLayout(null);

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
				JLabel back = new JLabel("< BACK");
				back.setForeground(Color.decode("#37474F"));
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
				
				frame.setSize((int)dim.getWidth(), (int)dim.getHeight());
				frame.setResizable(false);
				frame.getContentPane().setLayout(null);
				frame.setUndecorated(true);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setFocusable(true);
				frame.getContentPane().setBackground(Color.decode("#070a29"));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				
				e_mail = new JTextField();
				e_mail.setBounds(818, 189, 360, 35);
				e_mail.setForeground(new Color(55, 71, 79));
				e_mail.setFont(new Font("Times New Roman", Font.BOLD, 15));
				e_mail.setBackground(new Color(222, 228, 231));
				frame.getContentPane().add(e_mail);
				
				JButton btnSendEmail = new JButton("Send email");
				btnSendEmail.addActionListener(new ActionListener() {
					@SuppressWarnings("static-access")
					public void actionPerformed(ActionEvent e) {
						String mail=e_mail.getText();
						JavaEmailSender js=new JavaEmailSender();
						js.setEmailAddressTo(mail);
						js.main(null);
						JOptionPane.showMessageDialog(null, "L'Email est envoyé avec succees");
						frame.setVisible(true);

					}
				});
				btnSendEmail.setBounds(978, 290, 200, 50);
				btnSendEmail.setForeground(new Color(55, 71, 79));
				btnSendEmail.setFont(new Font("Times New Roman", Font.BOLD, 20));
				btnSendEmail.setBackground(new Color(222, 228, 231));
				frame.getContentPane().add(btnSendEmail);

	}
}
