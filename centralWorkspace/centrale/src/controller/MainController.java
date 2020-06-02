package controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import model.CompanyModel;
import model.WorkerModel;
import model.WorkingDayModel;
import view.MainView;

/**
 * 
 * Gestion de l'ensemble des mechanismes du programme + main Class that manages
 * all Central application mechanisms
 * 
 * @author Alhabaj Mahmod/ Belda Tom/ Dakroub MohamadAli
 * 
 *
 */
public class MainController {
	private final String COMPANY_DATABASE = "src" + File.separator + "assets" + File.separator + "dataBase.ser";

	private final String PARAMETERS_BACKUP = "src" + File.separator + "assets" + File.separator + "settings_save.ser";

	private final int APPLICATION_DEFAULT_PORT = 7700;

	private CompanyModel company;
	private ServerController server;
	private DataController<CompanyModel> dm; // Company structure dataManager
	private DataController<Integer> serverParameters; // Parameters structure dataManager
	private Thread serverThread; // main server thread
	private MainView view; // application GUI

	/**
	 * 
	 * Constructor of Class ManagerController
	 * 
	 * @param CompanyName Company name
	 */
	public MainController(String CompanyName) {
		// Data settings:
		System.out.println("Starting up.." + System.lineSeparator());
		dm = new DataController<CompanyModel>(COMPANY_DATABASE, this);
		company = dm.deserializeObject(); // restore company data
		if (company == null) // if no data found, create new company
			company = new CompanyModel("Company");

		////// server settings:
		serverParameters = new DataController<Integer>(PARAMETERS_BACKUP, this);
		int parameters_backup = APPLICATION_DEFAULT_PORT;
		try {
			parameters_backup = serverParameters.deserializeObject(); // try to restore parameters data

		} catch (NullPointerException e) { // failed to restore data: Empty file -> continue using default ports
			System.out.println(
					"No server Config file found or the file is empty, trying using default server settings on port: "
							+ APPLICATION_DEFAULT_PORT + System.lineSeparator());

		}

		if (parameters_backup != APPLICATION_DEFAULT_PORT) { // if found port isn't same as the default port then we use
																// it
			server = new ServerController(this, parameters_backup); // startup server with custom port
			System.out.println(" Configured port found! " + System.lineSeparator());
		} else
			server = new ServerController(this, APPLICATION_DEFAULT_PORT); // start up with default port

		view = new MainView(this); // startup GUI
		startServer(); // start server thread
	}

	/**
	 * Get application View
	 * 
	 * @return ManagerView
	 */
	public MainView getManagerView() {
		return view;
	}

