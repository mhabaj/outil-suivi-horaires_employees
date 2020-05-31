package central;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private JButton delDepartmentButton;

	private JScrollPane workerPane;
	private JScrollPane infosPane;

	private JSplitPane splitPane1;
	private JSplitPane splitPane2;

	private int lastDepartIndex = -1;
	private int lastWorkerIndex = -1;

	private ArrayList<Integer> departList;
	private ArrayList<ArrayList<Integer>> workerList;

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

		delDepartmentButton = new JButton("Delete");
		delDepartmentButton.addActionListener(this);

		JPanel buttonPane = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonPane, BoxLayout.X_AXIS);
		buttonPane.setLayout(buttonLayout);

		buttonPane.add(delDepartmentButton);
		buttonPane.add(addDepartmentButton);

		departPane.add(departScrollPane);
		departPane.add(buttonPane);

		splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workerPane, infosPane);
		splitPane2.setDividerLocation(150);

		splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, departPane, splitPane2);
		splitPane1.setDividerLocation(150);

		setData();

		this.setLayout(new BorderLayout());

		this.add(splitPane1);

	}

	public void setData() {

		departList = new ArrayList<Integer>();
		workerList = new ArrayList<ArrayList<Integer>>();

		ArrayList<Department> departArray = comp.getDepartment_List();

		if(departArray != null) {

			int index = 0;

			for(Department depart : departArray) {
				departList.add(depart.getId_Department());
				workerList.add(new ArrayList<Integer>());

				ArrayList<Worker> workerArray = depart.getWorker_List();
				if(workerArray != null) {
					for(Worker worker : workerArray) {
						workerList.get(index).add(worker.getId_Worker());
					}
				}

				index++;
			}
		}

		updateDList();
	}

	public void updateDList() {
		ArrayList<String> departNameList = new ArrayList<>();
		for(int idDepart : departList) {
			try {
				departNameList.add(comp.getDepartmentByID(idDepart).getName_Department());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		JList dList = new JList(departNameList.toArray());

		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setLayoutOrientation(JList.VERTICAL);

		dList.getSelectionModel().addListSelectionListener(this);

		departScrollPane.setViewportView(dList);
	}

	public void updateWList(int id) {
		ArrayList<String> workerNameList = new ArrayList<>();
		for(int idWorker : workerList.get(id)) {
			try {
				workerNameList.add(comp.getDepartmentByID(departList.get(id)).getWorkerById(idWorker).getLastname_Worker() +" " +comp.getDepartmentByID(departList.get(id)).getWorkerById(idWorker).getFirstname_Worker());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		JList wList = new JList(workerNameList.toArray());

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

		ArrayList<WorkingDay> lastWorkingDays = w.getLastWorkingDays();
		if(lastWorkingDays != null) {
			for(WorkingDay wd : lastWorkingDays) {
				infos.add(wd.getTodaysDate() +" : " +wd.getArrivalTime() +" - " +wd.getDepartureTime());
			}
		}

		jInfo = new JList(infos.toArray());
		infosPane.setViewportView(jInfo);
	}

	public void clearInfos() {
		infosPane.setViewportView(null);
	}

	public void clearWList() {
		workerPane.setViewportView(null);
	}

	public void update() {
		setData();
		updateDList();
		clearWList();
		clearInfos();
		lastDepartIndex = -1;
		lastWorkerIndex = -1;
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
		else if(e.getSource() == delDepartmentButton) {
			int departIndex = ((JList)(((JViewport)departScrollPane.getComponents()[0]).getView())).getSelectedIndex();
			try {
				if(comp.getDepartmentByID(departList.get(departIndex)).getWorker_List() != null) {
					int result = JOptionPane.showConfirmDialog(null, "This department contains workers, are you sure you want to delete it ?", "Delete department",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						comp.deleteDepartment(comp.getDepartmentByID(departList.get(departIndex)));
						lastDepartIndex = -1;
						lastWorkerIndex = -1;
						mv.update();
						clearInfos();
					}
				}
				else {
					comp.deleteDepartment(comp.getDepartmentByID(departList.get(departIndex)));
					lastDepartIndex = -1;
					lastWorkerIndex = -1;
					mv.update();
					clearInfos();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

	}

}
