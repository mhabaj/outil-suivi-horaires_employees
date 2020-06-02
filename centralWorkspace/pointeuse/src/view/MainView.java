package view;

import javax.swing.JFrame;

import javax.swing.JTabbedPane;

import controller.MainController;

/**
 * 
 * frame for the time clock application
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1038445110009748435L;

	private JTabbedPane tabPane;

	private TimeClockView timeClock;

	private ParametersView param;

	/**
	 * constructor for the time clock frame
	 * 
	 * @param emc emulator controller
	 */
	public MainView(MainController emc) {

		// create tabs
		tabPane = new JTabbedPane();

		// create the time clock view
		timeClock = new TimeClockView(emc);

		tabPane.addTab("Time clock", timeClock);

		// create the parameters view
		param = new ParametersView(emc);

		tabPane.addTab("Parameters", param);

		this.setTitle("Check-in Client");
		this.setContentPane(tabPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 300);
		this.setVisible(true);
	}

	/**
	 * set the current time on the time clock
	 * 
	 * @param time current time
	 */
	public void setTime(String time) {
		timeClock.setTime(time);
	}

	/**
	 * set the rounded time on the clock
	 * 
	 * @param time rounded time
	 */
	public void setRoundedTime(String time) {
		timeClock.setRoundedTime(time);
	}

}