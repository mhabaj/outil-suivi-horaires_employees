package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controller.MainController;

/**
 * 
 * Pane to set the parameters of the application
 * 
 * @author Alhabaj Mahmod/ Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class ParametersView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6938212046127775403L;
	private JTextField portField;
	private MainController mc;

	private JButton changePortButton;

	private JLabel serverStatus;

	/**
	 * @param mc manager controller constructor of the parameters pane
	 */
	public ParametersView(MainController mc) {
		this.mc = mc;

		JPanel mainPane = new JPanel();
		BoxLayout menuLayout = new BoxLayout(mainPane, BoxLayout.X_AXIS);
		mainPane.setLayout(menuLayout);

		// port pane
		mainPane.add(new JLabel("Port : "));

		// port text field
		portField = new JTextField();
		portField.setMaximumSize(new Dimension(150, 25));
		portField.setText(String.valueOf(mc.getServerPort()));
		mainPane.add(portField);

		// port change button
		changePortButton = new JButton("Change");
		changePortButton.addActionListener(this);
		mainPane.add(changePortButton);

		// server status pane
		serverStatus = new JLabel();

		this.setLayout(new GridLayout(10, 0));
		this.add(mainPane);
		this.add(serverStatus);
	}

	/**
	 * @param server boolean for server status change servr status
	 */
	public void setServerStatu(boolean server) {
		if (server)
			serverStatus.setText("Server Status : UP");
		else
			serverStatus.setText("Server Status : DOWN");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if the source is the port change button
		if (e.getSource() == changePortButton) {
			// get the value from the field
			int port = Integer.parseInt(portField.getText());
			// verify the port
			if (port < 65535 && port > 0) {
				// if it's different from the one the application already have
				if (port != mc.getServerPort())
					// change the port
					mc.changeServerConfig(port);
			} else {
				textError();
			}
		}
	}

	/**
	 * highlight the text area when there is an error
	 */
	public void textError() {
		// set the port field borders in red
		portField.setBorder(new LineBorder(Color.RED, 2));

		// put a timer to remove the border in 2 sec
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				portField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			}
		}, 2000);
	}
}
