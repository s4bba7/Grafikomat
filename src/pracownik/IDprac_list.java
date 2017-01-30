package pracownik;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GLOBAL.GlobalVariables;

public class IDprac_list {
	private static IDprac_list instance;
	private static File file = new File(GlobalVariables.listaPath);
	private static final ArrayList<Doradca> list = new ArrayList<Doradca>();

	private IDprac_list() {
	}

	public static IDprac_list getInstance() {
		if (instance == null) {
			instance = new IDprac_list();
			list.add(new Doradca("DZIEŃ", 0));
			list.add(new Doradca("DZIEŃ TYGODNIA", 0));

			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "UTF-8"));
				String s = "";
				while ((s = br.readLine()) != null) {
					String[] t = s.split("\\|");
					if (t[0] != null && !t[0].startsWith("#")) { // # pomija
																	// doradcę.
						int db = Integer.parseInt(t[1]);
						list.add(new Doradca(t[0], db));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public ArrayList<Doradca> getList() {
		return list;
	}

}