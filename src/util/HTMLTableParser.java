package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLTableParser {

	// public static void main(String[] args) throws IOException {
	// HTMLTableParser h = new HTMLTableParser();
	// h.parse(new File("tmp.xls"));
	// }

	int i = 0;

	public File parse(File file) throws IOException {
		File parsed = File.createTempFile("parsed", "txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(parsed));
		Document doc = Jsoup.parse(file, "Cp1250");
		Element table = doc.select("table").get(0); // select the first
													// table.
		Elements rows = table.select("tr");

		String s = "";
		for (int i = 1; i < rows.size(); i++) { // first row is the col
												// names so skip it.
			Element row = rows.get(i);
			Elements cols = row.select("td");
			s = cols.get(9).text() + "|" + cols.get(3).text() + "|"
					+ cols.get(7).text() + "\n";
			bw.write(s);
		}
		bw.close();
		file.delete();
		return parsed;
	}

}
