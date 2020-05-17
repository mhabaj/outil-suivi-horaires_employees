package centralApplication;

import java.util.ArrayList;

public class Department {
	private int id_Department;
	private String name_Department;
	private ArrayList<Worker> Worker_List = new ArrayList<Worker>();;

	/**
	 * @param id_Department
	 * @param name_Department
	 */
	public Department(int id_Department, String name_Department) {
		this.setId_Department(id_Department);
		this.setName_Department(name_Department);
	}

	/**
	 * @return the worker_List
	 */
	public ArrayList<Worker> getWorker_List() {
		return Worker_List;
	}

	/**
	 * @param worker_List the worker_List to set
	 */
	public void setWorker_List(ArrayList<Worker> worker_List) {
		Worker_List = worker_List;
	}

	/**
	 * @return the id_Department
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
	 * @param department_To_Add
	 */
	public void add_Worker(Worker worker_To_Add) {
		this.Worker_List.add(worker_To_Add);
	}

	/**
	 * @param searched_Worker_Id
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidId(int searched_Worker_Id) {
		for (Worker worker : Worker_List) {
			int current_worker_Id = worker.getId_Worker();
			if (current_worker_Id == searched_Worker_Id)
				return true;
		}
		return false;
	}

	
	/**
	 * @param searched_Worker_Firstname
	 * @param searched_Worker_Lastname
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidName(String searched_Worker_Firstname, String searched_Worker_Lastname) {
		for (Worker worker : Worker_List) {
			String current_worker_Firstname = worker.getFirstname_Worker();
			String current_worker_Lastname = worker.getLastname_Worker();
			if ((current_worker_Firstname == searched_Worker_Firstname)
					&& (current_worker_Lastname == searched_Worker_Lastname))
				return true;
		}
		return false;
	}

}