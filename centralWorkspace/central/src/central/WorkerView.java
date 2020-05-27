package central;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WorkerView extends JPanel implements ListSelectionListener, ActionListener {

	private JTextField searchField;
	private JButton searchButton;
	private JButton addButton;
	
	private JPanel menuPane;
	private BoxLayout menuLayout;
	
	private JScrollPane workersListPane;
	private JScrollPane workerInfoPane;
	private JSplitPane splitPane;
	
	private int lastWorkerIndex = -1;

	private ArrayList<Integer> workerList = new ArrayList<Integer>();
	
	private Company comp;
	
	public WorkerView(Company comp) {
		this.comp = comp;
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		searchButton = new JButton("search");
		searchButton.addActionListener(this);
		addButton = new JButton("add");
		addButton.addActionListener(this);
		
		menuLayout = new BoxLayout(menuPane, BoxLayout.X_AXIS);
		menuPane = new JPanel();
		
		menuPane.add(searchField);
		menuPane.add(searchButton);
		menuPane.add(addButton);
		
		workersListPane = new JScrollPane();
		workerInfoPane = new JScrollPane();
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workersListPane, workerInfoPane);
		
		setData();
		
		this.setLayout(new BorderLayout());
		
		this.add(menuPane, BorderLayout.PAGE_START);
		this.add(splitPane, BorderLayout.CENTER);
		
	}
	
	public void setData() {

		ArrayList<Department> departArray = comp.getDepartment_List();

		for(Department depart : departArray) {
			
			ArrayList<Worker> workerArray = depart.getWorker_List();
			for(Worker worker : workerArray) {
				workerList.add(worker.getId_Worker());
			}

		}

		updateWList();
	}
	
	public void updateInfo(int workerId) {

		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BorderLayout());
		
		JList<String> infoList;
		ArrayList<String> infoArray = new ArrayList<String>();
		Worker w = null;
		try {
			w = comp.whereIsWorker(workerId).getWorkerById(workerId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		infoArray.add("Nom : " +w.getLastname_Worker());
		infoArray.add("Prenom : " +w.getFirstname_Worker());
		infoArray.add("Horaire par defaut : " +w.getDefault_ArrivalTime_Worker() +" - " +w.getDefault_DepartureTime_Worker());
		infoArray.add("Pointages :");

		infoList = new JList(infoArray.toArray());
		
		Object[][] pointagesList = new Object[w.getNumberWorkedDays()][3];
		
		int indexListFill = 0;
		
		try {
			for(WorkingDay wd : w.getWorkingDays()) {
				pointagesList[indexListFill][0] = wd.getTodaysDate();
				pointagesList[indexListFill][1] = wd.getArrivalTime();
				pointagesList[indexListFill][2] = wd.getDepartureTime();
				indexListFill++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String[] entete = {"Jour", "Heure d'arriv�", "Heure de d�part"};
		
		JTable pointageTab = new JTable(pointagesList, entete);
		pointageTab.setPreferredScrollableViewportSize(new Dimension(0, 0));
		
		JScrollPane pointanePane = new JScrollPane(pointageTab);

		infoPane.add(infoList, BorderLayout.PAGE_START);
		infoPane.add(pointanePane, BorderLayout.CENTER);
		
		workerInfoPane.setViewportView(infoPane);
	}
	
	public void updateWList() {
		ArrayList<String> nameList = new ArrayList<>();
		for(int id : workerList) {
			try {
				nameList.add(comp.whereIsWorker(id).getWorkerById(id).getFirstname_Worker());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JList dList = new JList(nameList.toArray());

		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setLayoutOrientation(JList.VERTICAL);

		dList.getSelectionModel().addListSelectionListener(this);

		workersListPane.setViewportView(dList);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int workerIndex = ((JList)(((JViewport)workersListPane.getComponents()[0]).getView())).getSelectedIndex();
		
		updateInfo(workerList.get(workerIndex));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
	
}