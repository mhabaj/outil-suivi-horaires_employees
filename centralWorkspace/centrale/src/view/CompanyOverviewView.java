package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import controller.MainController;
import model.CompanyModel;
import model.DepartmentModel;
import model.WorkerModel;
import model.WorkingDayModel;

/**
 * 
 * pane that shows the main activity of the company
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */

@SuppressWarnings("rawtypes")
public class CompanyOverviewView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 901752000030729424L;

	private JScrollPane activityPane;

	private JComboBox<String> departCombo;

	private CompanyModel comp;

	/**
	 * constructor for the pane
	 * 
	 * @param mc manager controller
	 */
	public CompanyOverviewView(MainController mc) {
		this.comp = mc.getCompany();

		// pane that shows the workers informations of the day
		activityPane = new JScrollPane();

		// set all the data
		setData();
		updateActivityList();

		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		this.add(departCombo, BorderLayout.PAGE_START);
		this.add(activityPane, BorderLayout.CENTER);
	}

	/**
	 * get all the departments for the combo box
	 */
	public void setData() {
		String[] departNameArray = null;
		// if there is no departments
		if (comp.getDepartment_List() == null) {
			// just add the default option
			departNameArray = new String[1];
			departNameArray[0] = "-";
			departCombo = new JComboBox<>(departNameArray);
			departCombo.addActionListener(this);

		}
		// if there is departments
		else {
			departNameArray = new String[comp.getDepartment_List().size() + 1];

			int departIndex = 1;

			// add the default option
			departNameArray[0] = "-";

			// add all the departments to the combo box
			for (DepartmentModel depart : comp.getDepartment_List()) {
				try {
					departNameArray[departIndex] = depart.getName_Department();
				} catch (Exception e) {
					e.printStackTrace();
				}
				departIndex++;
			}

			departCombo = new JComboBox<>(departNameArray);
			departCombo.addActionListener(this);

		}
	}

	/**
	 * update the data from the combo box
	 */
	public void updateData() {
		// remove all items
		departCombo.removeAllItems();

		// add the default option
		departCombo.addItem("-");

		// if there is departments
		if (comp.getDepartment_List() != null) {

			// add all the departments in the combo box
			for (DepartmentModel depart : comp.getDepartment_List()) {
				try {

					departCombo.addItem(depart.getName_Department());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * update workers informations of the day
	 */
	@SuppressWarnings("unchecked")
	public void updateActivityList() {

		// get today's date
		String datetmp = LocalDate.now().toString();

		DateFormat oldTMP = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		DateFormat newTMP = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		Date dateTMP = null;
		try {
			dateTMP = oldTMP.parse(datetmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date = newTMP.format(dateTMP);

		// get all activity of the day
		HashMap<WorkerModel, WorkingDayModel> activityList = comp.getCompanyActivityPerDate(date);

		// add all activity to the scroll pane
		ArrayList<String> activityTempList = new ArrayList<>();

		for (Map.Entry<WorkerModel, WorkingDayModel> entry : activityList.entrySet()) {
			activityTempList.add(entry.getKey().getLastname_Worker() + " " + entry.getKey().getFirstname_Worker()
					+ " : " + entry.getValue().getArrivalTime() + " - " + entry.getValue().getDepartureTime());
		}

		JList activityJList = new JList(activityTempList.toArray());

		activityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityJList.setLayoutOrientation(JList.VERTICAL);

		activityPane.setViewportView(activityJList);
	}

	/**
	 * update workers informations of the day for a departments
	 * 
	 * @param depart selected department
	 */
	@SuppressWarnings("unchecked")
	public void updateActivityList(DepartmentModel depart) {
		// get today's date
		String datetmp = LocalDate.now().toString();

		DateFormat oldTMP = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		DateFormat newTMP = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
		Date dateTMP = null;
		try {
			dateTMP = oldTMP.parse(datetmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date = newTMP.format(dateTMP);

		// get all activity of the day for the department
		HashMap<WorkerModel, WorkingDayModel> activityList = depart.getDepartmentActivityPerDate(date);

		// add all activity to the scroll pane
		ArrayList<String> activityTempList = new ArrayList<>();

		for (Map.Entry<WorkerModel, WorkingDayModel> entry : activityList.entrySet()) {
			activityTempList.add(entry.getKey().getLastname_Worker() + " " + entry.getKey().getFirstname_Worker()
					+ " : " + entry.getValue().getArrivalTime() + " - " + entry.getValue().getDepartureTime());
		}

		JList activityJList = new JList(activityTempList.toArray());

		activityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityJList.setLayoutOrientation(JList.VERTICAL);

		activityPane.setViewportView(activityJList);
	}

	/**
	 * update all the view
	 */
	public void updateAll() {
		// update the data in the combo box
		updateData();
		// if selected element isn't the default option
		if (departCombo.getSelectedItem().toString() != "-") {
			try {
				// update the activity for the department
				updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			// update all activity
			updateActivityList();
	}

	/**
	 * update the activity
	 */
	public void updateActivity() {
		// if selected element isn't the default option
		if (departCombo.getSelectedItem().toString() != "-") {
			try {
				// update the activity for the department
				updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			// update all activity
			updateActivityList();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == departCombo) {
			if (departCombo.getSelectedItem() != null) {
				if (departCombo.getSelectedItem().toString() != "-") {
					try {
						updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else
					updateActivityList();
			}
		}
	}

}
