package centralApplication;

import java.time.LocalTime;

public class Worker {

	/**
	 * @param id_Worker
	 * @param firstname_Worker
	 * @param lastname_Worker
	 * @param default_ArrivalTime_Worker
	 * @param default_DepartureTime_Worker
	 */

	private int id_Worker;
	private String firstname_Worker, lastname_Worker;
	private LocalTime default_ArrivalTime_Worker, default_DepartureTime_Worker;

	public Worker(int id_Worker, String firstname_Worker, String lastname_Worker, LocalTime default_ArrivalTime_Worker,
			LocalTime default_DepartureTime_Worker) {
		this.setId_Worker(id_Worker);
		this.setFirstname_Worker(firstname_Worker);
		this.setLastname_Worker(lastname_Worker);
		this.setDefault_ArrivalTime_Worker(default_ArrivalTime_Worker);
		this.setDefault_DepartureTime_Worker(default_DepartureTime_Worker);
	}

	public int getId_Worker() {
		return id_Worker;
	}

	public void setId_Worker(int id_Worker) {
		this.id_Worker = id_Worker;
	}

	public String getFirstname_Worker() {
		return firstname_Worker;
	}

	public void setFirstname_Worker(String firstname_Worker) {
		this.firstname_Worker = firstname_Worker;
	}

	public String getLastname_Worker() {
		return lastname_Worker;
	}

	public void setLastname_Worker(String lastname_Worker) {
		this.lastname_Worker = lastname_Worker;
	}

	public LocalTime getDefault_ArrivalTime_Worker() {
		return default_ArrivalTime_Worker;
	}

	public void setDefault_ArrivalTime_Worker(LocalTime default_ArrivalTime_Worker) {
		this.default_ArrivalTime_Worker = default_ArrivalTime_Worker;
	}

	public LocalTime getDefault_DepartureTime_Worker() {
		return default_DepartureTime_Worker;
	}

	public void setDefault_DepartureTime_Worker(LocalTime default_DepartureTime_Worker) {
		this.default_DepartureTime_Worker = default_DepartureTime_Worker;
	}
}
