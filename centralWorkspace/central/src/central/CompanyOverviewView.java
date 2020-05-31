package central;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CompanyOverviewView extends JPanel implements ListSelectionListener, ActionListener{

	private JPanel departPane;
	private JScrollPane departScrollPane;
	private JButton addDepartmentButton;
	
	private JScrollPane workerPane;
	private JScrollPane infosPane;

	private JSplitPane splitPane1;
	private JSplitPane splitPane2;

	private int lastDepartIndex = -1;
	private int lastWorkerIndex = -1;

	private ArrayList<String> departList;
	private ArrayList<ArrayList<String>> workerList;

	private Company comp;
	private ManagerView mv;

	public CompanyOverviewView(ManagerView mv, Company comp) {

		this.comp = comp;
		this.mv = mv;

		infosPane = new JScrollPane();

		workerPane = new JScrollPane();

		departPane = new JPanel();
		departPane.setLayout(new BoxLayout(departPane, BoxLayout.Y_AXIS));
		
		departScrollPane = new JScrollPane();
		
		addDepartmentButton = new JButton("Add");
		addDepartmentButton.addActionListener(this);
		
		departPane.add(departScrollPane);
		departPane.add(addDepartmentButton);

		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workerPane, infosPane);
		splitPane2.setDividerLocation(150);

		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, departPane, splitPane2);
		splitPane1.setDividerLocation(150);

		setData();

		this.setLayout(new BorderLayout());

		this.add(splitPane1);

	}

	public void setData() {
		
		departList = new ArrayList<String>();
		workerList = new ArrayList<ArrayList<String>>();

		ArrayList<Department> departArray = comp.getDepartment_List();

		int index = 0;

		for(Department depart : departArray) {
			departList.add(depart.getName_Department());
			workerList.add(new ArrayList<String>());

			ArrayList<Worker> workerArray = depart.getWorker_List();
			for(Worker worker : workerArray) {
				workerList.get(index).add(worker.getFirstname_Worker());
			}

			index++;
		}

		updateDList();
	}

	public void updateDList() {
		JList dList = new JList(departList.toArray());

		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setLayoutOrientation(JList.VERTICAL);

		dList.getSelectionModel().addListSelectionListener(this);

		departScrollPane.setViewportView(dList);
	}

	public void updateWList(int id) {
		JList wList = new JList(workerList.get(id).toArray());

		wList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wList.setLayoutOrientation(JList.VERTICAL);

		wList.getSelectionModel().addListSelectionListener(this);

		workerPane.setViewportView(wList);
	}

	public void updateInfo(int departId, int workerId) {

		JList<String> jInfo;
		ArrayList<String> infos = new ArrayList<String>();
		Worker w = comp.getDepartment_List().get(departId).getWorker_List().get(workerId);

		infos.add("Nom : " +w.getLastname_Worker());
		infos.add("Prenom : " +w.getFirstname_Worker());
		infos.add("Last days :");

		try {
			for(WorkingDay wd : w.getLastWorkingDays()) {
				infos.add(wd.getTodaysDate() +" : " +wd.getArrivalTime() +" - " +wd.getDepartureTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		jInfo = new JList(infos.toArray());
		infosPane.setViewportView(jInfo);
	}

	public void update() {
		setData();
		updateDList();
		if(lastDepartIndex != -1) {
			updateWList(lastDepartIndex);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int departIndex = ((JList)(((JViewport)departScrollPane.getComponents()[0]).getView())).getSelectedIndex();
		if(departIndex !=  lastDepartIndex) {
			lastDepartIndex = departIndex;
			updateWList(departIndex);
			infosPane.setViewportView(null);
		}
		int workerIndex = ((JList)(((JViewport)workerPane.getComponents()[0]).getView())).getSelectedIndex();
		if(workerIndex >= 0) {
			if(workerIndex != lastWorkerIndex) {
				lastWorkerIndex = workerIndex;
				updateInfo(departIndex, workerIndex);
			}
		}
		else {
			lastWorkerIndex = -1;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addDepartmentButton) {
			AddDepartmentView.display(mv, comp);
		}
		
	}

}