	/**
	 * Backup Company
	 */
	public void serializeCompany() {
		try {
			dm.serialiseObject(company);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * backup Settings
	 */
	public void serializeServerSettings() {
		try {
			serverParameters.serialiseObject(server.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Imports Workers entries from a .txt file. <br>
	 * 
	 * Inside the text file, each line must be ONLY of this format:<br>
	 * <br>
	 * <b>YYYY-MM-DD/HH:mm/workerID</b> <br>
	 * <br>
	 * 
	 * which means: DayOfWorkerEntry/TimeOfAction in hours:minutes/workerID. <br>
	 * <br>
	 * 
	 * <b> Important </b> : Always put arrival time Line before departure time Line
	 * for a given worker.<br>
	 * Application Parse function will detect each worker Departure time <b>only if
	 * arrival time has already been given.</b><br>
	 * <br>
	 * 
	 * <b> Example of file content : </b> <br>
	 * 
	 * 2020-06-01/08:30/12345 <br>
	 * 2020-06-08/10:30/12347 <br>
	 * 2020-06-08/17:30/12347 <br>
	 * 2020-06-01/18:30/12345 <br>
	 * <br>
	 * 
	 * 
	 * 
	 * 
	 * @param filePath
	 */
	public void importFromFile(String filePath) {

		dm.importEntriesFromFile(filePath);

	}

	/**
	 * Imports Workers entries from external sources. <br>
	 * 
	 * String Content must be ONLY of this format:<br>
	 * <br>
	 * <b>YYYY-MM-DD/HH:mm/workerID</b> <br>
	 * <br>
	 * 
	 * which means: DayOfWorkerEntry/TimeOfAction in hours:minutes/workerID. <br>
	 * <br>
	 * 
	 * <b> Example of String content : </b> <br>
	 * 
	 * 2020-06-01/08:30/12345 <br>
	 * 
	 * <br>
	 * 
	 * @param input
	 */
	public void parseEmulatorInput(String input) {

		String[] strTmp = input.split("/"); // split data into 3 strings

		if (strTmp.length < 3) {
			System.out.println("Couldn't Parse input DATA  " + System.lineSeparator()); // invalide input -> end
		} else {
			int id_Worker = Integer.parseInt(strTmp[2]); // get worker id from split operation
			String datetmp = strTmp[0];// get dateOfAction from split operation
			String time = strTmp[1];// get timeOfAction from split operation

			try {
				// Parse date into the right dd/MM/yyyy format for further treatment:
				///////////////////////////////////////////////////////////////////
				DateFormat oldTMP = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				DateFormat newTMP = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
				Date date1 = oldTMP.parse(datetmp);

				String date = newTMP.format(date1);
				//////////////////////////////////////////////////////////////////

				try {
					// check if worker is in company
					WorkerModel signingIn_Worker = company.whereIsWorker(id_Worker).getWorkerById(id_Worker);

					try {
						// check if worker already signed in during current day :
						WorkingDayModel wdTemp = signingIn_Worker.getLastWorkingDay();
						if (wdTemp.getTodaysDate().equals(date)) {

							if (wdTemp.getArrivalTime() == null) { // if not checked in arrival:
								wdTemp.setArrivalTime(time); // check-in Arrival
							} else {
								if (wdTemp.getDepartureTime() == null) { // if he is finishing his day:
									wdTemp.setDepartureTime(time);
									signingIn_Worker.addTimeOverflow(wdTemp); // calculate if he left early or late (if
																				// he
																				// did respect his schedule
								}
							}
						} else {
							signingIn_Worker.addWorkingDay(date, time); // if the event day never found, create it and
																		// add
																		// the arrival time

						}
					} catch (Exception e) {

						signingIn_Worker.addWorkingDay(date, time); // If it's his first day, create it and sign him in
																	// with
																	// arrival time

					}
					view.updateInfos(signingIn_Worker.getId_Worker());

				} catch (Exception e) {
					System.out.println("Invalid worker ID" + System.lineSeparator()); // Could'nt identify worker using
																						// input ID -> end task
				}
			} catch (ParseException e1) {
				System.out.println("Couldn't Parse input Date  " + System.lineSeparator()); // invalide date input ->
																							// end
																							// current Task.
			}
		}
	}

	/**
	 * Start App server into designated thread
	 */
	public void startServer() {
		serverThread = new Thread(server);
		serverThread.start();
	}

	/**
	 * 
	 * change server Port
	 * 
	 * @param newPort
	 */
	public void changeServerConfig(int newPort) {
		if (newPort != server.getPort()) {

			server.stopCurrentServer();
			ServerController serverTMP = new ServerController(this, newPort);
			server = serverTMP;
			startServer();
		}

	}

	/**
	 * Get Server Port
	 * 
	 * @return Current serverPort
	 */
	public int getServerPort() {
		return server.getPort();
	}

	/**
	 * Get Company
	 * 
	 * @return current Company Class
	 */
	public CompanyModel getCompany() {
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

		MainController mg = new MainController("AledS6");
		// mg.changeServerConfig(7800);
		// mg.changeServerConfig(7700);

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
		 * catch block e.printStackTrace(); } System.out.println("oué");
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
