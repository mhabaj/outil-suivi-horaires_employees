package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.CompanyModel;
import model.DepartmentModel;
import model.WorkerModel;
import model.WorkingDayModel;

class CentralTest {

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////WorkingDay/////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void workingDayConstructorTest() {
		WorkingDayModel wd = new WorkingDayModel("02/06/2020", "9:30", "17:15");

		assertEquals(wd.getArrivalTime(), "9:30");
		assertEquals(wd.getDepartureTime(), "17:15");
		assertEquals(wd.getTodaysDate(), "02/06/2020");
		assertEquals(wd.getWeekDay(), "Tuesday");

	}

	@Test
	void workingDayIsCalculatedTest() {
		WorkingDayModel wd = new WorkingDayModel("02/06/2020", "9:30", "17:15");

		assertFalse(wd.isTimeCalculated());
		wd.setTimeOverflow(20);
		assertTrue(wd.isTimeCalculated());

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////Worker/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void workerConstructorTest() {
		WorkerModel w = new WorkerModel(12345, "Bat", "Man");

		assertEquals(w.getId_Worker(), 12345);
		assertEquals(w.getFirstname_Worker(), "Bat");
		assertEquals(w.getLastname_Worker(), "Man");

		assertEquals(w.getDefault_ArrivalTime_Worker()[0], "7:00");
		assertEquals(w.getDefault_DepartureTime_Worker()[0], "17:00");

	}

	@Test
	void workerWorkingDayTest() {
		WorkerModel w = new WorkerModel(12345, "Bat", "Man");
		w.addWorkingDay("02/06/2020", "7:00", "18:00");

		assertTrue(w.checkWorkingDayByDate("02/06/2020"));
		try {
			w.addTimeOverflow(w.getLastWorkingDay());
			assertEquals(w.getLastWorkingDay().getTimeOverflow(), 60);

		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(w.getWorkingTimeOverflow_Worker(), 60);

	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////Department/////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void departmentConstructorTest() {
		CompanyModel cp = new CompanyModel("Polytech");
		DepartmentModel dp = new DepartmentModel("Informatique", cp);
		cp.add_Department(dp);

		assertEquals(dp.getName_Department(), "Informatique");
		assertEquals(dp.getId_Department(), cp.getId_Department_Counter() - 1);
	}

	@Test
	void departmentWorkerTest() {
		CompanyModel cp = new CompanyModel("Polytech");
		DepartmentModel dp = new DepartmentModel("Informatique", cp);
		cp.add_Department(dp);
		WorkerModel w = new WorkerModel(12345, "Bat", "Man");
		WorkerModel w2 = new WorkerModel(12333, "Spider", "Boiii");
		dp.add_Worker(w);
		dp.add_Worker(w2);

		assertTrue(dp.isWorkerValidName("Bat", "Man"));
		assertTrue(dp.isWorkerValidName("Spider", "Boiii"));
		assertTrue(dp.isWorkerValidId(12345));
		assertTrue(dp.isWorkerValidId(12333));

		try {
			dp.deleteWorker(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertFalse(dp.isWorkerValidId(12345));

	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////Company/////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	void CompanyTest() {
		CompanyModel cp = new CompanyModel("Polytech");
		DepartmentModel dp = new DepartmentModel("Informatique", cp);
		cp.add_Department(dp);
		WorkerModel w = new WorkerModel(12345, "Bat", "Man");
		WorkerModel w2 = new WorkerModel(12333, "Spider", "Boiii");
		dp.add_Worker(w);
		dp.add_Worker(w2);

		try {
			assertEquals(cp.whereIsWorker(12345), dp);

			assertTrue(cp.isDepartmentValidName("Informatique"));
			cp.deleteDepartment(dp);
			assertFalse(cp.isDepartmentValidName("Informatique"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
