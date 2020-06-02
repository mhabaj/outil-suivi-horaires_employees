package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * class that represent the worker
 * 
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 */
public class WorkerModel implements Serializable {

	private final int MONDAY = 0;
	private final int TUESDAY = 1;
	private final int WEDNESDAY = 2;
	private final int THURSDAY = 3;
	private final int FRIDAY = 4;

	private static final long serialVersionUID = 7337982580589824925L;
	private int id_Worker;//a worker ID is minimum 10000 and max 99999
	private long workingTimeOverflow_Worker;
	private String firstname_Worker, lastname_Worker;
	private String[] default_ArrivalTime_Worker = { "7:00", "7:00", "7:00", "7:00", "7:00" };
	private String[] default_DepartureTime_Worker = { "17:00", "17:00", "17:00", "17:00", "17:00" };
	private ArrayList<WorkingDayModel> workingDaysArray;

	/**
	 * worker constructor with the schedule
	 * 
	 * @param id_Worker                    id of the worker
	 * @param firstname_Worker             first name
	 * @param lastname_Worker              last name
	 * @param default_ArrivalTime_Worker   array of default arrival time
	 * @param default_DepartureTime_Worker array of default departure time
	 */
	public WorkerModel(int id_Worker, String firstname_Worker, String lastname_Worker, String[] default_ArrivalTime_Worker,
			String[] default_DepartureTime_Worker) {
		this.setId_Worker(id_Worker);
		this.setFirstname_Worker(firstname_Worker);
		this.setLastname_Worker(lastname_Worker);
		this.setDefault_ArrivalTime_Worker(default_ArrivalTime_Worker);
		this.setDefault_DepartureTime_Worker(default_DepartureTime_Worker);
		this.workingDaysArray = new ArrayList<WorkingDayModel>();
	}

	/**
	 * @param id_Worker        id of the worker
	 * @param firstname_Worker first name
	 * @param lastname_Worker  last name
	 */
	public WorkerModel(int id_Worker, String firstname_Worker, String lastname_Worker) {
		this.setId_Worker(id_Worker);
		this.setFirstname_Worker(firstname_Worker);
		this.setLastname_Worker(lastname_Worker);
		this.workingDaysArray = new ArrayList<WorkingDayModel>();
	}

	/**
	 * @return the id_Worker
	 */
	public int getId_Worker() {
		return id_Worker;
	}

	/**
	 * add the time overflow of the day to the time overflow of the worker
	 * 
	 * @param workingDay working day
	 */
	public void addTimeOverflow(WorkingDayModel workingDay) {
		// if time overflow has already been calculated
		if (workingDay.isTimeCalculated()) {
			// remove last time overflow from the worker total time overflow
			workingTimeOverflow_Worker -= workingDay.getTimeOverflow();
			// recalculate the time overflow of the working day
			calculateTimeOverflow(workingDay);
			// adding the new time overflow to the worker total time overflow
			workingTimeOverflow_Worker += workingDay.getTimeOverflow();
		}
		// if time overflow hasn't been calculated
		else {
			// if the day is full
			if (workingDay.getArrivalTime() != null && workingDay.getDepartureTime() != null) {
				// calculate the time overflow of the working day
				calculateTimeOverflow(workingDay);
				// adding the time overflow to the worker total time overflow
				workingTimeOverflow_Worker += workingDay.getTimeOverflow();
			}
		}
	}

