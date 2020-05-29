package central;

import java.io.IOException;

public class ManagerController {
	private final int APPLICATION_DEFAULT_PORT = 7771;

	private Company company;
	private DataTransferServer server;
	private DataManager<Company> dm;

	public ManagerController(String CompanyName) {
		dm = new DataManager<Company>();
		company = getDataManager().deserialiseObject();
		server = new DataTransferServer(this, APPLICATION_DEFAULT_PORT);
		ManagerView vue = new ManagerView(this);
		//new Thread(this.server).start();
	}
	
	public void serialize() {
		try {
			dm.serialiseObject(company);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public DataManager<Company> getDataManager(){
		return dm;
	}

	public void parseEmulatorInput(String input) {

		String[] strTmp = input.split("/");
		int id_Worker = Integer.parseInt(strTmp[2]);
		String date = strTmp[0];
		String time = strTmp[1];

		try {
			Worker signingIn_Worker = company.whereIsWorker(id_Worker).getWorkerById(id_Worker);

			try {
				WorkingDay wdTemp = signingIn_Worker.getLastWorkingDay();
				if (wdTemp.getTodaysDate().equals(date)) {

					if (wdTemp.getArrivalTime() == null) {
						wdTemp.setArrivalTime(time);
					} else {
						if (wdTemp.getDepartureTime() == null) {
							wdTemp.setDepartureTime(time);
						}
					}
				} else {

					signingIn_Worker.addWorkingDay(date, time);

				}
			} catch (Exception e) {
				signingIn_Worker.addWorkingDay(date, time); // on creer le premier jour

			}

		} catch (Exception e) {
			System.out.println("INVAILD WORKER ID");
		}

	}

	public void updateServerSettings(int portNumber) {

		server.stopCurrentServer();
		server = new DataTransferServer(this, portNumber);
		new Thread(this.server).start();

		
	}

	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	public static void main(String[] args) {
		/*
		 * System.out.println("ETAPE1: --------------------"); TimeKeeper pointeuse =
		 * new TimeKeeper(1); TimeKeeper pointeuse2 = new TimeKeeper(2);
		 * System.out.println(pointeuse); System.out.println(pointeuse2);
		 * 
		 * try { DataManager dm = new DataManager(); dm.serialiseObject(pointeuse);
		 * 
		 * TimeKeeper pointeuse3 = (TimeKeeper) dm.deserialiseObject();
		 * System.out.println("////////////"); System.out.println(pointeuse3);
		 * 
		 * } catch (IOException | ClassNotFoundException e) { e.printStackTrace(); }
		 * 
		 * System.out.println("ETAPE 1 VALIDEE--------------------------------------");
		 * System.out.println("--------------------------------------");
		 * System.out.println("ETAPE2: Sockets:--------------------------------------");
		 * 
		 * try { DataTransferServer ds = new DataTransferServer(); ds.setupConnection();
		 * ds.connect(); ArrayList<Object> listofchanges = ds.getListOfUpdates();
		 * //System.out.println(listofchanges); } catch (IOException |
		 * ClassNotFoundException e) { e.printStackTrace(); }
		 */
		/*
		 * Company AledS6 = new Company(1, "AledS6");
		 * 
		 * Department bot = new Department(1, "Botlane"); Department mid = new
		 * Department(2, "Midlane"); Department jungl = new Department(3, "Jungle");
		 * Department top = new Department(4, "Toplane");
		 * 
		 * Worker Mah = new Worker(12346, "Mah", "----"); Worker Adrien = new
		 * Worker(12347, "Adrien", "----"); Worker Alexandre = new Worker(12348,
		 * "Alexandre", "-----"); Worker MohamadAli = new Worker(12349, "MohamadAli",
		 * "------"); Worker Tim = new Worker(12350, "Tim", "----");
		 * 
		 * bot.add_Worker(Mah); bot.add_Worker(Alexandre); mid.add_Worker(Adrien);
		 * jungl.add_Worker(MohamadAli); top.add_Worker(Tim);
		 * 
		 * AledS6.add_Department(bot); AledS6.add_Department(mid);
		 * AledS6.add_Department(jungl); AledS6.add_Department(top);
		 */
		// DataManager<Company> dm = new DataManager<Company>();

		ManagerController mg = new ManagerController("AledS6");
/*
		Worker Mah = new Worker(12346, "Mah", "----");
		Worker Adrien = new Worker(12347, "Adrien", "----");
		Worker Alexandre = new Worker(12348, "Alexandre", "-----");
		Worker MohamadAli = new Worker(12349, "MohamadAli", "------");
		Worker Tim = new Worker(12350, "Tim", "----");
		Worker tom = new Worker(12352, "Tom", "Belda");

		String[] defaultArrivalTime1 = {"10:30","10:30","9:30","10:30","10:30"};
		String[] defaultDepartureTime1 = {"20:00","18:00","20:00","20:00","20:00"};
		tom.setDefault_ArrivalTime_Worker(defaultArrivalTime1);
		tom.setDefault_DepartureTime_Worker(defaultDepartureTime1);
		
		String[] defaultArrivalTime2 = {"14:30","14:30","14:30","14:30","18:30"};
		String[] defaultDepartureTime2 = {"22:00","22:00","22:00","23:00","05:00"};
		Mah.setDefault_ArrivalTime_Worker(defaultArrivalTime2);
		Mah.setDefault_DepartureTime_Worker(defaultDepartureTime2);
		
		tom.addWorkingDay("21/05/2020", "11:00", "21:00");
        tom.addWorkingDay("20/05/2020", "10:30", "20:30");
        tom.addWorkingDay("19/05/2020", "8:30", "19:00");
        tom.addWorkingDay("18/05/2020", "11:00", "21:00");
        tom.addWorkingDay("17/05/2020", "10:30", "20:30");
        tom.addWorkingDay("16/05/2020", "8:30", "19:00");
        tom.addWorkingDay("15/05/2020", "11:00", "21:00");
        tom.addWorkingDay("14/05/2020", "10:30", "20:30");
        tom.addWorkingDay("13/05/2020", "8:30", "19:00");
        tom.addWorkingDay("12/05/2020", "11:00", "21:00");
        tom.addWorkingDay("11/05/2020", "10:30", "20:30");
        tom.addWorkingDay("10/05/2020", "8:30", "19:00");
        tom.addWorkingDay("09/05/2020", "11:00", "21:00");
        tom.addWorkingDay("08/05/2020", "10:30", "20:30");
        tom.addWorkingDay("07/05/2020", "8:30", "19:00");

        Mah.addWorkingDay("21/05/2020", "15:00", "21:00");
        Mah.addWorkingDay("20/05/2020", "18:00", "05:00");
        Mah.addWorkingDay("19/05/2020", "14:00");
        
		Department bot = new Department(1, "Botlane");
		Department mid = new Department(2, "Midlane");
		Department jungl = new Department(3, "Jungle");
		Department top = new Department(4, "Toplane");
		Department pro = new Department(5, "Les pros");

		bot.add_Worker(Mah);
		bot.add_Worker(Alexandre);
		mid.add_Worker(Adrien);
		jungl.add_Worker(MohamadAli);
		top.add_Worker(Tim);
		pro.add_Worker(tom);

		mg.getCompany().add_Department(bot);
		mg.getCompany().add_Department(mid);
		mg.getCompany().add_Department(jungl);
		mg.getCompany().add_Department(top);
		mg.getCompany().add_Department(pro);

		try {
			System.out.println(Mah.getLastWorkingDay().getWeekDay());
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

		/*
		 * new Thread(mg.server).start();
		 * 
		 * //TEST SI ON AJOUTE LE ARRIVAL TIME ET DEPARTURE TIME DE LEMPLOYEE: while
		 * (true) { try { if(mg.getCompany().whereIsWorker(12346).getWorkerById(12346).
		 * checkWorkingDayByDate("2020-05-21")) { System.out.println("PRESENT");
		 * System.out.println(mg.getCompany().whereIsWorker(12346).getWorkerById(12346).
		 * getLastWorkingDay().getArrivalTime());
		 * System.out.println(mg.getCompany().whereIsWorker(12346).getWorkerById(12346).
		 * getLastWorkingDay().getDepartureTime()); Thread.sleep(2000); } } catch
		 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); } }
		 */
		/*
		 * try { // System.out.println(mg.getCompany().getDepartmentByName("Botlane").
		 * getWorker_List()); } catch (Exception e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); } try { //
		 * System.out.println(mg.getCompany().whereIsWorker(12346).getWorkerById(12346))
		 * ; } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * 
		 * DataTransferServer DTT = new DataTransferServer(); new Thread(DTT).start();
		 * while (true) { System.out.println(DTT.getListOfUpdates()); try {
		 * Thread.sleep(2000); } catch (InterruptedException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } System.out.println("ou�");
		 * 
		 * }
		 */
		/*
		 * try {
		 * 
		 * dm.serialiseObject(AledS6);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * Company cp = dm.deserialiseObject(); System.out.println(cp.toString());
		 * System.out.println("///////"); System.out.println(cp.getDepartment_List());
		 * cp.showEveryDepartmentName();
		 * System.out.println("/////////////////////////////////////");
		 * 
		 * Department dpTest; Worker wTest; try {
		 * System.out.println("--Test:getDepartmentByName: Jungle "); dpTest =
		 * cp.getDepartmentByName("Jungle"); System.out.println(dpTest.toString());
		 * System.out.println("--Test:getDepartmentById: 2 "); dpTest =
		 * cp.getDepartmentById(2); System.out.println(dpTest.toString());
		 * System.out.println("/////////////////////////////////////");
		 * System.out.println("--getWorkerById: 12347 Adrien ");
		 * 
		 * wTest = dpTest.getWorkerById(12347); System.out.println(wTest.toString());
		 * 
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * 
		 * System.out.println("/////////////////////////////////////");
		 * System.out.println("--DeleteWorker: 12347 Adrien ");
		 * 
		 * try { System.out.println(bot.getWorkerById(12346).toString());
		 * bot.deleteWorker(bot.getWorkerById(12346));
		 * System.out.println(bot.getWorkerById(12346).toString()); bot.add_Worker(Mah);
		 * // on reset les tests
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 * System.out.println("/////////////////////////////////////");
		 */

	}

}
