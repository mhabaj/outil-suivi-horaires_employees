package central;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManagerController {
	private final int APPLICATION_DEFAULT_PORT = 7771;

	private Company company;
	private DataTransferServer server;
	private DataManager<Company> dm;
	private Thread serverThread;

	public ManagerController(String CompanyName) {
		dm = new DataManager<Company>();
		company = dm.deserialiseObject();
		server = new DataTransferServer(this, APPLICATION_DEFAULT_PORT);
		startServer();
		ManagerView vue = new ManagerView(this);
	}
	
	public void serializeCompany() {
		try {
			dm.serialiseObject(company);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseEmulatorInput(String input) {
		System.out.println("Reçu: " + input + System.lineSeparator());
		String[] strTmp = input.split("/");
		int id_Worker = Integer.parseInt(strTmp[2]);
		String datetmp = strTmp[0];
		String time = strTmp[1];

		////////////////////////////////////////////////////////////// on met le bon
		////////////////////////////////////////////////////////////// format de date

		try {
			DateFormat oldTMP = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			DateFormat newTMP = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
			Date date1 = oldTMP.parse(datetmp);

			String date = newTMP.format(date1);
			// System.out.println("DATEEE: " + date);
			////////////////////////////////////////////////////////////

			try {
				Worker signingIn_Worker = company.whereIsWorker(id_Worker).getWorkerById(id_Worker);

				try {
					WorkingDay wdTemp = signingIn_Worker.getLastWorkingDay();
					if (wdTemp.getTodaysDate().equals(date)) {

						if (wdTemp.getArrivalTime() == null) {
							wdTemp.setArrivalTime(time);
							signingIn_Worker.addTimeOverflowArrival(time, wdTemp);
						} else {
							if (wdTemp.getDepartureTime() == null) {
								wdTemp.setDepartureTime(time);
								signingIn_Worker.addTimeOverflowDepart(time, wdTemp);

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
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void startServer() {
		serverThread = new Thread(server);
		serverThread.start();
	}
	
	public void changeServerConfig(int newPort) {
		server.stopCurrentServer();
		DataTransferServer serverTMP = new DataTransferServer(this, newPort);
		server = serverTMP;
		startServer();

	}
	
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
