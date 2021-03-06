package central;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddWorkerView {

	public static void display(ManagerController mc) {
		
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField lastnameField = new JTextField();
        panel.add(new JLabel("Last name :"));
        panel.add(lastnameField);
        
        JTextField nameField = new JTextField();
        panel.add(new JLabel("First name:"));
        panel.add(nameField);
		
        String[] departmentsList = new String[mc.getCompany().getDepartment_List().size()];
        int departmentIndex = 0;
        
        for(Department d : mc.getCompany().getDepartment_List()) {
        	departmentsList[departmentIndex] = d.getName_Department();
        	departmentIndex++;
        }
        
        JComboBox<String> departmentsCombo = new JComboBox<>(departmentsList);
        panel.add(departmentsCombo);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Add worker",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
            	mc.getCompany().getDepartmentByName(departmentsCombo.getSelectedItem().toString()).add_New_Worker_DefaultTime(nameField.getText(), lastnameField.getText());
				mc.getManagerView().updateWorkers(mc.getCompany().getDepartmentByName(departmentsCombo.getSelectedItem().toString()).getId_Department());
            } catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
	
}
