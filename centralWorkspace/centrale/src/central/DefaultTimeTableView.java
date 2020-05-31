package central;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class DefaultTimeTableView extends JScrollPane implements TableModelListener {

	private JTable defaultTimeTab;

	private Worker w;

	public DefaultTimeTableView(Worker w) {

		this.w = w;

		Object[][] defaultTimeList = new Object[2][5];

		for(int loopDays = 0; loopDays < 5; loopDays++) {
			defaultTimeList[0][loopDays] = w.getDefault_ArrivalTime_Worker_ByWeekDayCode(loopDays);
			defaultTimeList[1][loopDays] = w.getDefault_DepartureTime_Worker_ByWeekDayCode(loopDays);
		}

		String[] defaultTimeHeader = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

		defaultTimeTab = new JTable(defaultTimeList, defaultTimeHeader);
		defaultTimeTab.getModel().addTableModelListener(this);
		defaultTimeTab.getTableHeader().setResizingAllowed(false);
		defaultTimeTab.getTableHeader().setReorderingAllowed(false);

		this.setViewportView(defaultTimeTab);
		this.setPreferredSize(new Dimension(300, 55));
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = defaultTimeTab.getSelectedRow();
		int column = defaultTimeTab.getSelectedColumn();
		String changedHour = (String)defaultTimeTab.getValueAt(row, column);
		try {
			if(row == 0) {
				switch(column) {
				case 0 :
					w.setDefault_ArrivalTime_Worker("Monday", changedHour);
					break;
				case 1 :
					w.setDefault_ArrivalTime_Worker("Tuesday", changedHour);
					break;
				case 2 :
					w.setDefault_ArrivalTime_Worker("Wednesday", changedHour);
					break;
				case 3 :
					w.setDefault_ArrivalTime_Worker("Thursday", changedHour);
					break;
				case 4 :
					w.setDefault_ArrivalTime_Worker("Friday", changedHour);
					break;
				}
			}
			else if(row == 0) {
				switch(column) {
				case 0 :
					w.setDefault_DepartureTime_Worker("Monday", changedHour);
					break;
				case 1 :
					w.setDefault_DepartureTime_Worker("Tuesday", changedHour);
					break;
				case 2 :
					w.setDefault_DepartureTime_Worker("Wednesday", changedHour);
					break;
				case 3 :
					w.setDefault_DepartureTime_Worker("Thursday", changedHour);
					break;
				case 4 :
					w.setDefault_DepartureTime_Worker("Friday", changedHour);
					break;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

}
