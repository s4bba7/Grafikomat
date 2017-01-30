package logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ConsoleLog {
	private static PrintStream ps;
	private static File file;
	private static boolean isLogExist;

	private static void setup() {
		file = new File("log.txt");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ps = new PrintStream(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setErr(ps);
		System.setOut(ps);
	}

	public static void write(String s) throws IOException {
		if (!isLogExist) {
			setup();
			isLogExist = true;
		}
		ps.write(s.getBytes());
		ps.flush();
	}

	public static void onExit() {
		ps.close();
	}

	public static boolean isLogExist() {
		return isLogExist;
	}
}
