package central;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WorkerView extends JPanel implements ListSelectionListener, ActionListener {

	private JTextField searchField;
	private JButton searchButton;
	private JButton addButton;

	private JPanel menuPane;
	private JScrollPane workersListPane;
	private JScrollPane workerInfoPane;
	private JSplitPane infosSplitPane;
	
	private JPanel departPane;
	private JScrollPane departScrollPane;
	private JButton addDepartmentButton;
	private JButton delDepartmentButton;
	
	private JSplitPane mainSplitPane;

	private int lastDepartIndex = -1;
	private int lastWorkerIndex = -1;

	private ArrayList<Integer> departList;
	private ArrayList<ArrayList<Integer>> workerList;

	private JList dList;
	private ArrayList<String> nameList;

	private JTextField lastnameField;
	private JTextField nameField;
	
	private JComboBox<String> departmentsCombo;

	private JButton delButton;

	private ManagerView mv;
	private Company comp;
	private Worker w;

	public WorkerView(ManagerView mv, Company comp) {

		this.mv = mv;
		this.comp = comp;

		searchField = new JTextField();
		searchField.setColumns(10);

		searchButton = new JButton("search");
		searchButton.addActionListener(this);
		addButton = new JButton("add");
		addButton.addActionListener(this);

		new BoxLayout(menuPane, BoxLayout.X_AXIS);
		menuPane = new JPanel();

		menuPane.add(searchField);
		menuPane.add(searchButton);
		menuPane.add(addButton);

		workersListPane = new JScrollPane();
		workerInfoPane = new JScrollPane();

		infosSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workersListPane, workerInfoPane);

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
		
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, departPane, infosSplitPane);
		mainSplitPane.setDividerLocation(150);
		
		setData();

		this.setLayout(new BorderLayout());

		this.add(menuPane, BorderLayout.PAGE_START);
		this.add(mainSplitPane, BorderLayout.CENTER);

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

		workersListPane.setViewportView(wList);
	}

	public void updateInfo(int workerId) {

		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BorderLayout());

		JPanel infoList = new JPanel();
		BoxLayout infoBoxLayout = new BoxLayout(infoList, BoxLayout.Y_AXIS);
		infoList.setLayout(infoBoxLayout);

		try {
			w = comp.whereIsWorker(workerId).getWorkerById(workerId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		infoList.add(new JLabel("ID : " +w.getId_Worker()));

		JPanel lastnamePane = new JPanel();
		BoxLayout lastnameBoxLayout = new BoxLayout(lastnamePane, BoxLayout.X_AXIS);
		lastnamePane.setLayout(lastnameBoxLayout);

		lastnameField = new JTextField(w.getLastname_Worker());
		lastnameField.addActionListener(this);
		lastnamePane.add(new JLabel("Nom : "));
		lastnamePane.add(lastnameField);

		infoList.add(lastnamePane);

		JPanel namePane = new JPanel();
		BoxLayout nameBoxLayout = new BoxLayout(namePane, BoxLayout.X_AXIS);
		namePane.setLayout(nameBoxLayout);

		nameField = new JTextField(w.getFirstname_Worker());
		nameField.addActionListener(this);
		namePane.add(new JLabel("Prenom : "));
		namePane.add(nameField);

		infoList.add(namePane);
		
		String[] departmentsList = new String[comp.getDepartment_List().size()];
        int departmentIndex = 0;
        int workerDepartmentIndex = -1;
        
        for(Department d : comp.getDepartment_List()) {
        	departmentsList[departmentIndex] = d.getName_Department();
        	try {
				if(d.getName_Department() == comp.whereIsWorker(w.getId_Worker()).getName_Department()) {
					workerDepartmentIndex = departmentIndex;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        	departmentIndex++;
        }
        
        departmentsCombo = new JComboBox<>(departmentsList);
        departmentsCombo.setSelectedIndex(workerDepartmentIndex);
        departmentsCombo.addActionListener(this);
        
        JPanel departPane = new JPanel();
		BoxLayout departBoxLayout = new BoxLayout(departPane, BoxLayout.X_AXIS);
		departPane.setLayout(departBoxLayout);
		
		departPane.add(new JLabel("Department : "));
		departPane.add(departmentsCombo);
        
        infoList.add(departPane);

		infoList.add(new JLabel("Horaires par defaut : "));

		DefaultTimeTableView defaultTimeTable = new DefaultTimeTableView(w);

		infoList.add(defaultTimeTable);

		infoList.add(new JLabel("Pointages :"));

		WorkingDaysTableView workedDaysTab = new WorkingDaysTableView(w);

		delButton = new JButton("delete");
		delButton.addActionListener(this);

		JPanel paramPane = new JPanel();
		BoxLayout paramLayout = new BoxLayout(paramPane, BoxLayout.X_AXIS);

		paramPane.add(delButton);

		infoPane.add(infoList, BorderLayout.PAGE_START);
		infoPane.add(workedDaysTab, BorderLayout.CENTER);
		infoPane.add(paramPane, BorderLayout.PAGE_END);

		workerInfoPane.setViewportView(infoPane);
	}

	public void clearInfos() {
		workerInfoPane.setViewportView(null);
	}

	public void update() {
		setData();
		clearInfos();
		lastWorkerIndex = -1;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int departIndex = ((JList)(((JViewport)departScrollPane.getComponents()[0]).getView())).getSelectedIndex();
		if(departIndex !=  lastDepartIndex) {
			lastDepartIndex = departIndex;
			updateWList(departIndex);
			workerInfoPane.setViewportView(null);
		}
		int workerIndex = ((JList)(((JViewport)workersListPane.getComponents()[0]).getView())).getSelectedIndex();
		if(workerIndex != -1) {
			if(workerIndex != lastWorkerIndex) {
				lastWorkerIndex = workerIndex;
				updateInfo(workerList.get(departIndex).get(workerIndex));
			}
		}
		else {
			lastWorkerIndex = -1;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == searchButton) {
			int index = nameList.indexOf(searchField.getText());
			if(index != -1) {
				searchField.setText("");
				dList.setSelectedIndex(index);
			}
			else {
				textError();
			}
		}
		else if(event.getSource() == lastnameField) {
			w.setLastname_Worker(lastnameField.getText());
			mv.update();
		}
		else if(event.getSource() == nameField) {
			w.setFirstname_Worker(nameField.getText());
			mv.update();
		}
		else if(event.getSource() == addButton) {
			AddWorkerView.display(mv, comp);
		}
		else if(event.getSource() == delButton) {
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this worker ?", "Delete worker",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				try {
					comp.whereIsWorker(w.getId_Worker()).deleteWorker(w);
					mv.update();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
		else if(event.getSource() == departmentsCombo) {
			try {
				comp.whereIsWorker(w.getId_Worker()).deleteWorker(w);
				comp.getDepartmentByName(departmentsCombo.getSelectedItem().toString()).add_Worker(w);
				mv.update();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void textError() {
		searchField.setBorder(new LineBorder(Color.RED, 2));

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				searchField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			}
		}, 2000);
	}

}
