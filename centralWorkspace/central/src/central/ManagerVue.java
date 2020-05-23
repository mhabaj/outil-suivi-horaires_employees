package central;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ManagerVue extends JFrame implements ListSelectionListener {
	
	private JScrollPane departPane;
	private JScrollPane workerPane;
	private JScrollPane infosPane;
	
	private JSplitPane splitPane1;
	private JSplitPane splitPane2;
	
	private int lastDepartIndex = -1;
	private int lastWorkerIndex = -1;
	
	private ArrayList<String> departList = new ArrayList<String>();
	private ArrayList<ArrayList<String>> workerList = new ArrayList<ArrayList<String>>();
	
	private Company comp;

	public ManagerVue(Company comp) {
		
		this.comp = comp;
		
		JTabbedPane tabPane = new JTabbedPane();
		
		infosPane = new JScrollPane();

		departPane = new JScrollPane();
		
		workerPane = new JScrollPane();

		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				workerPane, infosPane);
		splitPane2.setDividerLocation(150);
		
		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				departPane, splitPane2);
		splitPane1.setDividerLocation(150);

		JPanel pane2 = new JPanel();
		JPanel pane3 = new JPanel();

		tabPane.addTab("tab1", splitPane1);
		tabPane.addTab("tab2", pane2);
		tabPane.addTab("tab3", pane3);

		setData();
		
		this.setContentPane(tabPane);
		this.setSize(500, 400);
		this.setVisible(true);
	}
	
	public void setData() {
		
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
		
		departPane.setViewportView(dList);
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
		infos.add("Horaire par defaut : " +w.getDefault_ArrivalTime_Worker() +" - " +w.getDefault_DepartureTime_Worker());
		
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int departIndex = ((JList)(((JViewport)departPane.getComponents()[0]).getView())).getSelectedIndex();
		if(departIndex !=  lastDepartIndex) {
			lastDepartIndex = departIndex;
			updateWList(departIndex);
		}
		int workerIndex = ((JList)(((JViewport)workerPane.getComponents()[0]).getView())).getSelectedIndex();
		if(workerIndex >= 0) {
			if(workerIndex != lastWorkerIndex) {
				lastWorkerIndex = workerIndex;
				updateInfo(departIndex, workerIndex);
			}
		}
	}

}
