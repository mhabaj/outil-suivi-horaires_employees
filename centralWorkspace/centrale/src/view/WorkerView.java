package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.MainController;
import model.CompanyModel;
import model.DepartmentModel;
import model.WorkerModel;

/**
 * 
 * Pane for the management of the workers
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
@SuppressWarnings("rawtypes")
public class WorkerView extends JPanel implements ListSelectionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7858075020052301724L;
	private JTextField searchField;
	private JButton searchButton;
	private JButton addWorkerButton;
	private JButton addEntryFileButton;

	private JPanel menuPane;
	private JScrollPane workerScrollPane;
	private JScrollPane workerInfoPane;
	private JSplitPane infosSplitPane;

	private JPanel departPane;
	private JScrollPane departScrollPane;
	private JButton addDepartButton;
	private JButton delDepartButton;

	private JSplitPane mainSplitPane;

	private int lastDepartIndex = -1;
	private int lastWorkerIndex = -1;

	private ArrayList<Integer> departList;
	private ArrayList<ArrayList<Integer>> workerList;

	private JList departJList;
	private JList workerJList;

	private JTextField lastnameField;
	private JTextField nameField;

	private JComboBox<String> departCombo;

	private JButton addEntryButton;
	private JButton delButton;

	private MainController mc;
	private CompanyModel comp;
	private WorkerModel w;

	/**
	 * @param mc manager controller constructor of the worker's management pane
	 */
	public WorkerView(MainController mc) {

		this.mc = mc;
		this.comp = mc.getCompany();

		// search field
		searchField = new JTextField();
		searchField.setColumns(10);

		// menu buttons
		searchButton = new JButton("Search");
		searchButton.addActionListener(this);
		addWorkerButton = new JButton("Add worker");
		addWorkerButton.addActionListener(this);
		addEntryFileButton = new JButton("Add entry from file");
		addEntryFileButton.addActionListener(this);
		addEntryButton = new JButton("Add entry");
		addEntryButton.addActionListener(this);

		new BoxLayout(menuPane, BoxLayout.X_AXIS);
		menuPane = new JPanel();

		menuPane.add(searchField);
		menuPane.add(searchButton);
		menuPane.add(addWorkerButton);
		menuPane.add(addEntryFileButton);
		menuPane.add(addEntryButton);

		// workers list
		workerScrollPane = new JScrollPane();
		workerInfoPane = new JScrollPane();

		infosSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, workerScrollPane, workerInfoPane);
		infosSplitPane.setDividerLocation(150);

		// departments list
		departPane = new JPanel();
		departPane.setLayout(new BoxLayout(departPane, BoxLayout.Y_AXIS));

		departScrollPane = new JScrollPane();

		// department menu buttons
		addDepartButton = new JButton("Add department");
		addDepartButton.addActionListener(this);

		delDepartButton = new JButton("Delete department");
		delDepartButton.addActionListener(this);

		JPanel buttonPane = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonPane, BoxLayout.X_AXIS);
		buttonPane.setLayout(buttonLayout);

		buttonPane.add(delDepartButton);
		buttonPane.add(addDepartButton);

		departPane.add(departScrollPane);
		departPane.add(buttonPane);

		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, departPane, infosSplitPane);
		mainSplitPane.setDividerLocation(300);

		setData();
		updateDList();

		this.setLayout(new BorderLayout());

		this.add(menuPane, BorderLayout.PAGE_START);
		this.add(mainSplitPane, BorderLayout.CENTER);
	}

	/**
	 * set the data of the worker and department list
	 */
	public void setData() {

		departList = new ArrayList<Integer>();
		workerList = new ArrayList<ArrayList<Integer>>();

		ArrayList<DepartmentModel> departArray = comp.getDepartment_List();

		// add all departments and workers to the lists
		if (departArray != null) {

			int index = 0;

			for (DepartmentModel depart : departArray) {
				departList.add(depart.getId_Department());
				workerList.add(new ArrayList<Integer>());

				ArrayList<WorkerModel> workerArray = depart.getWorker_List();
				if (workerArray != null) {
					for (WorkerModel worker : workerArray) {
						workerList.get(index).add(worker.getId_Worker());
					}
				}

				index++;
			}
		}
	}

	/**
	 * update the department list
	 */
	@SuppressWarnings({ "unchecked" })
	public void updateDList() {
		ArrayList<String> departNameList = new ArrayList<>();
		// get all the departments in the list
		for (int idDepart : departList) {
			try {
				departNameList.add(comp.getDepartmentByID(idDepart).getName_Department());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// recreate the list and add it to the scroll pane
		departJList = new JList(departNameList.toArray());

		departJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		departJList.setLayoutOrientation(JList.VERTICAL);

		departJList.getSelectionModel().addListSelectionListener(this);

		departScrollPane.setViewportView(departJList);
	}

	/**
	 * update the worker list for a department
	 * 
	 * @param departID id of the selected department
	 */
	@SuppressWarnings("unchecked")
	public void updateWList(int departID) {
		ArrayList<String> workerNameList = new ArrayList<>();
		// get all the worker in a list
		for (int idWorker : workerList.get(departID)) {
			try {
				workerNameList.add(
						comp.getDepartmentByID(departList.get(departID)).getWorkerById(idWorker).getLastname_Worker()
								+ " " + comp.getDepartmentByID(departList.get(departID)).getWorkerById(idWorker)
										.getFirstname_Worker());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// recreate the list and add to the scroll pane
		workerJList = new JList(workerNameList.toArray());

		workerJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		workerJList.setLayoutOrientation(JList.VERTICAL);

		workerJList.getSelectionModel().addListSelectionListener(this);

		workerScrollPane.setViewportView(workerJList);
	}

	/**
	 * 
	 * set all the informations on a worker
	 * 
	 * @param workerId id of the selected worker
	 * 
	 * 
	 */
	public void setInfo(int workerId) {

		JPanel infoPane = new JPanel();
		infoPane.setLayout(new BorderLayout());

		JPanel infoList = new JPanel();
		BoxLayout infoBoxLayout = new BoxLayout(infoList, BoxLayout.Y_AXIS);
		infoList.setLayout(infoBoxLayout);

		// get the worker
		try {
			w = comp.whereIsWorker(workerId).getWorkerById(workerId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// set Id
		infoList.add(new JLabel("ID : " + w.getId_Worker()));

		// set the last name
		JPanel lastnamePane = new JPanel();
		BoxLayout lastnameBoxLayout = new BoxLayout(lastnamePane, BoxLayout.X_AXIS);
		lastnamePane.setLayout(lastnameBoxLayout);

		lastnameField = new JTextField(w.getLastname_Worker());
		lastnameField.addActionListener(this);
		lastnamePane.add(new JLabel("Last name : "));
		lastnamePane.add(lastnameField);

		infoList.add(lastnamePane);

		// set the first name
		JPanel namePane = new JPanel();
		BoxLayout nameBoxLayout = new BoxLayout(namePane, BoxLayout.X_AXIS);
		namePane.setLayout(nameBoxLayout);

		nameField = new JTextField(w.getFirstname_Worker());
		nameField.addActionListener(this);
		namePane.add(new JLabel("First name : "));
		namePane.add(nameField);

		infoList.add(namePane);

		// set the time overflow
		infoList.add(new JLabel("Extra time (minutes) : " + w.getWorkingTimeOverflow_Worker()));

		// set the departments list
		String[] departmentsList = new String[comp.getDepartment_List().size()];
		int departmentIndex = 0;
		int workerDepartmentIndex = -1;

		for (DepartmentModel d : comp.getDepartment_List()) {
			departmentsList[departmentIndex] = d.getName_Department();
			try {
				if (d.getName_Department() == comp.whereIsWorker(w.getId_Worker()).getName_Department()) {
					workerDepartmentIndex = departmentIndex;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			departmentIndex++;
		}

		// add the departments to the combo box
		departCombo = new JComboBox<>(departmentsList);
		departCombo.setSelectedIndex(workerDepartmentIndex);
		departCombo.addActionListener(this);

		JPanel departPane = new JPanel();
		BoxLayout departBoxLayout = new BoxLayout(departPane, BoxLayout.X_AXIS);
		departPane.setLayout(departBoxLayout);

		departPane.add(new JLabel("Department : "));
		departPane.add(departCombo);

		infoList.add(departPane);

		// show the default times for the worker
		infoList.add(new JLabel("Schedule (HH:mm) : "));

		DefaultTimeTableView defaultTimeTable = new DefaultTimeTableView(w);

		infoList.add(defaultTimeTable);

		// show all the entry for the worker
		infoList.add(new JLabel("Check in :"));

		WorkingDaysTableView workedDaysTab = new WorkingDaysTableView(this, w);

		// add the delete button
		delButton = new JButton("Delete worker");
		delButton.addActionListener(this);

		JPanel paramPane = new JPanel();

		paramPane.add(delButton);

		infoPane.add(infoList, BorderLayout.PAGE_START);
		infoPane.add(workedDaysTab, BorderLayout.CENTER);
		infoPane.add(paramPane, BorderLayout.PAGE_END);

		workerInfoPane.setViewportView(infoPane);
	}

	/**
	 * clear the info of the worker
	 */
	public void clearInfos() {
		workerInfoPane.setViewportView(null);
	}

	/**
	 * clear the worker list
	 */
	public void clearWList() {
		workerScrollPane.setViewportView(null);
	}

	/**
	 * update the view
	 */
	public void updateAll() {
		setData();
		updateDList();
		clearWList();
		clearInfos();
		// set the selected index to -1
		lastDepartIndex = -1;
		lastWorkerIndex = -1;
	}

	/**
	 * 
	 * update the informations on a worker
	 * 
	 * @param workerID id of the worker
	 * 
	 * 
	 */
	public void updateInfos(int workerID) {
		setData();
		try {
			// if the worker is the selected worker
			if (workerList.get(departList.indexOf(comp.whereIsWorker(workerID).getId_Department()))
					.indexOf(workerID) == lastWorkerIndex) {
				// set his info
				setInfo(workerID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param departID id of the department update the workers from a department
	 */
	public void updateWorkers(int departID) {
		setData();
		// if the department is the selected one
		if (departList.indexOf(departID) == lastDepartIndex) {
			// u^date the informations
			updateWList(departList.indexOf(departID));
			clearInfos();
			lastWorkerIndex = -1;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// get the selected index of the department list
		int departIndex = ((JList) (((JViewport) departScrollPane.getComponents()[0]).getView())).getSelectedIndex();
		// if the selected department changed
		if (departIndex != lastDepartIndex) {
			// set the informations and update the view
			lastDepartIndex = departIndex;
			updateWList(departIndex);
			workerInfoPane.setViewportView(null);
			lastWorkerIndex = -1;
		}
		// get the selected index of the worker list
		int workerIndex = ((JList) (((JViewport) workerScrollPane.getComponents()[0]).getView())).getSelectedIndex();
		if (workerIndex != -1) {
			// if the selected worker changed
			if (workerIndex != lastWorkerIndex) {
				// set the informations and update the view
				lastWorkerIndex = workerIndex;
				setInfo(workerList.get(departIndex).get(workerIndex));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// if the source is the search button
		if (event.getSource() == searchButton) {
			int departIndex = 0;
			int workerIndex = -1;
			// search every workers for the last name and first name in the search field
			for (ArrayList<Integer> tempList : workerList) {
				try {
					workerIndex = tempList.indexOf(comp.getDepartmentByID(departList.get(departIndex))
							.getWorkerByFullName(searchField.getText()).getId_Worker());
				} catch (Exception e) {
				}
				if (workerIndex != -1)
					break;
				departIndex++;
			}
			// if it's in
			if (workerIndex != -1) {
				// select it
				searchField.setText("");
				departJList.setSelectedIndex(departIndex);
				workerJList.setSelectedIndex(workerIndex);
			}
			// else
			else {
				// show error
				textError();
			}
		}
		// if the source is the last name field
		else if (event.getSource() == lastnameField) {
			// change the last name
			w.setLastname_Worker(lastnameField.getText());
			try {
				// update the view
				updateWorkers(comp.whereIsWorker(w.getId_Worker()).getId_Department());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// if the source is the first name field
		else if (event.getSource() == nameField) {
			// change the fist name
			w.setFirstname_Worker(nameField.getText());
			try {
				// update the view
				updateWorkers(comp.whereIsWorker(w.getId_Worker()).getId_Department());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// if the source is the add worker button
		else if (event.getSource() == addWorkerButton) {
			// open the pop up add worker window
			AddWorkerView.display(mc);
		}
		// if the source is the add department button
		else if (event.getSource() == addDepartButton) {
			// open the pop up add department window
			AddDepartmentView.display(mc);
		}
		// if the source is the delete department button
		else if (event.getSource() == delDepartButton) {
			// get the selected department index
			int departIndex = ((JList) (((JViewport) departScrollPane.getComponents()[0]).getView()))
					.getSelectedIndex();
			if (departIndex != -1) {
				try {
					// if the department contains workers
					if (comp.getDepartmentByID(departList.get(departIndex)).getWorker_List() != null) {
						// ask for confirmation
						int result = JOptionPane.showConfirmDialog(null,
								"This department contains workers, are you sure you want to delete it ?",
								"Delete department", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						// if the user say ok again
						if (result == JOptionPane.OK_OPTION) {
							// delete the department
							comp.deleteDepartment(comp.getDepartmentByID(departList.get(departIndex)));
							// update the view
							mc.getManagerView().updateAll();
						}
					}
					// if the department doesn't contain any worker
					else {
						// delete the department
						comp.deleteDepartment(comp.getDepartmentByID(departList.get(departIndex)));
						// update the view
						mc.getManagerView().updateAll();
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
		// if the source is the delete worker button
		else if (event.getSource() == delButton) {
			// ask for confirmation
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this worker ?",
					"Delete worker", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			// if th user say ok again
			if (result == JOptionPane.OK_OPTION) {
				try {
					// delete the worker
					DepartmentModel departTemp = comp.whereIsWorker(w.getId_Worker());
					departTemp.deleteWorker(w);
					// update the view
					updateWorkers(departTemp.getId_Department());
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
		// if the source is the department combo box
		else if (event.getSource() == departCombo) {
			try {
				// change worker's department
				comp.whereIsWorker(w.getId_Worker()).deleteWorker(w);
				comp.getDepartmentByName(departCombo.getSelectedItem().toString()).add_Worker(w);
				// update the view
				mc.getManagerView().updateAll();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// if the source is the add entry button
		else if (event.getSource() == addEntryFileButton) {
			// ask for the file to use
			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter Filefilter = new FileNameExtensionFilter("Text file contains on each line: YYYY-MM-DD/HH:mm/workerID ", "txt", "text");
			filechooser.setFileFilter(Filefilter);
			


			int returnValue = filechooser.showOpenDialog(null);

			// if the user say ok
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// import the entry from the selected file
				String selectedFile = filechooser.getSelectedFile().getAbsolutePath();
				mc.importFromFile(selectedFile);
				// update the view
				mc.getManagerView().updateInfos(w.getId_Worker());
			}
		}
		// if the source is the add entry button
		else if (event.getSource() == addEntryButton) {
			// open the add entry pop up window
			AddEntryView.display(mc, w);
		}
	}

	/**
	 * highlight the text area when there is an error
	 */
	public void textError() {
		// set the port field borders in red
		searchField.setBorder(new LineBorder(Color.RED, 2));

		// put a timer to remove the border in 2 sec
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				searchField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			}
		}, 2000);
	}

}
