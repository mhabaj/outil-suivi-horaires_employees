package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * Represents the Department data Structure inside a Company
 * 
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 *
 *
 */

public class DepartmentModel implements Serializable {

	private static final long serialVersionUID = 1212195773920642415L;
	private String name_Department;
	private ArrayList<WorkerModel> Worker_List = new ArrayList<WorkerModel>(); // Worker list in each department
	private CompanyModel cp;
	private int id_Department;

	/**
	 * Constructor of class Department
	 * 
	 * @param id_Department
	 * @param name_Department
	 */
	public DepartmentModel(String name_Department, CompanyModel cp) {
		id_Department = cp.getId_Department_Counter();
		this.cp = cp;
		this.setName_Department(name_Department);
		cp.incrementDepartmentNumber();
	}

	/**
	 * @return the current id_Department
	 */
	public int getId_Department() {
		return id_Department;
	}

	/**
	 * @param id_Department the id_Department to set
	 */
	public void setId_Department(int id_Department) {
		this.id_Department = id_Department;
	}

	/**
	 * 
	 * get Department activity by date
	 * 
	 * @param date
	 * @return HashMap<Worker, WorkingDay> Every worker activity in the department
	 *         per date
	 */
	public HashMap<WorkerModel, WorkingDayModel> getDepartmentActivityPerDate(String date) {

		HashMap<WorkerModel, WorkingDayModel> listeWorkerWorkingDays = new HashMap<WorkerModel, WorkingDayModel>();

		for (WorkerModel worker : Worker_List) {

			if (worker.checkWorkingDayByDate(date) == true) {
				listeWorkerWorkingDays.put(worker, worker.getWorkingDayByDate(date));

			}
		}

		return listeWorkerWorkingDays;
	}

	/**
	 * @return the worker_List, null if empty
	 */
	public ArrayList<WorkerModel> getWorker_List() {
		if (Worker_List.isEmpty()) {
			return null;
		} else {
			return Worker_List;
		}
	}

	/**
	 * @param worker_List the worker_List to set
	 */
	public void setWorker_List(ArrayList<WorkerModel> worker_List) {
		Worker_List = worker_List;
	}

	/**
	 * @return the name_Department
	 */
	public String getName_Department() {
		return name_Department;
	}

	/**
	 * @param name_Department the name_Department to set
	 */
	public void setName_Department(String name_Department) {
		this.name_Department = name_Department;
	}

	/**
	 * add worker into the actual department
	 * 
	 * @param department_To_Add
	 */
	public void add_Worker(WorkerModel worker_To_Add) {

		this.Worker_List.add(worker_To_Add);
	}

	/**
	 * 
	 * Adds a new worker with custom schedule for every day
	 * 
	 * @param firstname_Worker
	 * @param lastname_Worker
	 * @param default_ArrivalTime_Worker
	 * @param default_DepartureTime_Worker
	 */
	public void add_New_Worker_CustomTime(String firstname_Worker, String lastname_Worker,
			String[] default_ArrivalTime_Worker, String[] default_DepartureTime_Worker) {
		WorkerModel worker_To_Add = new WorkerModel(cp.getId_Worker_Counter(), firstname_Worker, lastname_Worker,
				default_ArrivalTime_Worker, default_DepartureTime_Worker);
		this.Worker_List.add(worker_To_Add);
		cp.incrementWorkersNumber();
	}

	/**
	 * 
	 * Adds a new worker with default Application schedule (7:00/17:00)
	 * 
	 * @param firstname_Worker
	 * @param lastname_Worker
	 */
	public void add_New_Worker_DefaultTime(String firstname_Worker, String lastname_Worker) {
		WorkerModel worker_To_Add = new WorkerModel(cp.getId_Worker_Counter(), firstname_Worker, lastname_Worker);
		this.Worker_List.add(worker_To_Add);
		cp.incrementWorkersNumber();
	}

	/**
	 * Checks if Worker Id is valid
	 * 
	 * @param searched_Worker_Id
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidId(int searched_Worker_Id) {
		for (WorkerModel worker : Worker_List) {
			int current_Worker_Id = worker.getId_Worker();
			if (current_Worker_Id == searched_Worker_Id)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * search for worker by ID
	 * 
	 * @param searched_Worker_Id
	 * @return worker with searched ID
	 * @throws Exception Worker Not Found
	 */
	public WorkerModel getWorkerById(int searched_Worker_Id) throws Exception {
		for (WorkerModel worker : Worker_List) {
			int current_Worker_Id = worker.getId_Worker();
			if (current_Worker_Id == searched_Worker_Id)
				return worker;
		}
		throw new Exception("Error Search: Worker Not Found");

	}

	/**
	 * 
	 * Checks if a worker with a specific name is working in the department
	 * 
	 * @param searched_Worker_Firstname
	 * @param searched_Worker_Lastname
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidName(String searched_Worker_Firstname, String searched_Worker_Lastname) {
		for (WorkerModel worker : Worker_List) {
			String current_Worker_Firstname = worker.getFirstname_Worker();
			String current_Worker_Lastname = worker.getLastname_Worker();
			if ((current_Worker_Firstname.equals(searched_Worker_Firstname))
					&& (current_Worker_Lastname.equals(searched_Worker_Lastname)))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * Get a worker with a specific name in the department (separrated)
	 * 
	 * @param searched_Worker_Firstname
	 * @param searched_Worker_Lastname
	 * @return worker if found, else null
	 */
	public WorkerModel getWorkerByName(String searched_Worker_Firstname, String searched_Worker_Lastname) {
		for (WorkerModel worker : Worker_List) {
			String current_Worker_Firstname = worker.getFirstname_Worker();
			String current_Worker_Lastname = worker.getLastname_Worker();
			if ((current_Worker_Firstname.equals(searched_Worker_Firstname))
					&& (current_Worker_Lastname.equals(searched_Worker_Lastname)))
				return worker;
		}
		return null;
	}

	/**
	 * Get a worker with a specific name in the department (joint)
	 * 
	 * 
	 * @param searched_Worker_FullName
	 * @return
	 * @throws Exception
	 */
	public WorkerModel getWorkerByFullName(String searched_Worker_FullName) throws Exception {
		String[] name = searched_Worker_FullName.split(" ");

		return getWorkerByName(name[1], name[0]);
	}

	/**
	 * Delete worker from department
	 * 
	 * @param workerToDelete
	 * @return 1 if success
	 * @throws Exception Worker Not Found
	 */
	public int deleteWorker(WorkerModel workerToDelete) throws Exception {

		for (WorkerModel worker : Worker_List) {
			if (worker.getId_Worker() == workerToDelete.getId_Worker()) {
				this.Worker_List.remove(worker);
				return 1;
			}
		}
		throw new Exception("Error Remove: Worker Not Found");

	}
}
