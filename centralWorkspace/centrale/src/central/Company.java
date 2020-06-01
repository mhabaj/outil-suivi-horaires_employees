package central;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6607331681733486180L;

	private String name_Company;
	private ArrayList<Department> Department_List;
	private int id_Worker_Counter = 10000;
	private int id_Department_Counter = 1;

	public void incrementDepartmentNumber() {
		id_Department_Counter++;
	}

	public int getId_Department_Counter() {
		return id_Department_Counter;
	}

	public void incrementWorkersNumber() {
		id_Worker_Counter++;
	}

	/**
	 * @return the id_Worker_Counter
	 */
	public int getId_Worker_Counter() {
		return id_Worker_Counter;
	}

	/**
	 * @param id_Worker_Counter the id_Worker_Counter to set
	 */
	public void setId_Worker_Counter(int id_Worker_Counter) {
		this.id_Worker_Counter = id_Worker_Counter;
	}

	/**
	 * @param id_Company
	 * @param name_Company
	 */
	public Company(String name_Company) {
		Department_List = new ArrayList<Department>();
		this.setName_Company(name_Company);
	}

	public HashMap<Worker, WorkingDay> getCompanyActivityPerDate(String date) {

		HashMap<Worker, WorkingDay> tmp = new HashMap<Worker, WorkingDay>();

		for (Department dpt : Department_List) {

			HashMap<Worker, WorkingDay> tmp2 = dpt.getDepartmentActivityPerDate(date);

			if (!tmp2.isEmpty()) {
				tmp.putAll(tmp2);
			}
		}

		return tmp;

	}

	/**
	 * @return the department_List
	 */
	public ArrayList<Department> getDepartment_List() {

		if (Department_List.isEmpty()) {
			return null;
		} else {

			return Department_List;
		}
	}

	/**
	 * @param department_List the department_List to set
	 */
	public void setDepartment_List(ArrayList<Department> department_List) {
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
	 * @param department_To_Add
	 */
	public void add_Department(Department department_To_Add) {
		this.Department_List.add(department_To_Add);
	}

	public void add_New_Department(int id_Department, String name_Department) {
		Department dptTemp = new Department(name_Department, this);
		this.Department_List.add(dptTemp);
	}

	public Department whereIsWorker(int searched_Worker_Id) throws Exception {
		for (Department department : Department_List) {
			if (department.isWorkerValidId(searched_Worker_Id) == true)
				return department;
		}
		throw new Exception("Error Search: Worker not found");
	}

	/**
	 * @param searched_department_Name
	 * @return true if searched department found, else false
	 */
	public boolean isDepartmentValidName(String searched_Department_Name) {
		for (Department department : Department_List) {
			String current_Department_Name = department.getName_Department();
			if (current_Department_Name.contentEquals(searched_Department_Name))
				return true;
		}
		return false;
	}

	public void showEveryDepartmentName() {
		for (Department department : Department_List) {
			String current_Department_Name = department.getName_Department();
			System.out.println(current_Department_Name);
		}
	}

	public Department getDepartmentByName(String searched_Department_Name) throws Exception {
		for (Department department : Department_List) {
			String current_Department_Name = department.getName_Department();
			if (current_Department_Name.equals(searched_Department_Name))
				return department;
		}
		throw new Exception("Error Search: Department Not Found");
	}

	public Department getDepartmentByID(int searched_Department_ID) throws Exception {
		for (Department department : Department_List) {
			int current_Department_ID = department.getId_Department();
			if (current_Department_ID == searched_Department_ID)
				return department;
		}
		throw new Exception("Error Search: Department Not Found");
	}

	public int deleteDepartment(Department dptToDelete) throws Exception {
		for (Department dpt : Department_List) {
			if (dpt.getId_Department() == dptToDelete.getId_Department()) {
				this.Department_List.remove(dptToDelete);
				return 1;
			}
		}
		throw new Exception("Error Remove: Department not found");
	}

	@Override
	public String toString() { // A refaire avec string buffer
		return "Company [name_Company=" + name_Company + ", Department_List=" + Department_List + "]";
	}

}
