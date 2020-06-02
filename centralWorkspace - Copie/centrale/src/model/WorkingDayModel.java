package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A class to represent a full Working day of a worker
 * 
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 *
 */
public class WorkingDayModel implements Serializable {

	private static final long serialVersionUID = 3677369569047290839L;
	private String todaysDate;
	private String arrivalTime; // Worker arrival time at company
	private String departureTime; // Worker departure time from company
	private String weekDay; // Day of week : Saturday...
	private int timeOverFlow; // determine how many time he was late / spent more in the company
	private boolean calculedTime = false;

	/**
	 * Constuctor of WorkingDay
	 * 
	 * @param todaysDate    String format : dd/MM/yyyy
	 * @param arrivalTime   arrival time in HH:mm
	 * @param departureTime departure time in HH:mm
	 */
	public WorkingDayModel(String todaysDate, String arrivalTime, String departureTime) {
		this.setTodaysDate(todaysDate);
		this.setArrivalTime(arrivalTime);
		this.setDepartureTime(departureTime);
		this.setWeekDay();
	}

	/**
	 * @return boolean if today overtime calculated
	 */
	public boolean isTimeCalculated() {
		return calculedTime;
	}

	/**
	 * Sets today time overflow
	 * 
	 * @param timeOverflow
	 */
	public void setTimeOverflow(int timeOverflow) {
		this.timeOverFlow = timeOverflow;
		calculedTime = true;
	}

	/**
	 * @return timeOverFlow
	 */
	public int getTimeOverflow() {
		return timeOverFlow;
	}

	/**
	 * @return todaysDate dd/MM/yyyy
	 */
	public String getTodaysDate() {
		return todaysDate;
	}

	/**
	 * @param todaysDate the todaysDate to set
	 */
	public void setTodaysDate(String todaysDate) {
		this.todaysDate = todaysDate;
	}

	/**
	 * @return arrivalTime
	 */
	public String getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the departureTime
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * @return the weekDay
	 */
	public String getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay() {

		Date tmpDate;
		try {

			tmpDate = new SimpleDateFormat("dd/MM/yyyy").parse(todaysDate); // parse date String into Date object

			DateFormat format2 = new SimpleDateFormat("EEEE", Locale.US); // Get the day name out of the Date (Local.US
																			// for english days names)
			weekDay = format2.format(tmpDate); // convert Date name to String

		} catch (ParseException e) {
			System.out.println("Couldn't Parse input Date  " + System.lineSeparator()); // invalide date input -> end
			// current Task.
		}

	}

}