package frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import logs.ConsoleLog;
import GLOBAL.GlobalVariables;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8583083643051002316L;
	public static int x, y, width = 164, height = 100;
	private static Border padding = BorderFactory.createEmptyBorder(4, 16, 4,
			16);
	private static JComboBox yearList;
	private static JComboBox monthList = new DateList().getMonths();
	private static MainFrame mframe = new MainFrame();

	public static void main(String[] args) {
//		File f = new File("lista.txt");
//		GlobalVariables.listaPath = f.getAbsolutePath();
		GlobalVariables.listaPath = (args[0] + File.separator + "lista.txt")
				.replace("%20", " ");
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				if (ConsoleLog.isLogExist())
					ConsoleLog.onExit();
			}
		}));

		GregorianCalendar gc = new GregorianCalendar();
		final Integer year = gc.get(Calendar.YEAR);
		final Integer month = gc.get(Calendar.MONTH) + 1;
		yearList = new DateList().getYears(year - 1, year + 1);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mframe.setTitle("Grafikomat");
				mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JPanel datePanel = new JPanel(new GridLayout());
				datePanel.setBorder(padding);
				datePanel.add(yearList);
				datePanel.add(monthList);
				yearList.setSelectedItem(year);
				monthList.setSelectedItem(month);

				JPanel buttonPanel = new JPanel(new GridLayout());
				buttonPanel.setBorder(padding);
				buttonPanel.add(new GrafikButton().getLocalButton(yearList,
						monthList));
				// panel.add(new GrafikButton().getRemoteButton(yearList,
				// monthList));

				mframe.add(datePanel, BorderLayout.NORTH);
				mframe.add(buttonPanel, BorderLayout.CENTER);
				mframe.setPosition();
				mframe.setBounds(x, y, width, height);
				mframe.setVisible(true);
			}
		});

	}

	private void setPosition() {
		Toolkit t = Toolkit.getDefaultToolkit();
		int w = (int) t.getScreenSize().getWidth();
		int h = (int) t.getScreenSize().getHeight();
		x = (int) ((w / 2) - (width / 2));
		y = (int) ((h / 2) - (height / 2));
	}

	public static Point2D getPosition() {
		return new Point2D.Double(mframe.getX(), mframe.getY());
	}
}
