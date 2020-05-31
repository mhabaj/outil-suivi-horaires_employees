package central;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkingDay implements Serializable {

	private static final long serialVersionUID = 3677369569047290839L;
	private String todaysDate;
	private String arrivalTime;
	private String departureTime;
	private String weekDay;

	/**
	 * @param todaysDate
	 * @param arrivalTime
	 * @param departureTime
	 */
	public WorkingDay(String todaysDate, String arrivalTime, String departureTime) {
		this.setTodaysDate(todaysDate);
		this.setArrivalTime(arrivalTime);
		this.setDepartureTime(departureTime);
		this.setWeekDay();
	}

	public WorkingDay(String todaysDate, String weekDay, String arrivalTime, String departureTime) {
		this.setTodaysDate(todaysDate);
		this.setArrivalTime(arrivalTime);
		this.setDepartureTime(departureTime);
		this.setWeekDay();
	}

	public WorkingDay(String todaysDate) {
		this.setTodaysDate(todaysDate);
		this.setArrivalTime(arrivalTime);
		this.setDepartureTime(departureTime);
		this.setWeekDay();
	}

	@Override
	public String toString() {
		return "WorkingDay [todaysDate=" + todaysDate + ", arrivalTime=" + arrivalTime + ", departureTime="
				+ departureTime + "]";
	}

	/**
	 * @return the todaysDate
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
	 * @return the arrivalTime
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

			tmpDate = new SimpleDateFormat("dd/MM/yyyy").parse(todaysDate);

			DateFormat format2 = new SimpleDateFormat("EEEE", Locale.US);
			weekDay = format2.format(tmpDate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}