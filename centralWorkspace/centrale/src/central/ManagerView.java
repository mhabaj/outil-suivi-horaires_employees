package central;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ManagerView extends JFrame implements WindowListener{
	
	private JTabbedPane tabPane;
	private CompanyOverviewView compOverview;
	private WorkerView workerView;
	private ParametersView paramView;
	
	private ManagerController mc;

	public ManagerView(ManagerController mc) {
		
		this.mc = mc;
		
		tabPane = new JTabbedPane();
		
		compOverview = new CompanyOverviewView(mc.getCompany());

		workerView = new WorkerView(this, mc.getCompany());
		
		paramView = new ParametersView(mc);

		tabPane.addTab("Overview", compOverview);
		tabPane.addTab("Workers", workerView);
		tabPane.addTab("Parameters", paramView);
		
		this.setContentPane(tabPane);
		this.setSize(900, 600);
		this.setVisible(true);
		
		this.addWindowListener(this);
	}
	
	public void update() {
		compOverview.update();
		workerView.updateAll();
	}
	
	public void updateInfos(int workerID) {
		compOverview.update();
		workerView.updateInfos(workerID);
	}
	
	public void updateWorkers(int departID) {
		compOverview.update();
		workerView.updateWorkers(departID);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		mc.serializeCompany();
		mc.serializeServerSettings();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowIconified(WindowEvent e) {}

	@Override
	public void windowDeiconified(WindowEvent e) {}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowDeactivated(WindowEvent e) {}

}
