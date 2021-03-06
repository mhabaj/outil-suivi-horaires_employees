package pointeuse;

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

public class TimeClockView extends JPanel implements ActionListener {

	private EmulatorController emc;
	private JPanel timePane = new JPanel();
	private JPanel entryPane = new JPanel();
	private BorderLayout mainLayout;
	private GridLayout timeLayout;
	private BoxLayout entryLayout;
	private JLabel timeLabel;
	private JLabel roundedTimeLabel;
	private JTextField idField;
	private JButton sendIdButton;
	
	public TimeClockView(EmulatorController emc) {
		
		this.emc = emc;
		
		idField = new JTextField();
		idField.setColumns(5);
		idField.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		sendIdButton = new JButton("IN/OUT");
		sendIdButton.addActionListener(this);

		entryLayout = new BoxLayout(entryPane, BoxLayout.X_AXIS);
		entryPane.setLayout(entryLayout);

		entryPane.add(idField);
		entryPane.add(sendIdButton);

		timeLabel = new JLabel("");
		timeLabel.setFont(timeLabel.getFont().deriveFont(50.0f));
		timeLabel.setHorizontalAlignment(JLabel.CENTER);

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
	
	public void setTime(String time) {
		timeLabel.setText(time);
	}

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
