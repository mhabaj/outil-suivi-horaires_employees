package centralApplication;

import java.io.IOException;
import java.util.ArrayList;

import TimeKeeperApplication.TimeKeeper;

public class main {

	public static void main(String[] args) {
		System.out.println("ETAPE1: --------------------");
		TimeKeeper pointeuse = new TimeKeeper(1);
		TimeKeeper pointeuse2 = new TimeKeeper(2);
		System.out.println(pointeuse);
		System.out.println(pointeuse2);

		try {
			DataManager dm = new DataManager();
			dm.serialiseObject(pointeuse);

			TimeKeeper pointeuse3 = (TimeKeeper) dm.deserialiseObject();
			System.out.println("////////////");
			System.out.println(pointeuse3);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("ETAPE 1 VALIDEE--------------------------------------");
		System.out.println("--------------------------------------");
		System.out.println("ETAPE2: Sockets:--------------------------------------");

		try {
			DataTransferServer ds = new DataTransferServer();
			ds.setupConnection();
			ds.connect();
			ArrayList<Object> listofchanges = ds.getListOfUpdates();
			 //System.out.println(listofchanges);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}