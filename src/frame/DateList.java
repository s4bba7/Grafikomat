package frame;

import javax.swing.JComboBox;

public class DateList {

	public JComboBox getYears(int from, int to) {
		JComboBox jbox = new JComboBox();
		for (int i = from; i <= to; i++)
			jbox.addItem(i);
		return jbox;
	}
	
	public JComboBox getMonths() {
		JComboBox jbox = new JComboBox();
		for (int i = 1; i < 13; i++)
			jbox.addItem(i);
		return jbox;
	}

}
