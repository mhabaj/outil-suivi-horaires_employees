package centralApplication;

public class Department {
	private int id_Department;
	private String name_Department;

	/**
	 * @param id_Department
	 * @param name_Department
	 */
	public Department(int id_Department, String name_Department) {
		this.setId_Department(id_Department);
		this.setName_Department(name_Department);
	}

	public int getId_Department() {
		return id_Department;
	}

	public void setId_Department(int id_Department) {
		this.id_Department = id_Department;
	}

	public String getName_Department() {
		return name_Department;
	}

	public void setName_Department(String name_Department) {
		this.name_Department = name_Department;
	}

}
