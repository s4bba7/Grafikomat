package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import GLOBAL.GlobalVariables;

public class UpDownLoader {

	// public static void main(String[] args) throws BiffException, IOException
	// {
	// UpDownLoader udl = new UpDownLoader();
	// File file = udl.downloadGrafik(23020, 201505 + "");
	// Workbook.getWorkbook(file);
	// }

	public File downloadGrafik(int nr, String data) throws IOException {
		File file = null;
		file = File.createTempFile("tmp", "");

		do {
			BufferedInputStream bis = new BufferedInputStream(new URL(
					GlobalVariables.GRAFIK_URL_1 + nr
							+ GlobalVariables.GRAFIK_URL_2 + data).openStream());
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			int i = 0;
			while ((i = bis.read()) != -1)
				bos.write(i);
			bos.close();
			bis.close();
		} while (file.length() == 0);
		return file;
	}
}
