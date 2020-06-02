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
 * Pane that shows the parameters
 * 
 * @author Alhabaj Mahmod / Belda Tom / Dakroub MohamadAli
 * 
 * 
 */
public class ParametersView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387287974014007457L;
	private JTextField portField;
	private JTextField addressField;
	private MainController emc;

	private JButton changePortButton;
	private JButton changeAddressButton;

	/**
	 * constructor for the class
	 * 
	 * @param emc emulator controller
	 */
	public ParametersView(MainController emc) {
		this.emc = emc;

		// Main panel for the port input
		JPanel portPane = new JPanel();
		BoxLayout portLayout = new BoxLayout(portPane, BoxLayout.X_AXIS);
		portPane.setLayout(portLayout);

		portPane.add(new JLabel("Port : "));

		// Port panel textfield
		portField = new JTextField();
		portField.setMaximumSize(new Dimension(150, 25));
		portField.addActionListener(this);
		portField.setText(String.valueOf(emc.getServerPort()));
		portPane.add(portField);

		// Port panel button
		changePortButton = new JButton("Change");
		changePortButton.addActionListener(this);
		portPane.add(changePortButton);

		// Main panel for the address input
		JPanel addressPane = new JPanel();
		BoxLayout IPLayout = new BoxLayout(addressPane, BoxLayout.X_AXIS);
		addressPane.setLayout(IPLayout);

		addressPane.add(new JLabel("IP : "));

		// Address panel textfield
		addressField = new JTextField();
		addressField.setMaximumSize(new Dimension(150, 25));
		addressField.addActionListener(this);
		addressField.setText(String.valueOf(emc.getServerAddress()));
		addressPane.add(addressField);

		// Address panel button
		changeAddressButton = new JButton("Change");
		changeAddressButton.addActionListener(this);
		addressPane.add(changeAddressButton);

		this.setLayout(new GridLayout(7, 0));
		this.add(portPane);
		this.add(addressPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if the source is from the port button
		if (e.getSource() == changePortButton) {
			// get the text from the field
			int port = Integer.parseInt(portField.getText());
			// verif port value
			if (port < 65535 && port > 0) {
				// change serve settings
				emc.changeServerPort(port);
				System.out.println("Port changed to : " + port + System.lineSeparator());
			} else {
				// show error
				textError();
			}
		}
		// if the source is from the address button
		if (e.getSource() == changeAddressButton) {
			// get the text from the field
			String address = addressField.getText();
			// change serv settings
			emc.changeServerAddress(address);
			System.out.println("Address changed to : " + address + System.lineSeparator());
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
