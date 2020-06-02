
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 * 
 *         Represents the Company data Structure
 *
 */
public class CompanyModel implements Serializable {

	private static final long serialVersionUID = 6607331681733486180L;

	private String name_Company;
	private ArrayList<DepartmentModel> Department_List;
	private int id_Worker_Counter = 10000; //a worker ID is minimum 10000 and max 99999
	private int id_Department_Counter = 1;

	/**
	 * Initialize Company Object
	 * 
	 * @param id_Company
	 * @param name_Company
	 */
	public CompanyModel(String name_Company) {
		this.setName_Company(name_Company);
		Department_List = new ArrayList<DepartmentModel>();
	}

	/**
	 * Increments number of departments ID
	 */
	public void incrementDepartmentNumber() {
		id_Department_Counter++;
	}

	/**
	 * get Department ID counter actual value
	 * 
	 * @return int id_Department_Counter
	 */
	public int getId_Department_Counter() {
		return id_Department_Counter;
	}

	/**
	 * increments worker ID number
	 */
	public void incrementWorkersNumber() {
		id_Worker_Counter++;
	}

	/**
	 * Worker ID counter actual value
	 * 
	 * @return id_Worker_Counter
	 */
	public int getId_Worker_Counter() {
		return id_Worker_Counter;
	}

	/**
	 * set Worker ID counter
	 * 
	 * @param id_Worker_Counter the id_Worker_Counter to set
	 */
	public void setId_Worker_Counter(int id_Worker_Counter) {
		this.id_Worker_Counter = id_Worker_Counter;
	}

	/**
	 * Retrieves A hashmap of Worker per WorkingDay using a specific date
	 * 
	 * @param date Working Date
	 * @return HashMap<Worker, WorkingDay>
	 */
	public HashMap<WorkerModel, WorkingDayModel> getCompanyActivityPerDate(String date) {

		HashMap<WorkerModel, WorkingDayModel> tmp = new HashMap<WorkerModel, WorkingDayModel>();

		for (DepartmentModel dpt : Department_List) {

			HashMap<WorkerModel, WorkingDayModel> tmp2 = dpt.getDepartmentActivityPerDate(date);

			if (!tmp2.isEmpty()) {
				tmp.putAll(tmp2); // on append les deux lists
			}
		}

		return tmp;

	}

	/**
	 * @return null if Department_List is empty, else returns Department_List
	 */
	public ArrayList<DepartmentModel> getDepartment_List() {

		if (Department_List.isEmpty()) {
			return null;
		} else {

			return Department_List;
		}
	}

	/**
	 * @param department_List the department_List to set
	 */
	public void setDepartment_List(ArrayList<DepartmentModel> department_List) {
		Department_List = department_List;
	}

	/**
	 * @return the name_Company
	 */
	public String getName_Company() {
		return name_Company;
	}

	/**
	 * @param name_Company the name_Company to set
	 */
	public void setName_Company(String name_Company) {
		this.name_Company = name_Company;
	}

	/**
	 * Adds an existing Department to the company's Departments index
	 * 
	 * @param department_To_Add
	 */
	public void add_Department(DepartmentModel department_To_Add) {
		this.Department_List.add(department_To_Add);
	}

	/**
	 * Creates a new Department and adds it to the company's Departments index
	 * 
	 * @param id_Department
	 * @param name_Department
	 */
	public void add_New_Department(int id_Department, String name_Department) {
		DepartmentModel dptTemp = new DepartmentModel(name_Department, this);
		this.Department_List.add(dptTemp);
	}

	/**
	 * 
	 * Searches in which department is a worker
	 * 
	 * @param searched_Worker_Id
	 * @return department of the worker
	 * @throws Exception Worker not found
	 */
	public DepartmentModel whereIsWorker(int searched_Worker_Id) throws Exception {
		for (DepartmentModel department : Department_List) {
			if (department.isWorkerValidId(searched_Worker_Id) == true)
				return department;
		}
		throw new Exception("Error Search: Worker not found");
	}

	/**
	 * 
	 * Checks if a given name is a Valid Department name.Every department has a
	 * unique name.
	 * 
	 * @param searched_department_Name
	 * @return true if searched department found, else false
	 */
	public boolean isDepartmentValidName(String searched_Department_Name) {
		for (DepartmentModel department : Department_List) {
			String current_Department_Name = department.getName_Department();
			if (current_Department_Name.contentEquals(searched_Department_Name))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * Search a department by name
	 * 
	 * @param searched_Department_Name
	 * @return department
	 * @throws Exception Searched department not found
	 */
	public DepartmentModel getDepartmentByName(String searched_Department_Name) throws Exception {
		for (DepartmentModel department : Department_List) {
			String current_Department_Name = department.getName_Department();
			if (current_Department_Name.equals(searched_Department_Name))
				return department;
		}
		throw new Exception("Error Search: Department Not Found");
	}

	/**
	 * 
	 * Search a department by ID
	 * 
	 * @param searched_Department_ID
	 * @return department
	 * @throws Exception Searched department not found
	 */
	public DepartmentModel getDepartmentByID(int searched_Department_ID) throws Exception {
		for (DepartmentModel department : Department_List) {
			int current_Department_ID = department.getId_Department();
			if (current_Department_ID == searched_Department_ID)
				return department;
		}
		throw new Exception("Error Search: Department Not Found");
	}

	/**
	 * 
	 * Deletes a given department
	 * 
	 * @param dptToDelete
	 * @return 1 if success
	 * @throws Exception Department not found
	 */
	public int deleteDepartment(DepartmentModel dptToDelete) throws Exception {
		for (DepartmentModel dpt : Department_List) {
			if (dpt.getId_Department() == dptToDelete.getId_Department()) {
				this.Department_List.remove(dptToDelete);
				return 1;
			}
		}
		throw new Exception("Error Remove: Department not found");
	}

}
