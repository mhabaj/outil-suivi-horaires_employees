package central;

import java.util.ArrayList;

import javax.swing.JFrame;
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
	
	private JSplitPane splitPane2;
	
	private ArrayList<String> departList = new ArrayList<String>();
	private ArrayList<ArrayList<String>> workerList = new ArrayList<ArrayList<String>>();
	
	private Company comp;

	public ManagerVue(Company comp) {
		
		this.comp = comp;
		
		JTabbedPane tabPane = new JTabbedPane();
		
		JScrollPane scrollPane3 = new JScrollPane();

		departPane = new JScrollPane();
		
		workerPane = new JScrollPane();

		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				workerPane, scrollPane3);
		splitPane2.setDividerLocation(150);
		
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
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

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int index = ((JList)(((JViewport)departPane.getComponents()[0]).getView())).getSelectedIndex();
		updateWList(index);
	}

}
