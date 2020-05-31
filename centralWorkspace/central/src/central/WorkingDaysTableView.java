package central;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class WorkingDaysTableView extends JScrollPane implements TableModelListener {

	private JTable workedDaysTab;
	private Worker w;

	public WorkingDaysTableView(Worker w) {

		this.w = w;

		Object[][] workedDaysList = new Object[w.getNumberWorkedDays()][3];

		int indexListFill = 0;

		ArrayList<WorkingDay> workingDays = w.getWorkingDays();
		
		if(workingDays != null) {
			for(WorkingDay wd : workingDays) {
				workedDaysList[indexListFill][0] = wd.getTodaysDate();
				workedDaysList[indexListFill][1] = wd.getArrivalTime();
				workedDaysList[indexListFill][2] = wd.getDepartureTime();
				indexListFill++;
			}
		}

		String[] workedDaysHeader = {"Jour", "Heure d'arriv�", "Heure de d�part"};

		workedDaysTab = new JTable(workedDaysList, workedDaysHeader);
		workedDaysTab.getModel().addTableModelListener(this);
		workedDaysTab.setPreferredScrollableViewportSize(new Dimension(0, 0));
		workedDaysTab.getTableHeader().setResizingAllowed(false);
		workedDaysTab.getTableHeader().setReorderingAllowed(false);

		this.setViewportView(workedDaysTab);
	}

	@Override
	public void tableChanged(TableModelEvent event) {
		int row = workedDaysTab.getSelectedRow();
		int column = workedDaysTab.getSelectedColumn();
		String changedHour = (String)workedDaysTab.getValueAt(row, column);
		try {
			if(column == 1)
				w.getWorkingDayByDate((String)workedDaysTab.getValueAt(row, 0)).setArrivalTime(changedHour);
			else if(column == 2)
				w.getWorkingDayByDate((String)workedDaysTab.getValueAt(row, 0)).setDepartureTime(changedHour);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
