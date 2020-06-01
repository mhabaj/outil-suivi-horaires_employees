package central;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class CompanyOverviewView extends JPanel implements ActionListener{

	private JScrollPane activityPane;

	private JComboBox<String> departCombo;

	private Company comp;

	public CompanyOverviewView(ManagerController mc) {
		this.comp = mc.getCompany();

		activityPane = new JScrollPane();

		setData();
		updateActivityList();

		BorderLayout mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		this.add(departCombo, BorderLayout.PAGE_START);
		this.add(activityPane, BorderLayout.CENTER);
	}

	public void setData() {
		String[] departNameArray = null;
		if (comp.getDepartment_List() == null) {
			departNameArray = new String[1];
			departNameArray[0] = "-";
			departCombo = new JComboBox<>(departNameArray);
			departCombo.addActionListener(this);

		}
		else {
			departNameArray = new String[comp.getDepartment_List().size() + 1];

			int departIndex = 1;

			departNameArray[0] = "-";

			for (Department depart : comp.getDepartment_List()) {
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

	public void updateData() {
		departCombo.removeAllItems();

		departCombo.addItem("-");

		if (comp.getDepartment_List() != null) {
			int departIndex = 0;

			for (Department depart : comp.getDepartment_List()) {
				try {

					departCombo.addItem(depart.getName_Department());
				} catch (Exception e) {
					e.printStackTrace();
				}
				departIndex++;
			}
		}
	}

	public void updateActivityList() {

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

		HashMap<Worker, WorkingDay> activityList = comp.getCompanyActivityPerDate(date);

		ArrayList<String> activityTempList = new ArrayList<>();

		for (Map.Entry<Worker, WorkingDay> entry : activityList.entrySet()) {
			activityTempList.add(entry.getKey().getLastname_Worker() +" " +entry.getKey().getFirstname_Worker() +" : " +entry.getValue().getArrivalTime() +" - " +entry.getValue().getDepartureTime());
		}

		JList activityJList = new JList(activityTempList.toArray());

		activityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityJList.setLayoutOrientation(JList.VERTICAL);

		activityPane.setViewportView(activityJList);
	}

	public void updateActivityList(Department depart) {
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

		HashMap<Worker, WorkingDay> activityList = depart.getDepartmentActivityPerDate(date);

		ArrayList<String> activityTempList = new ArrayList<>();

		for (Map.Entry<Worker, WorkingDay> entry : activityList.entrySet()) {
			activityTempList.add(entry.getKey().getLastname_Worker() +" " +entry.getKey().getFirstname_Worker() +" : " +entry.getValue().getArrivalTime() +" - " +entry.getValue().getDepartureTime());
		}

		JList activityJList = new JList(activityTempList.toArray());

		activityJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activityJList.setLayoutOrientation(JList.VERTICAL);

		activityPane.setViewportView(activityJList);
	}

	public void updateAll() {
		updateData();
		if(departCombo.getSelectedItem().toString() != "-") {
			try {
				updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			updateActivityList();
	}

	public void updateActivity() {
		if(departCombo.getSelectedItem().toString() != "-") {
			try {
				updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			updateActivityList();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == departCombo) {
			if(departCombo.getSelectedItem() != null) {
				if(departCombo.getSelectedItem().toString() != "-") {
					try {
						updateActivityList(comp.getDepartmentByName(departCombo.getSelectedItem().toString()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else
					updateActivityList();
			}
		}
	}

}
