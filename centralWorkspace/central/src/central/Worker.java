package central;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Worker implements Serializable {

	/**
	 * 
	 */
	private final int MONDAY = 0;
	private final int TUESDAY = 1;
	private final int WEDNESDAY = 2;
	private final int THURSDAY = 3;
	private final int FRIDAY = 4;

	private static final long serialVersionUID = 7337982580589824925L;
	private int id_Worker;
	private static int workingTimeOverflow_Worker;
	private String firstname_Worker, lastname_Worker;
	private String[] default_ArrivalTime_Worker;
	private String[] default_DepartureTime_Worker;
	private ArrayList<WorkingDay> workingDaysArray;

	/**
	 * @param id_Worker
	 * @param firstname_Worker
	 * @param lastname_Worker
	 * @param default_ArrivalTime_Worker
	 * @param default_DepartureTime_Worker
	 */

	public Worker(int id_Worker, String firstname_Worker, String lastname_Worker, String[] default_ArrivalTime_Worker,
			String[] default_DepartureTime_Worker) {
		this.setId_Worker(id_Worker);
		this.setFirstname_Worker(firstname_Worker);
		this.setLastname_Worker(lastname_Worker);
		this.setDefault_ArrivalTime_Worker(default_ArrivalTime_Worker);
		this.setDefault_DepartureTime_Worker(default_DepartureTime_Worker);
		this.workingDaysArray = new ArrayList<WorkingDay>();

	}

	public Worker(int id_Worker, String firstname_Worker, String lastname_Worker) {
		this.setId_Worker(id_Worker);
		this.setFirstname_Worker(firstname_Worker);
		this.setLastname_Worker(lastname_Worker);
		this.setDefault_ArrivalTime_Worker(default_ArrivalTime_Worker);
		this.setDefault_DepartureTime_Worker(default_DepartureTime_Worker);
		this.workingDaysArray = new ArrayList<WorkingDay>();

	}

	/**
	 * @return the id_Worker
	 */
	public int getId_Worker() {
		return id_Worker;
	}

	public void addTimeOverflowArrival(String arrivalTime, WorkingDay todayWorkingDay) {

		try {

			if (todayWorkingDay.getWeekDay().equals(weekDay_Code_ToString(MONDAY))) {

				DateFormat df = new SimpleDateFormat("hh:mm:ss");
				Date arrivalTimeTMP = df.parse(arrivalTime);
				Date defaultArrivalTimeTMP = df.parse(default_ArrivalTime_Worker[MONDAY]);

				long diffTmp = defaultArrivalTimeTMP.getTime() - arrivalTimeTMP.getTime();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void addWorkingDay(String todaysDate, String arrivalTime) {
		WorkingDay wdTemp = new WorkingDay(todaysDate);
		wdTemp.setArrivalTime(arrivalTime);
		workingDaysArray.add(wdTemp);
	}

	public void addWorkingDay(String todaysDate, String arrivalTime, String departureTime) {
		WorkingDay wdTemp = new WorkingDay(todaysDate);
		wdTemp.setArrivalTime(arrivalTime);
		wdTemp.setDepartureTime(departureTime);
		workingDaysArray.add(wdTemp);
	}

	public WorkingDay getLastWorkingDay() throws Exception {

		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {

			return workingDaysArray.get(workingDaysArray.size() - 1);
		}
		throw new Exception("Error getLastWorkingDay: no working days found ");

	}

	public ArrayList<WorkingDay> getLastWorkingDays() throws Exception {

		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {

			ArrayList<WorkingDay> workingDays = new ArrayList<>();

			workingDays.add(workingDaysArray.get(workingDaysArray.size() - 1));

			if (workingDaysArray.size() > 1)
				workingDays.add(workingDaysArray.get(workingDaysArray.size() - 2));
			if (workingDaysArray.size() > 2)
				workingDays.add(workingDaysArray.get(workingDaysArray.size() - 3));
			if (workingDaysArray.size() > 3)
				workingDays.add(workingDaysArray.get(workingDaysArray.size() - 4));
			if (workingDaysArray.size() > 4)
				workingDays.add(workingDaysArray.get(workingDaysArray.size() - 5));

			return workingDays;
		}
		throw new Exception("Error getLastWorkingDay: no working days found ");
	}

	public ArrayList<WorkingDay> getWorkingDays() throws Exception {

		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {
			return workingDaysArray;
		}
		throw new Exception("Error getLastWorkingDay: no working days found ");
	}

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

	public void setDefault_ArrivalTime_Worker(String day, String NewArrivalTime) throws Exception {
        int dayCode = weekDay_Name_ToCode(day);
        this.default_ArrivalTime_Worker[dayCode] = NewArrivalTime;
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
    
    public void setDefault_DepartureTime_Worker(String day, String NewArrivalTime) throws Exception {
        int dayCode = weekDay_Name_ToCode(day);
        this.default_DepartureTime_Worker[dayCode] = NewArrivalTime;
    }

	public void showEveryWorkingDay() {
		for (WorkingDay workingDay : workingDaysArray) {
			System.out.println(workingDay.toString());
		}
	}

	public WorkingDay getWorkingDayByDate(String searched_Date) throws Exception {
		for (WorkingDay workingDay : workingDaysArray) {
			String current_WorkingDay_Date = workingDay.getTodaysDate();
			if (current_WorkingDay_Date.equals(searched_Date))
				return workingDay;
		}
		throw new Exception("Error Search: WorkingDay Not Found");

	}

	public boolean checkWorkingDayByDate(String searched_Date) throws Exception {
		for (WorkingDay workingDay : workingDaysArray) {
			String current_WorkingDay_Date = workingDay.getTodaysDate();
			if (current_WorkingDay_Date.equals(searched_Date))
				return true;
		}
		return false;
	}

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

	public String getDefault_DepartureTime_Worker_ByWeekDayCode(int DayCode) {

		return default_DepartureTime_Worker[DayCode];

	}

	public String getDefault_DepartureTime_Worker_ByWeekDayName(String DayName) throws Exception {

		return default_DepartureTime_Worker[weekDay_Name_ToCode(DayName)];

	}

	public String getDefault_ArrivalTime_Worker_ByWeekDayCode(int DayCode) {

		return default_ArrivalTime_Worker[DayCode];

	}

	public String getDefault_ArrivalTime_Worker_ByWeekDayName(String DayName) throws Exception {

		return default_ArrivalTime_Worker[weekDay_Name_ToCode(DayName)];

	}

	@Override
	public String toString() {
		return "Worker [id_Worker=" + id_Worker + ", firstname_Worker=" + firstname_Worker + ", lastname_Worker="
				+ lastname_Worker + ", default_ArrivalTime_Worker=" + default_ArrivalTime_Worker
				+ ", default_DepartureTime_Worker=" + default_DepartureTime_Worker + "]";
	}

}
