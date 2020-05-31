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
	
	private ManagerController mc;

	public ManagerView(ManagerController mc) {
		
		this.mc = mc;
		
		tabPane = new JTabbedPane();
		
		compOverview = new CompanyOverviewView(this, mc.getCompany());

		workerView = new WorkerView(this, mc.getCompany());
		
		JPanel pane3 = new JPanel();

		tabPane.addTab("Overview", compOverview);
		tabPane.addTab("Workers", workerView);
		tabPane.addTab("tab3", pane3);
		
		this.setContentPane(tabPane);
		this.setSize(600, 400);
		this.setVisible(true);
		
		this.addWindowListener(this);
	}
	
	public void update() {
		compOverview.update();
		workerView.update();
	}

	@Override
	public void windowOpened(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		mc.serializeCompany();
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
