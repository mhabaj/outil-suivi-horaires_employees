package TimeKeeperApplication;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeKeeper implements Serializable {

	private static final long serialVersionUID = 7883183033977595538L;
	private LocalDate current_Date_TimeKeeper;
	private LocalTime registered_rounded_time;
	private int id_Worker;

	/**
	 * @return the current_Date_TimeKeeper
	 */
	public LocalDate getCurrent_Date_TimeKeeper() {
		return current_Date_TimeKeeper;
	}

	/**
	 * @param current_Date_TimeKeeper the current Date to set
	 */
	public void setCurrent_Date_TimeKeeper(LocalDate current_Date_TimeKeeper) {
		this.current_Date_TimeKeeper = current_Date_TimeKeeper;
	}

	/**
	 * @return the registered_rounded_time
	 */
	public LocalTime getRegistered_rounded_time() {
		return registered_rounded_time;
	}

	/**
	 * @param registered_rounded_time the registered_rounded_time to set
	 */
	public void setRegistered_rounded_time(LocalTime registered_rounded_time) {
		this.registered_rounded_time = registered_rounded_time;
	}

	/**
	 * @brief Update current date
	 */
	public void updateCurrentDate() {
		this.setCurrent_Date_TimeKeeper(LocalDate.now());
	}

	/**
	 * @return the id_Worker
	 */
	public int getId_Worker() {
		return id_Worker;
	}

	/**
	 * @param id_Worker the id_Worker to set
	 */
	public void setId_Worker(int id_Worker) {
		this.id_Worker = id_Worker;
	}

	/**
	 * @return LocalTime rounded Time to closest 1/4 hour.
	 */
	public LocalTime roundedTime(LocalDateTime currentTime) {
		// LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("HH:mm:ss");

		LocalTime roundedTime = LocalTime.parse(currentTime.truncatedTo(ChronoUnit.HOURS)
				.plusMinutes(15 * (currentTime.getMinute() / 15)).format(formatTime));

		return roundedTime;
	}

	/**
	 * @param current_Date_TimeKeeper
	 * @param registered_rounded_time
	 * @param id_Worker
	 */
	public TimeKeeper(int id_Worker) {
		this.updateCurrentDate();
		setRegistered_rounded_time(roundedTime(LocalDateTime.now()));
		setId_Worker(id_Worker);
	}

	@Override
	public String toString() {
		return "TimeKeeper [current_Date_TimeKeeper=" + current_Date_TimeKeeper + ", registered_rounded_time="
				+ registered_rounded_time + ", id_Worker=" + id_Worker + "]";
	}

	
}