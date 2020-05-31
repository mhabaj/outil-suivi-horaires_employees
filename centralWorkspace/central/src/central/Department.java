package central;

import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1212195773920642415L;
	private String name_Department;
	private ArrayList<Worker> Worker_List = new ArrayList<Worker>();
	private Company cp;

	/**
	 * @param id_Department
	 * @param name_Department
	 */
	public Department(String name_Department, Company cp) {
		this.cp = cp;
        this.setName_Department(name_Department);
    }

	@Override
	public String toString() {
		return "Department [name_Department=" + name_Department + ", Worker_List="
				+ Worker_List + "]";
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
	
	public void add_New_Worker_CustomTime(String firstname_Worker, String lastname_Worker,
            String[] default_ArrivalTime_Worker, String[] default_DepartureTime_Worker) {
        Worker worker_To_Add = new Worker(cp.getId_Worker_Counter(), firstname_Worker, lastname_Worker,
                default_ArrivalTime_Worker, default_DepartureTime_Worker);
        this.Worker_List.add(worker_To_Add);
        cp.incrementWorkersNumber();
    }

    public void add_New_Worker_DefaultTime(String firstname_Worker, String lastname_Worker) {
        Worker worker_To_Add = new Worker(cp.getId_Worker_Counter(), firstname_Worker, lastname_Worker);
        this.Worker_List.add(worker_To_Add);
        cp.incrementWorkersNumber();
    }

	/**
	 * @param searched_Worker_Id
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidId(int searched_Worker_Id) {
		for (Worker worker : Worker_List) {
			int current_Worker_Id = worker.getId_Worker();
			if (current_Worker_Id == searched_Worker_Id)
				return true;
		}
		return false;
	}

	public Worker getWorkerById(int searched_Worker_Id) throws Exception {
		for (Worker worker : Worker_List) {
			int current_Worker_Id = worker.getId_Worker();
			if (current_Worker_Id == searched_Worker_Id)
				return worker;
		}
		throw new Exception("Error Search: Worker Not Found");

	}

	public void showEveryWorkerFirstName() {
		for (Worker worker : Worker_List) {
			String current_Worker_Firstname = worker.getFirstname_Worker();
			System.out.println(current_Worker_Firstname);
		}
	}

	/**
	 * @param searched_Worker_Firstname
	 * @param searched_Worker_Lastname
	 * @return true if searched worker found, else false
	 */
	public boolean isWorkerValidName(String searched_Worker_Firstname, String searched_Worker_Lastname) {
		for (Worker worker : Worker_List) {
			String current_Worker_Firstname = worker.getFirstname_Worker();
			String current_Worker_Lastname = worker.getLastname_Worker();
			if ((current_Worker_Firstname.equals(searched_Worker_Firstname))
					&& (current_Worker_Lastname.equals(searched_Worker_Lastname)))
				return true;
		}
		return false;
	}

	public Worker getWorkerByName(String searched_Worker_Firstname, String searched_Worker_Lastname) throws Exception {
		for (Worker worker : Worker_List) {
			String current_Worker_Firstname = worker.getFirstname_Worker();
			String current_Worker_Lastname = worker.getLastname_Worker();
			if ((current_Worker_Firstname.equals(searched_Worker_Firstname))
					&& (current_Worker_Lastname.equals(searched_Worker_Lastname)))
				return worker;
		}
		throw new Exception("Error Search: Worker Not Found");
	}

	public int deleteWorker(Worker workerToDelete) throws Exception {

		for (Worker worker : Worker_List) {
			if (worker.getId_Worker() == workerToDelete.getId_Worker()) {
				this.Worker_List.remove(worker);
				return 1;
			}
		}
		throw new Exception("Error Remove: Worker Not Found");

	}
}
