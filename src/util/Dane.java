package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import xls.GrafikWorkBook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableFont.FontName;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Dane {
	private WritableSheet grafikSheet;
	private GrafikWorkBook grafik;
	private final Colour backgroundNormalRano = Colour.WHITE;
	private final Colour backgroundNormalPopo = Colour.GRAY_50;
	private final Colour backgroundWeekendRano = Colour.GRAY_25;
	private final Colour backgroundWeekendPopo = Colour.GREY_40_PERCENT;
	private final Colour fontLightColor = Colour.WHITE;
	private final Colour fontDarkColor = Colour.BLACK;
	private final Alignment alignment = Alignment.CENTRE;
	private final Orientation orientation = Orientation.HORIZONTAL;
	private final FontName font = WritableFont.ARIAL;
	private final int fontSize = 9;

	public void copyDane(int c, File parsedFile, GrafikWorkBook grafik)
			throws RowsExceededException, WriteException, IOException {
		this.grafik = grafik;
		grafikSheet = grafik.workbook.getSheet(0);
		BufferedReader br = new BufferedReader(new FileReader(parsedFile));
		String s = "";

		while ((s = br.readLine()) != null) {
			if (s.substring(0, 2).equals("||")) {
				Label l = new Label(grafik.col, grafik.row, "");
				grafikSheet.addCell(l);
				grafikSheet.getWritableCell(grafik.col, grafik.row)
						.setCellFormat(setNormalFormat());
			} else {
				String[] t = s.split("\\|");
				if (t[0].toLowerCase().equals("")) {
					Label l = new Label(grafik.col, grafik.row, t[1] + "-"
							+ t[2]);
					grafikSheet.addCell(l);
					grafikSheet.getWritableCell(grafik.col, grafik.row)
							.setCellFormat(setNormalFormat());
				} else {
					Label l = new Label(grafik.col, grafik.row, t[0]);
					grafikSheet.addCell(l);
					grafikSheet.getWritableCell(grafik.col, grafik.row)
							.setCellFormat(setNormalFormat());
				}
			}
			grafik.row++;
		}
		br.close();
		
		grafik.col++;
		grafik.row = 1;
	}

	private WritableCellFormat setNormalFormat() throws WriteException {
		WritableCellFormat format = null;
		int hour = 0;
		try {
			hour = Integer.parseInt(grafikSheet.getCell(grafik.col, grafik.row)
					.getContents().substring(0, 2));
		} catch (NumberFormatException e) {
			hour = 20;
		} catch (IndexOutOfBoundsException e) {
			// hour = 0;
		}

		if (grafikSheet.getCell(1, grafik.row).getContents().equals("Sobota")
				|| grafikSheet.getCell(1, grafik.row).getContents()
						.equals("Niedziela")) {
			if (hour > 9) {
				WritableFont wfont = new WritableFont(font, fontSize,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						fontLightColor);
				format = new WritableCellFormat(wfont);
				format.setBackground(backgroundWeekendPopo);
				setFormatBorder(format);
			} else {
				WritableFont wfont = new WritableFont(font, fontSize,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						fontDarkColor);
				format = new WritableCellFormat(wfont);
				format.setBackground(backgroundWeekendRano);
				setFormatBorder(format);
			}
		} else {
			if (hour > 9) {
				WritableFont wfont = new WritableFont(font, fontSize,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						fontLightColor);
				format = new WritableCellFormat(wfont);
				format.setBackground(backgroundNormalPopo);
				setFormatBorder(format);
			} else {
				WritableFont wfont = new WritableFont(font, fontSize,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						fontDarkColor);
				format = new WritableCellFormat(wfont);
				format.setBackground(backgroundNormalRano);
				setFormatBorder(format);
			}
		}
		return format;
	}

	private void setFormatBorder(WritableCellFormat format)
			throws WriteException {
		format.setAlignment(alignment);
		format.setOrientation(orientation);
		format.setBorder(Border.TOP, BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM, BorderLineStyle.THIN);
		format.setBorder(Border.LEFT, BorderLineStyle.MEDIUM);
		format.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM);
	}
}
