package view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import controller.MainController;

/**
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 *         Frame for the application
 */
public class MainView extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8219601825753073131L;
	private JTabbedPane tabPane;
	private CompanyOverviewView compOverview;
	private WorkerView workerView;
	private ParametersView paramView;

	private MainController mc;

	/**
	 * @param mc manager controller create the frame for the application
	 */
	public MainView(MainController mc) {

		this.mc = mc;

		// create the tabs
		tabPane = new JTabbedPane();

		// add the activity overview view
		compOverview = new CompanyOverviewView(mc);

		// add the worker managment view
		workerView = new WorkerView(mc);

		// add the parameters view
		paramView = new ParametersView(mc);

		tabPane.addTab("Overview", compOverview);
		tabPane.addTab("Workers", workerView);
		tabPane.addTab("Parameters", paramView);

		this.setContentPane(tabPane);
		this.setSize(1100, 800);
		this.setVisible(true);

		this.addWindowListener(this);
	}

	/**
	 * update all the view
	 */
	public void updateAll() {
		compOverview.updateAll();
		workerView.updateAll();
	}

	/**
	 * @param workerID id of the worker update the informations for a worker
	 */
	public void updateInfos(int workerID) {
		compOverview.updateActivity();
		workerView.updateInfos(workerID);
	}

	/**
	 * @param departID id of the deartment update the informations for a department
	 */
	public void updateWorkers(int departID) {
		compOverview.updateAll();
		workerView.updateWorkers(departID);
	}

	/**
	 * @param server server status change the server status
	 */
	public void setServerStatus(boolean server) {
		paramView.setServerStatu(server);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// serialize the data before closing the app;
		mc.serializeCompany();
		mc.serializeServerSettings();
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

}
