package TimeKeeperApplication;

import java.io.IOException;
import java.util.ArrayList;

import centralApplication.DataManager;

public class main {

	public static void main(String[] args) {
		/**
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

		System.out.println("Yes");
		
		System.out.println("ETAPE 1 VALIDEE--------------------------------------");
		System.out.println("--------------------------------------");*/
		System.out.println("ETAPE2: Sockets:--------------------------------------");
		TimeKeeper pointeuse = new TimeKeeper(1);
		TimeKeeper pointeuse2 = new TimeKeeper(2);
		System.out.println(pointeuse);
		System.out.println(pointeuse2);
		try {
			ArrayList<Object> listofchanges = new ArrayList<Object>();
			listofchanges.add(pointeuse);
			listofchanges.add(pointeuse2);

			DataTransferClient ds = new DataTransferClient();
			
			ds.send(listofchanges);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}