package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MainController;
import model.WorkerModel;

/**
 * 
 * pop up window for adding an entry
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class AddEntryView {

	/**
	 * @param mc manager controller
	 * @param w  worker show the pop up window
	 */
	public static void display(MainController mc, WorkerModel w) {
		JPanel panel = new JPanel(new GridLayout(0, 1));

		// entry field
		JTextField entryField = new JTextField();
		panel.add(new JLabel("Entry (Format : YYYY-MM-DD/HH:mm/workerID) :"));
		panel.add(entryField);

		// get the result from the pop up window
		int result = JOptionPane.showConfirmDialog(null, panel, "Add entry", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		// if the user confirm
		if (result == JOptionPane.OK_OPTION) {
			try {
				// add the entry
				mc.parseEmulatorInput(entryField.getText());
				// update the view
				try {
					int workerID = w.getId_Worker();

					mc.getManagerView().updateInfos(workerID);
				} catch (NullPointerException e) {
					System.out.println("Invalid Entry" + System.lineSeparator());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}