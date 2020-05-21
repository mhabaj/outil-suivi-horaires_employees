package central;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7337982580589824925L;
	private int id_Worker;
	private String firstname_Worker, lastname_Worker;
	private String default_ArrivalTime_Worker, default_DepartureTime_Worker;
	private ArrayList<WorkingDay> workingDaysArray;

	/**
	 * @param id_Worker
	 * @param firstname_Worker
	 * @param lastname_Worker
	 * @param default_ArrivalTime_Worker
	 * @param default_DepartureTime_Worker
	 */

	public Worker(int id_Worker, String firstname_Worker, String lastname_Worker, String default_ArrivalTime_Worker,
			String default_DepartureTime_Worker) {
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

	public WorkingDay getLastWorkingDay() throws Exception {

		if (workingDaysArray != null && !workingDaysArray.isEmpty()) {

			return workingDaysArray.get(workingDaysArray.size() - 1);
		}
		throw new Exception("Error getLastWorkingDay: no working days found ");

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
	public String getDefault_ArrivalTime_Worker() {
		return default_ArrivalTime_Worker;
	}

	/**
	 * @param default_ArrivalTime_Worker the default_ArrivalTime_Worker to set
	 */
	public void setDefault_ArrivalTime_Worker(String default_ArrivalTime_Worker) {
		this.default_ArrivalTime_Worker = default_ArrivalTime_Worker;
	}

	/**
	 * @return the default_DepartureTime_Worker
	 */
	public String getDefault_DepartureTime_Worker() {
		return default_DepartureTime_Worker;
	}

	/**
	 * @param default_DepartureTime_Worker the default_DepartureTime_Worker to set
	 */
	public void setDefault_DepartureTime_Worker(String default_DepartureTime_Worker) {
		this.default_DepartureTime_Worker = default_DepartureTime_Worker;
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

	@Override
	public String toString() {
		return "Worker [id_Worker=" + id_Worker + ", firstname_Worker=" + firstname_Worker + ", lastname_Worker="
				+ lastname_Worker + ", default_ArrivalTime_Worker=" + default_ArrivalTime_Worker
				+ ", default_DepartureTime_Worker=" + default_DepartureTime_Worker + "]";
	}

}
