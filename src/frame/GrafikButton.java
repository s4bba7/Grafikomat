package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import GLOBAL.GlobalVariables;
import util.GrafikMaker;

public class GrafikButton {
	private final String REMOTE_PATH = GlobalVariables.REMOTE_PATH;
	private final String locBTitle = "Generuj grafik";
	private final String remBTitle = "Zdalnie";
	private JButton locButton = new JButton(locBTitle);
	private JButton remButton = new JButton(remBTitle);

	public JButton getLocalButton(final JComboBox yearList,
			final JComboBox monthList) {
		locButton.setToolTipText("Zapisuje w aktualnym folderze programu.");
		locButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final ProgressBar_JFrame wf = new ProgressBar_JFrame();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								locButton.setEnabled(false);
								locButton.setText("Generuję...");
							}
						});
						GrafikMaker gm = new GrafikMaker();
						gm.make("",
								(Integer) yearList.getSelectedItem(),
								(Integer) monthList.getSelectedItem(),
								locButton, locBTitle);
						wf.disable();
					}
				});
				t.start();
				wf.enable();
			}
		});
		return locButton;
	}

	public JButton getRemoteButton(final JComboBox yearList,
			final JComboBox monthList) {
		remButton.setToolTipText("Zapisuje w zdalnym folderze Backoffice/Harmonogram.");
		remButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final ProgressBar_JFrame wf = new ProgressBar_JFrame();
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								remButton.setEnabled(false);
								remButton.setText("Generuję...");
							}
						});
						GrafikMaker gm = new GrafikMaker();
						gm.make(REMOTE_PATH,
								(Integer) yearList.getSelectedItem(),
								(Integer) monthList.getSelectedItem(),
								remButton, remBTitle);
						wf.disable();
					}
				});
				t.start();
				wf.enable();
			}
		});
		return remButton;
	}

}
