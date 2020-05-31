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
	private BoxLayout menuLayout;

	private JScrollPane workersListPane;
	private JScrollPane workerInfoPane;
	private JSplitPane splitPane;

	private int lastWorkerIndex = -1;

	private ArrayList<Integer> workerList;

	private JList dList;
	private ArrayList<String> nameList;

	private JTextField lastnameField;
	private JTextField nameField;

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

		workerList = new ArrayList<Integer>();

		ArrayList<Department> departArray = comp.getDepartment_List();

		for(Department depart : departArray) {

			ArrayList<Worker> workerArray = depart.getWorker_List();
			if(workerArray != null) {
				for(Worker worker : workerArray) {
					workerList.add(worker.getId_Worker());
				}
			}
		}
		updateWList();
	}

	public void updateWList() {
		nameList = new ArrayList<>();
		for(int id : workerList) {
			try {
				nameList.add(comp.whereIsWorker(id).getWorkerById(id).getLastname_Worker() +" " +comp.whereIsWorker(id).getWorkerById(id).getFirstname_Worker());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		dList = new JList(nameList.toArray());

		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setLayoutOrientation(JList.VERTICAL);

		dList.getSelectionModel().addListSelectionListener(this);

		workersListPane.setViewportView(dList);
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
		updateWList();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		int workerIndex = ((JList)(((JViewport)workersListPane.getComponents()[0]).getView())).getSelectedIndex();

		if(workerIndex != lastWorkerIndex) {
			updateInfo(workerList.get(workerIndex));
			lastWorkerIndex = workerIndex;
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
					lastWorkerIndex = -1;
					mv.update();
					clearInfos();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
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
