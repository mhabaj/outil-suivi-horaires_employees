package view;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MainController;
import model.DepartmentModel;

/**
 * Pop up window for adding a department
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class AddDepartmentView {

	/**
	 * @param mc manager controller show the pop up window
	 */
	public static void display(MainController mc) {

		JPanel panel = new JPanel(new GridLayout(0, 1));

		// department name field
		JTextField nameField = new JTextField();
		panel.add(new JLabel("Department name :"));
		panel.add(nameField);

		// get the result from the pop up window
		int result = JOptionPane.showConfirmDialog(null, panel, "Add department", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		// if the user confirm
		if (result == JOptionPane.OK_OPTION) {
			if (!nameField.getText().isEmpty()) {
				try {
					// add the department
					mc.getCompany().add_Department(new DepartmentModel(nameField.getText(), mc.getCompany()));
					// update the view
					mc.getManagerView().updateAll();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}