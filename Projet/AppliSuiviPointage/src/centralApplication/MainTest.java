package centralApplication;

import java.io.IOException;

public class MainTest {

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

		Company AledS6 = new Company(1, "AledS6");

		Department bot = new Department(1, "Botlane");
		Department mid = new Department(2, "Midlane");
		Department jungl = new Department(3, "Jungle");
		Department top = new Department(4, "Toplane");

		Worker Mah = new Worker(12346, "Mah", "----");
		Worker Adrien = new Worker(12347, "Adrien", "----");
		Worker Alexandre = new Worker(12348, "Alexandre", "-----");
		Worker MohamadAli = new Worker(12349, "MohamadAli", "------");
		Worker Tim = new Worker(12350, "Tim", "----");

		bot.add_Worker(Mah);
		bot.add_Worker(Alexandre);
		mid.add_Worker(Adrien);
		jungl.add_Worker(MohamadAli);
		top.add_Worker(Tim);

		AledS6.add_Department(bot);
		AledS6.add_Department(mid);
		AledS6.add_Department(jungl);
		AledS6.add_Department(top);
		DataManager<Company> dm = new DataManager<Company>();

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
		 */
		System.out.println("/////////////////////////////////////");
		System.out.println("--DeleteWorker: 12347 Adrien ");

		try {
			System.out.println(bot.getWorkerById(12346).toString());
			bot.deleteWorker(bot.getWorkerById(12346));
			System.out.println(bot.getWorkerById(12346).toString());
			bot.add_Worker(Mah); // on reset les tests

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("/////////////////////////////////////");

	}

}