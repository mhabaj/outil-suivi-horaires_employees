package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
 * pane that shows the clock
 * 
 * @author Alhabaj Mahmod/ Belda Tom / Dakroub MohamadAli
 */
public class TimeClockView extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5885399341786570866L;
	private MainController emc;
	private JPanel timePane = new JPanel();
	private JPanel entryPane = new JPanel();
	private BorderLayout mainLayout;
	private GridLayout timeLayout;
	private BoxLayout entryLayout;
	private JLabel timeLabel;
	private JLabel roundedTimeLabel;
	private JTextField idField;
	private JButton sendIdButton;

	/**
	 * constructor for the clock
	 * 
	 * @param emc emulator controller
	 */
	public TimeClockView(MainController emc) {

		this.emc = emc;

		// field for the id to send
		idField = new JTextField();
		idField.setColumns(5);
		idField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		// button to send id
		sendIdButton = new JButton("IN/OUT");
		sendIdButton.addActionListener(this);

		entryLayout = new BoxLayout(entryPane, BoxLayout.X_AXIS);
		entryPane.setLayout(entryLayout);

		entryPane.add(idField);
		entryPane.add(sendIdButton);

		// label to show the time
		timeLabel = new JLabel("");
		timeLabel.setFont(timeLabel.getFont().deriveFont(50.0f));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

		// label to show the rounded time
		roundedTimeLabel = new JLabel("");
		roundedTimeLabel.setFont(roundedTimeLabel.getFont().deriveFont(35.0f));
		roundedTimeLabel.setHorizontalAlignment(JLabel.CENTER);

		timeLayout = new GridLayout(2, 1);
		timePane.setLayout(timeLayout);

		timePane.add(timeLabel);
		timePane.add(roundedTimeLabel);

		mainLayout = new BorderLayout();
		this.setLayout(mainLayout);

		this.add(timePane, BorderLayout.CENTER);
		this.add(entryPane, BorderLayout.PAGE_END);
	}

	/**
	 * set the current time on the clock
	 * 
	 * @param time current time
	 */
	public void setTime(String time) {
		timeLabel.setText(time);
	}

	/**
	 * set the rounded time on the clock
	 * 
	 * @param time rounded time
	 */
	public void setRoundedTime(String time) {
		roundedTimeLabel.setText(time);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {

			int id = Integer.parseInt(idField.getText());
			if (id > 9999 && id < 100000) {
				idField.setText("");
				emc.sendId(id);
			} else
				textError();

		} catch (Exception exception) {
			textError();
		}

	}

	/**
	 * highlight the text area when there is an error
	 */
	public void textError() {
		idField.setText("");
		idField.setBorder(new LineBorder(Color.RED, 2));

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				idField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
			}
		}, 2000);
	}
}
