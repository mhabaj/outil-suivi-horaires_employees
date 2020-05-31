package pointeuse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class EmulatorController {

	private EmulatorVue vue;
	private TimeManagerController time;

	public EmulatorController() {
		vue = new EmulatorVue(this);
		time = new TimeManagerController(vue);
		Thread thread = new Thread(time);
		thread.start();
	}

	public TimeManagerController getTimeManager() {
		return time;
	}

	public void sendId(int id) {

		if (!time.getTodayWeekDay().equals("Sunday") && !time.getTodayWeekDay().equals("Saturday")) {
			System.out.println(time.getTodayWeekDay());
			String message = createMessage(id);
			System.out.println(message);

			Socket socket;
			OutputStream outputStream;
			ObjectOutputStream objectOutputStream;

			// Initialisation du socket d'envoie
			try {
				socket = new Socket("127.0.0.1", 7779);
				// Flux de donnees:
				outputStream = socket.getOutputStream();
				// Envoie des donnes via objectOutputStream:
				objectOutputStream = new ObjectOutputStream(outputStream);

				ArrayList<String> infos = readData();

				if (infos != null) {
					for (String info : infos) {
						objectOutputStream.writeObject(info);
					}
					deleteFile();
				}

				objectOutputStream.writeObject(message);

				objectOutputStream.close();
				outputStream.close();
				socket.close();
			} catch (IOException e) {

				saveData(message);
			}

			System.out.println(readData());
		} else {
			System.out.println("ENVOYER UN MESSAGE DERREUR ICI");
		}
	}

	public void deleteFile() {
		try {
			File file = new File("save.txt");
			if (file.exists())
				Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> readData() {
		ArrayList<String> infos = new ArrayList<String>();

		try {
			File file = new File("save.txt");
			if (file.exists()) {
				Scanner reader = new Scanner(file);
				while (reader.hasNextLine()) {
					infos.add(reader.nextLine());
				}
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return infos;
	}

	public void saveData(String info) {
		try {
			File file = new File("save.txt");
			if (!file.exists())
				Files.createFile(file.toPath());
			Files.write(file.toPath(), (info + '\n').getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String createMessage(int id) {
		StringBuffer strBuff = new StringBuffer();

		strBuff.append(time.getCurrent_Date());
		strBuff.append("/");
		strBuff.append(time.getRounded_Time());
		strBuff.append("/");
		strBuff.append(id);

		return strBuff.toString();
	}

	public static void main(String args[]) {
		EmulatorController control = new EmulatorController();
	}

}
