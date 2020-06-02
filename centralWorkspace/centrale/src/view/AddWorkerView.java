package view;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.MainController;
import model.DepartmentModel;

/**
 * pop up window for adding a worker
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 */
public class AddWorkerView {

	/**
	 * @param mc mnager controller displar the pop up window
	 */
	public static void display(MainController mc) {

		JPanel panel = new JPanel(new GridLayout(0, 1));

		// last name field
		JTextField lastnameField = new JTextField();
		panel.add(new JLabel("Last name :"));
		panel.add(lastnameField);

		// fist name field
		JTextField nameField = new JTextField();
		panel.add(new JLabel("First name:"));
		panel.add(nameField);

		if (mc.getCompany().getDepartment_List() != null) {
			// get all the departments in a combo box
			String[] departmentsList = new String[mc.getCompany().getDepartment_List().size()];
			int departmentIndex = 0;

			for (DepartmentModel d : mc.getCompany().getDepartment_List()) {
				departmentsList[departmentIndex] = d.getName_Department();
				departmentIndex++;
			}

			JComboBox<String> departmentsCombo = new JComboBox<>(departmentsList);
			panel.add(departmentsCombo);

			// get the result of the pop up window
			int result = JOptionPane.showConfirmDialog(null, panel, "Add worker", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE);
			// if the user confirm
			if (result == JOptionPane.OK_OPTION) {
				if (!nameField.getText().isEmpty() && !lastnameField.getText().isEmpty())
					try {
						// add the worker
						mc.getCompany().getDepartmentByName(departmentsCombo.getSelectedItem().toString())
								.add_New_Worker_DefaultTime(nameField.getText(), lastnameField.getText());
						// update the view
						mc.getManagerView().updateWorkers(mc.getCompany()
								.getDepartmentByName(departmentsCombo.getSelectedItem().toString()).getId_Department());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
	}

}
