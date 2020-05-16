
package centralApplication;

import java.time.*;

public class TimeKeeper {
	private LocalDate current_Date_TimeKeeper;
	private LocalTime registered_rounded_time;

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

}
