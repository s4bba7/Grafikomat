package xls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import pracownik.IDprac_list;
import util.CalendarDays;

public class WorkbookTemplate {
	private WritableSheet sheet;
	private final Colour obramowkaColor = Colour.LIME;
	private final Colour obramowkaWeekendColor = Colour.GREY_25_PERCENT;
	private final Alignment obramowkaAlignment = Alignment.CENTRE;
	private final Orientation obramowkaOrientation = Orientation.PLUS_90;
	private final FontName font = WritableFont.ARIAL;
	private final int fontSize = 9;
	private static IDprac_list idPracList = IDprac_list.getInstance();

	public void prepare(GrafikWorkBook wb, File file, int rok, int miesiac)
			throws WriteException, IOException {
		int c = 0;
		int r = 1;

		wb.workbook = Workbook.createWorkbook(file);
		sheet = wb.workbook.createSheet("grafik", 0);

		// Wiersz A1:
		WritableFont wfont = new WritableFont(font, fontSize,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(wfont);
		format.setAlignment(obramowkaAlignment);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBackground(obramowkaColor);
		format.setOrientation(obramowkaOrientation);
		format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		format.setWrap(true);

		int lenght = 0;
		for (int i = 0; i < idPracList.getList().size(); i++) {
			Label l = new Label(c, 0, idPracList.getList().get(i).getName()
					.replace(" ", "\n"));
			if (lenght < l.getContents().length())
				lenght = l.getContents().length();
			sheet.addCell(l);
			WritableCell cellFormat = sheet.getWritableCell(c, 0);
			if (c == 2) {
				format = new WritableCellFormat(wfont);
				format.setAlignment(obramowkaAlignment);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setBackground(obramowkaColor);
				format.setOrientation(obramowkaOrientation);
				format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
				format.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
				format.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
				format.setWrap(true);
			}
			cellFormat.setCellFormat(format);
			c++;
		}
		sheet.setColumnView(0, 3);
		sheet.setColumnView(1, 12);
		for (int i = 2; i < sheet.getColumns(); i++)
			sheet.setColumnView(i, 11);
		sheet.setRowView(0, lenght * 80);

		// Kolumna A i B:
		c = 0;
		format = new WritableCellFormat(wfont);
		format.setAlignment(obramowkaAlignment);
		format.setBackground(obramowkaColor);
		format.setOrientation(Orientation.HORIZONTAL);

		for (CalendarDays e : getCalendarDays(rok, miesiac)) {
			try {
				sheet.addCell(new Label(c, r, e.getDay() + ""));
				format = setFormat(true, Border.BOTTOM, e.getDayOfWeek());
				sheet.setRowView(r, 300);
				WritableCell cellFormat = sheet.getWritableCell(c++, r);
				cellFormat.setCellFormat(format);

				sheet.addCell(new Label(c, r, e.getDayOfWeek()));
				cellFormat = sheet.getWritableCell(c--, r++);
				format = setFormat(true, Border.BOTTOM, e.getDayOfWeek());
				cellFormat.setCellFormat(format);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// wb.workbook.write();
		}
	}

	private WritableCellFormat setFormat(boolean b, Border bor, String dayOfWeek)
			throws WriteException {
		WritableFont wfont = new WritableFont(font, fontSize,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.BLACK);
		WritableCellFormat format = new WritableCellFormat(wfont);
		format.setAlignment(obramowkaAlignment);
		format.setOrientation(Orientation.HORIZONTAL);
		if (b)
			format.setBorder(bor, BorderLineStyle.THIN);
		else
			format.setBorder(bor, BorderLineStyle.NONE);

		if (dayOfWeek.equals("Sobota") || dayOfWeek.equals("Niedziela"))
			format.setBackground(obramowkaWeekendColor);
		else
			format.setBackground(obramowkaColor);
		return format;
	}

	private ArrayList<CalendarDays> getCalendarDays(int rok, int miesiac) {
		ArrayList<CalendarDays> list = new ArrayList<CalendarDays>();
		GregorianCalendar gc = new GregorianCalendar(new Locale("PL"));
		gc.set(rok, --miesiac, 1);
		int dayOfWeek = gc.get(Calendar.DAY_OF_WEEK);
		int day = gc.get(Calendar.DATE);

		while (gc.get(Calendar.MONTH) == miesiac) {
			list.add(new CalendarDays(day, switchIntToWeekday(dayOfWeek)));
			gc.add(Calendar.DATE, 1);
			day = gc.get(Calendar.DATE);
			dayOfWeek = gc.get(Calendar.DAY_OF_WEEK);
		}
		return list;
	}

	private String switchIntToWeekday(int a) {
		switch (a) {
		case 1:
			return "Niedziela";
		case 2:
			return "Poniedziałek";
		case 3:
			return "Wtorek";
		case 4:
			return "Środa";
		case 5:
			return "Czwartek";
		case 6:
			return "Piątek";
		case 7:
			return "Sobota";
		}
		return "";
	}
}