	/**
	 * calculate the time overflow of the day
	 * 
	 * @param workingDay working day
	 */
	public void calculateTimeOverflow(WorkingDayModel workingDay) {
		// create a format
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		try {
			// get the arrival and departure time of the day
			Date arrivalTime = dateFormat.parse(workingDay.getArrivalTime());
			Date departureTime = dateFormat.parse(workingDay.getDepartureTime());

			// get the default arrival and departure time of the worker for this day
			Date defaultArrivalTimeTMP = null;
			Date defaultDepartureTimeTMP = null;
			if (workingDay.getWeekDay().equals(weekDay_Code_ToString(MONDAY))) {
				defaultArrivalTimeTMP = dateFormat.parse(default_ArrivalTime_Worker[MONDAY]);
				defaultDepartureTimeTMP = dateFormat.parse(default_DepartureTime_Worker[MONDAY]);
			} else if (workingDay.getWeekDay().equals(weekDay_Code_ToString(TUESDAY))) {
				defaultArrivalTimeTMP = dateFormat.parse(default_ArrivalTime_Worker[TUESDAY]);
				defaultDepartureTimeTMP = dateFormat.parse(default_DepartureTime_Worker[TUESDAY]);
			} else if (workingDay.getWeekDay().equals(weekDay_Code_ToString(WEDNESDAY))) {
				defaultArrivalTimeTMP = dateFormat.parse(default_ArrivalTime_Worker[WEDNESDAY]);
				defaultDepartureTimeTMP = dateFormat.parse(default_DepartureTime_Worker[WEDNESDAY]);
			} else if (workingDay.getWeekDay().equals(weekDay_Code_ToString(THURSDAY))) {
				defaultArrivalTimeTMP = dateFormat.parse(default_ArrivalTime_Worker[THURSDAY]);
				defaultDepartureTimeTMP = dateFormat.parse(default_DepartureTime_Worker[THURSDAY]);
			} else if (workingDay.getWeekDay().equals(weekDay_Code_ToString(FRIDAY))) {
				defaultArrivalTimeTMP = dateFormat.parse(default_ArrivalTime_Worker[FRIDAY]);
				defaultDepartureTimeTMP = dateFormat.parse(default_DepartureTime_Worker[FRIDAY]);
			}

			// calculate the default working time in milisecond
			long defaultWorkingTimeSec = defaultDepartureTimeTMP.getTime() - defaultArrivalTimeTMP.getTime();

			// calculate the working day working time in milisecond
			long workingTimeSec = departureTime.getTime() - arrivalTime.getTime();

			// calculate the difference between the two
			long timeOverflowSec = workingTimeSec - defaultWorkingTimeSec;

			// convert the difference in minutes
			int timeOverflow = (int) (timeOverflowSec / 60000);

			// set the time overflow of the day
			workingDay.setTimeOverflow(timeOverflow);

		} catch (ParseException e) {
			System.out.println("Couldn't Parse input Date  " + System.lineSeparator()); // invalide date input -> end
			// current Task.
		} catch (Exception e) {
			System.out.println("invalide entry as given date is a Saturday or a Sunday. " + System.lineSeparator()); // Sunday
																														// or
																														// saturday:
																														// company
																														// closed
		}
	}

	/**
	 * @param id_Worker the id_Worker to set
	 */
	public void setId_Worker(int id_Worker) {
		this.id_Worker = id_Worker;
	}

	/**
	 * @return the firstname_Worker
	 */
	public String getFirstname_Worker() {
		return firstname_Worker;
	}

	/**
	 * @param firstname_Worker the firstname_Worker to set
	 */
	public void setFirstname_Worker(String firstname_Worker) {
		this.firstname_Worker = firstname_Worker;
	}

	/**
	 * add a working day to the worker
	 * 
	 * @param todaysDate  date
	 * @param arrivalTime arrival worker time
	 */
	public void addWorkingDay(String todaysDate, String arrivalTime) {
		// create the working day
		WorkingDayModel wdTemp = new WorkingDayModel(todaysDate, arrivalTime, null);

		// add it to the worker
		workingDaysArray.add(wdTemp);

	}

	/**
	 * add a working day to the worker
	 * 
	 * @param todaysDate    date
	 * @param arrivalTime   arrival worker time
	 * @param departureTime departure worker time
	 */
	public void addWorkingDay(String todaysDate, String arrivalTime, String departureTime) {
		// create the working day
		WorkingDayModel wdTemp = new WorkingDayModel(todaysDate, arrivalTime, departureTime);
		// add it to the worker
		workingDaysArray.add(wdTemp);

	}

	/**
	 * get the last worked day of the worker
	 * 
	 * @return last working day
	 * @throws Exception no working days
	 */
	public WorkingDayModel getLastWorkingDay() throws Exception {

		// if there are working days
		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {
			// return the last one
			return workingDaysArray.get(workingDaysArray.size() - 1);
		}
		throw new Exception("Error getLastWorkingDay: no working days found ");

	}

	/**
	 * return the working days
	 * 
	 * @return working days array
	 */
	public ArrayList<WorkingDayModel> getWorkingDays() {

		// if there are working days
		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {
			// return the array
			return workingDaysArray;
		} else
			return null;
	}

	/**
	 * @return number of worked days
	 */
	public int getNumberWorkedDays() {
		return workingDaysArray.size();
	}

	/**
	 * @return the lastname_Worker
	 */
	public String getLastname_Worker() {
		return lastname_Worker;
	}

	/**
	 * @param lastname_Worker the lastname_Worker to set
	 */
	public void setLastname_Worker(String lastname_Worker) {
		this.lastname_Worker = lastname_Worker;
	}

	/**
	 * @return the default_ArrivalTime_Worker
	 */
	public String[] getDefault_ArrivalTime_Worker() {
		return default_ArrivalTime_Worker;
	}

	/**
	 * @param default_ArrivalTime_Worker the default_ArrivalTime_Worker to set
	 */
	public void setDefault_ArrivalTime_Worker(String[] default_ArrivalTime_Worker) {
		this.default_ArrivalTime_Worker = default_ArrivalTime_Worker;
	}

