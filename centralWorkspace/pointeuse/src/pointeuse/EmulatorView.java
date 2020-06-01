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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class EmulatorView extends JFrame {

	private EmulatorController emc;
	
	private JTabbedPane tabPane;
	
	private TimeClockView timeClock;

	public EmulatorView(EmulatorController emc) {

		this.emc = emc;

		tabPane = new JTabbedPane();
		
		timeClock = new TimeClockView(emc);
		
		tabPane.addTab("Time clock", timeClock);
		
		ParametersView param = new ParametersView(emc);
		
		tabPane.addTab("Parameters", param);
		
		this.setTitle("pointeuse");
		this.setContentPane(tabPane);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 300);
		this.setVisible(true);
	}
	
	public void setTime(String time) {
		timeClock.setTime(time);
	}

	public void setRoundedTime(String time) {
		timeClock.setRoundedTime(time);
	}

}
