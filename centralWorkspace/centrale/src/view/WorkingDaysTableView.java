package view;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import model.WorkerModel;
import model.WorkingDayModel;

/**
 * @author Alhabaj Mahmod /Belda Tom / Dakroub MohamadAli
 * 
 *         Pane that shows all the worked days of a worker
 */
public class WorkingDaysTableView extends JScrollPane implements TableModelListener {

	private JTable workedDaysTab;
	private WorkerModel w;
	private WorkerView wv;

	/**
	 * @param wv worker view
	 * @param w  worker constructor for the scroll pane
	 */
	public WorkingDaysTableView(WorkerView wv, WorkerModel w) {
		this.wv = wv;
		this.w = w;

		// create an array for all the worked days
		Object[][] workedDaysList = new Object[w.getNumberWorkedDays()][3];

		int indexListFill = 0;

		ArrayList<WorkingDayModel> workingDays = w.getWorkingDays();

		if (workingDays != null) {
			for (WorkingDayModel wd : workingDays) {
				workedDaysList[indexListFill][0] = wd.getTodaysDate();
				workedDaysList[indexListFill][1] = wd.getArrivalTime();
				workedDaysList[indexListFill][2] = wd.getDepartureTime();
				indexListFill++;
			}
		}

		// create the header
		String[] workedDaysHeader = { "Day(dd/MM/yyyy)", "Arrival time(HH:mm)", "Departure time(HH:mm)" };

		workedDaysTab = new JTable(workedDaysList, workedDaysHeader);
		workedDaysTab.getModel().addTableModelListener(this);
		workedDaysTab.setPreferredScrollableViewportSize(new Dimension(0, 0));
		workedDaysTab.getTableHeader().setResizingAllowed(false);
		workedDaysTab.getTableHeader().setReorderingAllowed(false);

		this.setViewportView(workedDaysTab);
	}

	@Override
	public void tableChanged(TableModelEvent event) {
		// get the selected row and column
		int row = workedDaysTab.getSelectedRow();
		int column = workedDaysTab.getSelectedColumn();
		// get the value from the cell
		String changedHour = (String) workedDaysTab.getValueAt(row, column);
		try {
			// if it's from second column
			if (column == 1) {
				// change the arrival time
				w.getWorkingDayByDate((String) workedDaysTab.getValueAt(row, 0)).setArrivalTime(changedHour);
				w.addTimeOverflow(w.getWorkingDayByDate((String) workedDaysTab.getValueAt(row, 0)));
			}
			// if it's from third column
			else if (column == 2) {
				// change the column time
				w.getWorkingDayByDate((String) workedDaysTab.getValueAt(row, 0)).setDepartureTime(changedHour);
				w.addTimeOverflow(w.getWorkingDayByDate((String) workedDaysTab.getValueAt(row, 0)));
			}
			// update infos
			wv.updateInfos(w.getId_Worker());
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
