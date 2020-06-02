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
	private final int DEFAULT_AUTO_BACKUP_RATE_MS = 20000; // Auto backup delay 20 secs default

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
		autoBackup(DEFAULT_AUTO_BACKUP_RATE_MS); // start autoBackup every 10 secs
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
	public void parseEmulatorInput(String str) {

		String input = str.trim(); // remove spaces
		if (!input.equals("")) {
			// (20)\\d\\d([-])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])\\/ : we take the
			// number 20 with 2 more digits, then a "-", then 0 with a digit from 1 -> 9 OR
			// 1 with digit from 0->2...etc We will get at the end the format : yyyy-MM-dd/

			// ([01][0-9]|2[0-3]|\\d{0}[0-9]):[0-5][0-9]\\/ : we take 0, 1 or 2 with any
			// digit from 0 to 9, except 2 with a digit from 0 to 23 (for 20:00, 22:00,
			// 23:00..). Finally we get a format: HH:mm/ or H:mm/

			// ([1-9][0-9][0-9][0-9][0-9]) : we take 5 digits between 0 and 9 (first one
			// 1-9) (Worker ID is 5 digits and starts at 10000). Finally we get : workerID

			// We could've parsed it with code, but for simplicity and time effeciency
			// reasons, we chose regular expressions in here.

			if (input.matches(
					"(20)\\d\\d([-])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])\\/([01][0-9]|2[0-3]|\\d{0}[0-9]):[0-5][0-9]\\/([1-9][0-9][0-9][0-9][0-9])")) {
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
						// check if the event day is weekend or not:
						Date tmpDate = new SimpleDateFormat("dd/MM/yyyy").parse(date); // parse date String into Date
																						// object
						DateFormat format2 = new SimpleDateFormat("EEEE", Locale.US); // Get the day name out of the
																						// Date (Local.US
																						// for english days names)
						String weekDay = format2.format(tmpDate); // convert Date name to String
						if (!weekDay.equals("Saturday") && !weekDay.equals("Sunday")) {

							try {
								// check if worker is in company
								WorkerModel signingIn_Worker = company.whereIsWorker(id_Worker)
										.getWorkerById(id_Worker);

								try {
									// check if worker already signed in during current day :
									WorkingDayModel wdTemp = signingIn_Worker.getLastWorkingDay();
									WorkingDayModel wdTemp2 = signingIn_Worker.getWorkingDayByDate(date);

									if (wdTemp.getTodaysDate().equals(date)) {

										if (wdTemp.getArrivalTime() == null) { // if not checked in arrival:
											wdTemp.setArrivalTime(time); // check-in Arrival
										} else {
											if (wdTemp.getDepartureTime() == null) { // if he is finishing his day:
												wdTemp.setDepartureTime(time);
												signingIn_Worker.addTimeOverflow(wdTemp);
											}
										}
									} else if (wdTemp2 != null) {

										if (wdTemp2.getArrivalTime() == null) { // if not checked in arrival:
											wdTemp2.setArrivalTime(time); // check-in Arrival
										} else {
											if (wdTemp2.getDepartureTime() == null) { // if he is finishing his day:
												wdTemp2.setDepartureTime(time);
												signingIn_Worker.addTimeOverflow(wdTemp2);
											}

										}
									} else {
										signingIn_Worker.addWorkingDay(date, time);
									}
								} catch (Exception e) {

									signingIn_Worker.addWorkingDay(date, time); // If it's his first day, create it and
																				// sign
																				// him
																				// in
																				// with
																				// arrival time

								}
								view.updateInfos(signingIn_Worker.getId_Worker());

							} catch (Exception e) {
								// Could'nt identify worker using input ID -> end task
								System.out.println("Invalid worker ID" + System.lineSeparator());
							}
						} else {
							System.out.println("invalide entry as given date is a Saturday or a Sunday. "
									+ System.lineSeparator());
							// Weekend so company closed

						}
					} catch (ParseException e1) {
						System.out.println("Couldn't Parse input Date  " + System.lineSeparator()); // invalide date
																									// input
																									// ->
																									// end
																									// current Task.
					}

				}

			} else

			{
				System.out.println(
						" Invalide Input Data structure. Please refer to documentation for parsable/Valid data format:  yyyy-MM-dd/HH:mm (or H:mm)/workerID "
								+ System.lineSeparator()); // cant parse -> stop treatment

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
	 * Auto Backup server Data and settings on defined periods
	 * 
	 * @param ms
	 */
	public void autoBackup(int ms) {
		Runnable backupServiceRunTask = new Runnable() {
			public void run() {
				while (true) {
					serializeCompany();
					serializeServerSettings();
					try {
						Thread.sleep(ms);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread threadBackupServer = new Thread(backupServiceRunTask);
		threadBackupServer.start();
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

		@SuppressWarnings("unused")
		MainController mg = new MainController("Company");

	}

}
