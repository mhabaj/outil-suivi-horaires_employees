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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WorkerView extends JPanel implements ListSelectionListener, ActionListener {

	private JTextField searchField;
	private JButton searchButton;
	private JButton addButton;
	
	private JPanel menuPane;
	private BoxLayout menuLayout;
	
	private JScrollPane scrollPane;
	private JPanel pane;
	private JSplitPane splitPane;

	private ArrayList<String> workerList = new ArrayList<String>();
	
	private Company comp;
	
	public WorkerView(Company comp) {
		this.comp = comp;
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		searchButton = new JButton("search");
		searchButton.addActionListener(this);
		addButton = new JButton("add");
		searchButton.addActionListener(this);
		
		menuLayout = new BoxLayout(menuPane, BoxLayout.X_AXIS);
		menuPane = new JPanel();
		
		menuPane.add(searchField);
		menuPane.add(searchButton);
		menuPane.add(addButton);
		
		scrollPane = new JScrollPane();
		pane = new JPanel();
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, pane);
		
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
				workerList.add(worker.getFirstname_Worker());
			}

		}

		updateWList();
	}
	
	public void updateWList() {
		JList dList = new JList(workerList.toArray());

		dList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dList.setLayoutOrientation(JList.VERTICAL);

		dList.getSelectionModel().addListSelectionListener(this);

		scrollPane.setViewportView(dList);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		System.out.println("tu suce ?");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
	
}
