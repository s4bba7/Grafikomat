package frame;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import pracownik.IDprac_list;

public class ProgressBar_JFrame extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3858947856067508512L;
	private int x, y;
	private int width = MainFrame.width, height = MainFrame.height;
	private static JProgressBar bar;
	private static IDprac_list idPracList = IDprac_list.getInstance();

	public void enable() {
		x = (int) MainFrame.getPosition().getX();
		y = (int) MainFrame.getPosition().getY();

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setModal(true);
		setUndecorated(true);
		setBounds(x, y, width, height);

		int max = idPracList.getList().size() - 2; // wartość -2 z pętli for z GrafikMaker linia 43.
		bar = new JProgressBar(0, max);
		bar.setStringPainted(true);
		bar.setString("Pracuję");
		add(bar);
		setVisible(true);
	}

	public void disable() {
		setVisible(false);
	}

	public static void setValue(int i) {
		bar.setValue(i);
	}
}
