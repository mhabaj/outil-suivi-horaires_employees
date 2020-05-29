package central;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ManagerView extends JFrame {
	
	private JTabbedPane tabPane;
	private CompanyOverviewView compOverview;
	
	private Company comp;

	public ManagerView(Company comp) {
		
		this.comp = comp;
		
		tabPane = new JTabbedPane();
		
		compOverview = new CompanyOverviewView(comp);

		WorkerView workerView = new WorkerView(comp);
		
		JPanel pane3 = new JPanel();

		tabPane.addTab("Overview", compOverview);
		tabPane.addTab("Workers", workerView);
		tabPane.addTab("tab3", pane3);
		
		this.setContentPane(tabPane);
		this.setSize(600, 400);
		this.setVisible(true);
	}

}
