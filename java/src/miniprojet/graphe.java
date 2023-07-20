package miniprojet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class graphe {
	Connection con;

	public graphe() {

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void graphView() {
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
		

		// button BACK
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

		String url = "jdbc:mysql://localhost:3306/projetjava";
		String user = "root";
		String pass = "Saif@12345";
		// chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			con = DriverManager.getConnection(url, user, pass);
			String sql = "select distinct class,count(*) from Absence where status='Absent' group by class;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				dataset.setValue(rs.getInt(2), "absent", rs.getString(1));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JFreeChart chart = ChartFactory.createBarChart("", "classe", "absent", dataset, PlotOrientation.VERTICAL, false,
				true, false);

		CategoryPlot categoryPlot = chart.getCategoryPlot();
		// categoryPlot.setRangeGridlinePaint(Color.BLUE);
		categoryPlot.setBackgroundPaint(Color.WHITE);
		BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
		Color clr3 = new Color(204, 0, 51);
		renderer.setSeriesPaint(0, clr3);

		JLabel lblStaistiqueDuTaux = new JLabel("Staistique du taux d'absentéisme");
		lblStaistiqueDuTaux.setForeground(new Color(222, 228, 231));
		lblStaistiqueDuTaux.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblStaistiqueDuTaux.setBounds(625, 152, 317, 20);
		frame.getContentPane().add(lblStaistiqueDuTaux);

		ChartPanel barpChartPanel = new ChartPanel(chart);
		barpChartPanel.setBounds(500, 215, 591, 508);
		frame.getContentPane().add(barpChartPanel);

		JPanel panelPieChart = new JPanel();
		barpChartPanel.add(panelPieChart);
		panelPieChart.setLayout(new BorderLayout(0, 0));
		panelPieChart.removeAll();
		panelPieChart.validate();

	}
}
