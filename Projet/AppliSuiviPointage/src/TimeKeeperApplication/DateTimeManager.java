package TimeKeeperApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeManager implements Runnable {

	private LocalDate current_Date;
	private LocalTime current_Time;
	private LocalTime rounded_Time;

	/**
	 * @param current_Date
	 * @param current_Time
	 * @param rounded_Time
	 */
	public DateTimeManager() {
		this.updateCurrent_Time();
		this.updateCurrent_Date();
		this.updateRounded_Time();
	}

	/**
	 * @param current_Time the current_Time to set
	 */
	public void updateCurrent_Time() {
		this.current_Time = LocalTime.now();
	}

	/**
	 * @return the current_Time
	 */
	public LocalTime getCurrent_Time() {
		this.updateCurrent_Time();
		return current_Time;
	}

	/**
	 * @brief Update current date
	 */
	public void updateCurrent_Date() {
		this.current_Date = LocalDate.now();
	}

	/**
	 * @return the current_Date
	 */
	public LocalDate getCurrent_Date() {
		this.updateCurrent_Date();
		return current_Date;
	}

	/**
	 * @return LocalTime rounded Time to closest 1/4 hour.
	 */
	public LocalTime roundedTime() {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

		LocalTime roundedTime = LocalTime.parse(currentTime.truncatedTo(ChronoUnit.HOURS)
				.plusMinutes(15 * (currentTime.getMinute() / 15)).format(formatTime));

		return roundedTime;
	}

	/**
	 * @param rounded_Time the rounded_Time to set
	 */
	public void updateRounded_Time() {
		this.rounded_Time = roundedTime();
	}

	public LocalTime getRounded_Time() {
		updateRounded_Time();
		return this.rounded_Time;

	}

	public void update_All() {
		this.updateCurrent_Time();
		this.updateCurrent_Date();
		this.updateRounded_Time();
	}

	@Override
	public void run() {
		while (true) {
			this.update_All();
			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
