package central;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddDepartmentView {

	public static void display(ManagerController mc) {

		JPanel panel = new JPanel(new GridLayout(0, 1));

		JTextField nameField = new JTextField();
		panel.add(new JLabel("Department name :"));
		panel.add(nameField);

		int result = JOptionPane.showConfirmDialog(null, panel, "Add department",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			try {
				mc.getCompany().add_Department(new Department(nameField.getText(), mc.getCompany()));
				mc.getManagerView().updateAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}