	/**
	 * change default arrival time for a day
	 * 
	 * @param day
	 * @param NewArrivalTime
	 * @throws Exception error in time
	 */
	public void setDefault_ArrivalTime_Worker(String day, String NewArrivalTime) throws Exception {
		int dayCode = weekDay_Name_ToCode(day);
		this.default_ArrivalTime_Worker[dayCode] = NewArrivalTime;
	}

	/**
	 * change default departure time for a day
	 * 
	 * @param day
	 * @param NewArrivalTime
	 * @throws Exception error in time
	 */
	public void setDefault_DepartureTime_Worker(String day, String NewArrivalTime) throws Exception {
		int dayCode = weekDay_Name_ToCode(day);
		this.default_DepartureTime_Worker[dayCode] = NewArrivalTime;
	}

	/**
	 * @return the default_DepartureTime_Worker
	 */
	public String[] getDefault_DepartureTime_Worker() {
		return default_DepartureTime_Worker;
	}

	/**
	 * @param default_DepartureTime_Worker the default_DepartureTime_Worker to set
	 */
	public void setDefault_DepartureTime_Worker(String[] default_DepartureTime_Worker) {

		this.default_DepartureTime_Worker = default_DepartureTime_Worker;

	}

	/**
	 * print all working days
	 */
	public void showEveryWorkingDay() {
		for (WorkingDayModel workingDay : workingDaysArray) {
			System.out.println(workingDay.toString() + System.lineSeparator());
		}
	}

	/**
	 * get a working day by his date
	 * 
	 * @param searched_Date
	 * @return working day
	 */
	public WorkingDayModel getWorkingDayByDate(String searched_Date) {
		for (WorkingDayModel workingDay : workingDaysArray) {
			String current_WorkingDay_Date = workingDay.getTodaysDate();
			if (current_WorkingDay_Date.equals(searched_Date))
				return workingDay;
		}
		return null;

	}

	/**
	 * search a working day by his date
	 * 
	 * @param searched_Date
	 * @return if day exist
	 */
	public boolean checkWorkingDayByDate(String searched_Date) {
		for (WorkingDayModel workingDay : workingDaysArray) {
			String current_WorkingDay_Date = workingDay.getTodaysDate();
			if (current_WorkingDay_Date.equals(searched_Date))
				return true;
		}
		return false;
	}

	/**
	 * convert int day code to string day name
	 * 
	 * @param dayCode
	 * @return day string
	 * @throws Exception invalid code
	 */
	public String weekDay_Code_ToString(int dayCode) throws Exception {
		switch (dayCode) {
		case MONDAY:
			return "Monday";
		case TUESDAY:
			return "Tuesday";
		case WEDNESDAY:
			return "Wednesday";
		case THURSDAY:
			return "Thursday";
		case FRIDAY:
			return "Friday";
		default:
			throw new Exception("Error WeekDayToString: invalid DayCode ");
		}
	}

	/**
	 * convert string day name to int day code
	 * 
	 * @param dayName
	 * @return day code
	 * @throws Exception invalid day name
	 */
	public int weekDay_Name_ToCode(String dayName) throws Exception {
		switch (dayName) {
		case "Monday":
			return MONDAY;
		case "Tuesday":
			return TUESDAY;
		case "Wednesday":
			return WEDNESDAY;
		case "Thursday":
			return THURSDAY;
		case "Friday":
			return FRIDAY;
		default:
			throw new Exception("Error WeekDayToString: invalid dayName ");
		}
	}

	/**
	 * @param DayCode day code of the day
	 * @return default departure time
	 */
	public String getDefault_DepartureTime_Worker_ByWeekDayCode(int DayCode) {

		return default_DepartureTime_Worker[DayCode];

	}

	/**
	 * @param DayName name of the day
	 * @return default departure time
	 * @throws Exception invalid name of the day
	 */
	public String getDefault_DepartureTime_Worker_ByWeekDayName(String DayName) throws Exception {

		return default_DepartureTime_Worker[weekDay_Name_ToCode(DayName)];

	}

	/**
	 * @param DayCode day code of the day
	 * @return default arrival time
	 */
	public String getDefault_ArrivalTime_Worker_ByWeekDayCode(int DayCode) {

		return default_ArrivalTime_Worker[DayCode];

	}

	/**
	 * @param DayName name of the day
	 * @return default arrival time
	 * @throws Exception invalid name of the day
	 */
	public String getDefault_ArrivalTime_Worker_ByWeekDayName(String DayName) throws Exception {

		return default_ArrivalTime_Worker[weekDay_Name_ToCode(DayName)];

	}

	/**
	 * @return the workingTimeOverflow_Worker
	 */
	public long getWorkingTimeOverflow_Worker() {
		return workingTimeOverflow_Worker;
	}

}
