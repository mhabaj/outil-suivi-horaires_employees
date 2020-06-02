package view;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.WorkerModel;

/**
 * 
 * Table of the default time for a worker
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class DefaultTimeTableView extends JScrollPane implements TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4660008490663951414L;

	private JTable defaultTimeTab;

	private WorkerModel w;

	/**
	 * @param w worker constructor for the default time table
	 */
	public DefaultTimeTableView(WorkerModel w) {

		this.w = w;

		// get all the default time in an array
		Object[][] defaultTimeList = new Object[2][5];

		for (int loopDays = 0; loopDays < 5; loopDays++) {
			defaultTimeList[0][loopDays] = w.getDefault_ArrivalTime_Worker_ByWeekDayCode(loopDays);
			defaultTimeList[1][loopDays] = w.getDefault_DepartureTime_Worker_ByWeekDayCode(loopDays);
		}

		// set the header of the table
		String[] defaultTimeHeader = { "Monday(HH:mm)", "Tuesday(HH:mm)", "Wednesday(HH:mm)", "Thursday(HH:mm)", "Friday(HH:mm)" };

		defaultTimeTab = new JTable(defaultTimeList, defaultTimeHeader);
		defaultTimeTab.getModel().addTableModelListener(this);
		defaultTimeTab.getTableHeader().setResizingAllowed(false);
		defaultTimeTab.getTableHeader().setReorderingAllowed(false);

		this.setViewportView(defaultTimeTab);
		this.setPreferredSize(new Dimension(300, 55));
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		// get the selected row and column
		int row = defaultTimeTab.getSelectedRow();
		int column = defaultTimeTab.getSelectedColumn();
		// get the selected time
		String changedHour = (String) defaultTimeTab.getValueAt(row, column);
		try {
			// change the default time
			if (row == 0) {
				switch (column) {
				case 0:
					w.setDefault_ArrivalTime_Worker("Monday", changedHour);
					break;
				case 1:
					w.setDefault_ArrivalTime_Worker("Tuesday", changedHour);
					break;
				case 2:
					w.setDefault_ArrivalTime_Worker("Wednesday", changedHour);
					break;
				case 3:
					w.setDefault_ArrivalTime_Worker("Thursday", changedHour);
					break;
				case 4:
					w.setDefault_ArrivalTime_Worker("Friday", changedHour);
					break;
				}
			} else if (row == 1) {
				switch (column) {
				case 0:
					w.setDefault_DepartureTime_Worker("Monday", changedHour);
					break;
				case 1:
					w.setDefault_DepartureTime_Worker("Tuesday", changedHour);
					break;
				case 2:
					w.setDefault_DepartureTime_Worker("Wednesday", changedHour);
					break;
				case 3:
					w.setDefault_DepartureTime_Worker("Thursday", changedHour);
					break;
				case 4:
					w.setDefault_DepartureTime_Worker("Friday", changedHour);
					break;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
