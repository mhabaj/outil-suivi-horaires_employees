package centralApplication;

public class Company {

	private int id_Company;
	private String name_Company;

	/**
	 * @param id_Company
	 * @param name_Company
	 */
	public Company(int id_Company, String name_Company) {
		this.setId_Company(id_Company);
		this.setName_Company(name_Company);
	}

	public int getId_Company() {
		return id_Company;
	}

	public void setId_Company(int id_Company) {
		this.id_Company = id_Company;
	}

	public String getName_Company() {
		return name_Company;
	}

	public void setName_Company(String name_Company) {
		this.name_Company = name_Company;
	}

}
