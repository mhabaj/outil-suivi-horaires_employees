package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import view.MainView;

/**
 * 
 * Runnable clock with rounding time and dates functionalities
 * 
 * @author Alhabaj Mahmod /Belda Tom / Dakroub MohamadAli
 *
 */
public class TimeController implements Runnable {

	private LocalDate current_Date;
	private LocalTime current_Time;
	private LocalTime rounded_Time;

	private MainView vue;

	/**
	 * @param current_Date
	 * @param current_Time
	 * @param rounded_Time
	 */
	public TimeController(MainView vue) {
		this.vue = vue;
	}

	/**
	 * Updates current Time displayed
	 * 
	 */
	@SuppressWarnings("static-access")
	public void updateCurrent_Time() {
		this.current_Time = LocalTime.now().parse(LocalTime.now().truncatedTo(ChronoUnit.HOURS)
				.plusMinutes(LocalTime.now().getMinute()).format(DateTimeFormatter.ofPattern("HH:mm")));
		;
	}

	/**
	 * @return the current_Time
	 */
	public String getCurrent_Time() {
		this.updateCurrent_Time();
		return current_Time.toString();
	}

	/**
	 * Update current date
	 */
	public void updateCurrent_Date() {
		this.current_Date = LocalDate.now();
	}

	/**
	 * @return the current_Date
	 */
	public String getCurrent_Date() {
		this.updateCurrent_Date();
		return current_Date.toString();
	}

	/**
	 * @return LocalTime rounded Time to closest 1/4 hour.
	 */
	public LocalTime roundedTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm");

		LocalTime roundedTime = LocalTime.parse(currentTime.truncatedTo(ChronoUnit.HOURS)
				.plusMinutes(15 * (currentTime.getMinute() / 15)).format(formatTime));

		return roundedTime;
	}

	/**
	 * Update current rounded time
	 */
	public void updateRounded_Time() {
		this.rounded_Time = roundedTime();
	}

	/**
	 * @return current rounded time
	 */
	public String getRounded_Time() {
		updateRounded_Time();
		return this.rounded_Time.toString();

	}

	/**
	 * Update all clock components
	 */
	public void update_All() {
		this.updateCurrent_Time();
		this.updateCurrent_Date();
		this.updateRounded_Time();

		vue.setTime(getCurrent_Time());
		vue.setRoundedTime(getRounded_Time());
	}

	/**
	 * @return return day name of the week
	 */
	public String getTodayWeekDay() {
		Date tmpDate;
		String todayweekDay = null;
		try {

			tmpDate = new SimpleDateFormat("yy-MM-dd").parse(getCurrent_Date());

			DateFormat format2 = new SimpleDateFormat("EEEE", Locale.US);
			todayweekDay = format2.format(tmpDate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return todayweekDay;
	}

	@Override
	public void run() {
		while (true) {
			this.update_All();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
