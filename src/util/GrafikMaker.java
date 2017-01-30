package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import logs.ConsoleLog;
import pracownik.IDprac_list;
import xls.GrafikWorkBook;
import xls.WorkbookTemplate;
import frame.ProgressBar_JFrame;

public class GrafikMaker {

	private File grafikFile;
	private GrafikWorkBook grafikWorkbook = new GrafikWorkBook();
	private int dbid;
	private String date;
	private UpDownLoader udloader = new UpDownLoader();
	private HTMLTableParser parser = new HTMLTableParser();
	private static IDprac_list idPracList = IDprac_list.getInstance();

	public void make(String loc, int rok, int miesiac, final JButton button,
			final String buttonText) {
		if (miesiac < 10)
			date = rok + "0" + miesiac;
		else
			date = "" + rok + miesiac;

		try {
			File oldFile = new File(loc + date + ".xls");
			if (oldFile.exists())
				oldFile.delete();

			grafikFile = new File(loc + date);
			grafikFile.createNewFile();

			WorkbookTemplate wrkTemp = new WorkbookTemplate();
			wrkTemp.prepare(grafikWorkbook, grafikFile, rok, miesiac);

			Dane dane = new Dane();
			for (int i = 2; i < idPracList.getList().size(); i++) {
				dbid = idPracList.getList().get(i).getDbid();
				File file = udloader.downloadGrafik(dbid, date);
				file = parser.parse(file);
				dane.copyDane(3, file, grafikWorkbook);
				ProgressBar_JFrame.setValue(i - 1);
			}
			grafikWorkbook.workbook.write();
			grafikWorkbook.workbook.close();

			grafikFile
					.renameTo(new File(grafikFile.getAbsoluteFile() + ".xls"));

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					button.setEnabled(true);
					button.setText(buttonText);
				}
			});

		} catch (Exception e) {
			try {
				StringWriter ws = new StringWriter();
				e.printStackTrace(new PrintWriter(ws));
				ConsoleLog.write(ws.toString());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,
					"Coś poszło nie tak. Spróbuj ponownie.");
			System.exit(0);
		}
	}
}