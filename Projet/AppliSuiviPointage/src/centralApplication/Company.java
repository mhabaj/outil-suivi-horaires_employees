package centralApplication;

import java.util.ArrayList;

public class Company {

	private int id_Company;
	private String name_Company;
	private ArrayList<Department> Department_List = new ArrayList<Department>();;

	/**
	 * @param id_Company
	 * @param name_Company
	 */
	public Company(int id_Company, String name_Company) {
		this.setId_Company(id_Company);
		this.setName_Company(name_Company);
	}

	/**
	 * @return the department_List
	 */
	public ArrayList<Department> getDepartment_List() {
		return Department_List;
	}

	/**
	 * @param department_List the department_List to set
	 */
	public void setDepartment_List(ArrayList<Department> department_List) {
		Department_List = department_List;
	}

	/**
	 * @return the id_Company
	 */
	public int getId_Company() {
		return id_Company;
	}

	/**
	 * @param id_Company the id_Company to set
	 */
	public void setId_Company(int id_Company) {
		this.id_Company = id_Company;
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

	/**
	 * @param searched_Department_Id
	 * @return true if searched department found, else false
	 */
	public boolean isDepartmentValidId(int searched_Department_Id) {
		for (Department department : Department_List) {
			int current_Department_Id = department.getId_Department();
			if (current_Department_Id == searched_Department_Id)
				return true;
		}
		return false;
	}
	
	/**
	 * @param searched_department_Name
	 * @return true if searched department found, else false
	 */
	public boolean isDepartmentValidName(String searched_Department_Name) {
		for (Department department : Department_List) {
			String current_Department_Name = department.getName_Department();
			if (current_Department_Name == searched_Department_Name)
				return true;
		}
		return false;
	}
	
	
	
}
