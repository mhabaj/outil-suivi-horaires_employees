package controller;

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

import view.MainView;

/**
 * 
 * main controller for the application
 * 
 * @author Alhabaj Mahmod /Belda Tom / Dakroub MohamadAli
 * 
 */
public class MainController {

	private final String DATA_BACKUP_PATH = "src" + File.separator + "assets" + File.separator + "DATA_BACKUP.txt";

	private final String SETTINGS_BACKUP_PATH = "src" + File.separator + "assets" + File.separator + "config.txt";

	private final int SERVER_DEFAULT_PORT = 7700;
	private final String SERVER_DEFAULT_ADDRESS = "127.0.0.1";

	private int serverPort;
	private String serverAddress;
	private MainView view;
	private TimeController time;

	/**
	 * constructor
	 */
	public MainController() {
		// setup the server settings
		setupSettings();
		// create the view
		view = new MainView(this);
		// create the time controller
		time = new TimeController(view);
		Thread thread = new Thread(time);
		thread.start();
	}

	/**
	 * @return time manager
	 */
	public TimeController getTimeManager() {
		return time;
	}

	/**
	 * send the id to the central application
	 * 
	 * @param id id to send
	 */
	public void sendId(int id) {
		// if it's not sunday or saturday
		if (!time.getTodayWeekDay().equals("Sunday") && !time.getTodayWeekDay().equals("Saturday")) {
			System.out.println(time.getTodayWeekDay());
			// create message
			String message = createMessage(id);
			System.out.println(message);

			// create the socket
			Socket socket;
			OutputStream outputStream;
			ObjectOutputStream objectOutputStream;
			// init the socket
			try {
				// link the socket
				socket = new Socket(serverAddress, serverPort);
				// init data stream
				outputStream = socket.getOutputStream();
				// init object stream
				objectOutputStream = new ObjectOutputStream(outputStream);

				// get the data from the backup file
				ArrayList<String> infos = readData(DATA_BACKUP_PATH);

				// if there is data
				if (infos != null) {
					for (String info : infos) {
						// send the data
						objectOutputStream.writeObject(info);
					}
					// delete the file
					deleteFile(DATA_BACKUP_PATH);
				}

				// send the message
				objectOutputStream.writeObject(message);

				// close everything
				objectOutputStream.close();
				outputStream.close();
				socket.close();
			}
			// if the connection isn't working
			catch (IOException e) {
				// save the data in the file
				saveData(message, DATA_BACKUP_PATH);
			}

			System.out.println(readData(DATA_BACKUP_PATH));
		}
	}

	/**
	 * setup the setting of the server
	 */
	public void setupSettings() {

		// get the setup from the setup file
		ArrayList<String> parameters = readData(SETTINGS_BACKUP_PATH);

		// if there are parameters
		if (parameters != null) {

			try {
				// get the parameters
				int importedPort = Integer.parseInt(parameters.get(0));
				String importedServerAddress = parameters.get(1);
				// set the informations
				if (importedPort != SERVER_DEFAULT_PORT && importedServerAddress != SERVER_DEFAULT_ADDRESS) {
					serverPort = importedPort;
					serverAddress = importedServerAddress;
					System.out.println("Config file found, Port: " + serverPort + ", Address: " + serverAddress
							+ System.lineSeparator());
				} else {
					serverPort = SERVER_DEFAULT_PORT;
					serverAddress = SERVER_DEFAULT_ADDRESS;
					System.out.println("Using default server settings, Port: " + serverPort + ", Address: "
							+ serverAddress + System.lineSeparator());
				}
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				serverPort = SERVER_DEFAULT_PORT;
				serverAddress = SERVER_DEFAULT_ADDRESS;
				System.out.println("invalid Config file data. Using default server settings, Port: " + serverPort
						+ ", Address: " + serverAddress + System.lineSeparator());
			}

		} else {
			serverPort = SERVER_DEFAULT_PORT;
			serverAddress = SERVER_DEFAULT_ADDRESS;
			System.out.println("No Config file detected. Using default server settings: Port" + serverPort + "Address:"
					+ serverAddress + System.lineSeparator());
		}

	}

	/**
	 * @return server port
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @return server address
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * save the settings in a file
	 */
	public void saveSettings() {
		// delete the last settings
		deleteFile(SETTINGS_BACKUP_PATH);
		// save the new data
		saveData(String.valueOf(serverPort), SETTINGS_BACKUP_PATH);
		saveData(serverAddress, SETTINGS_BACKUP_PATH);
	}

	/**
	 * change port number
	 * 
	 * @param portNumber
	 */
	public void changeServerPort(int portNumber) {
		this.serverPort = portNumber;
		saveSettings();
	}

	/**
	 * change server address
	 * 
	 * @param serverAdress
	 */
	public void changeServerAddress(String serverAdress) {
		this.serverAddress = serverAdress;
		saveSettings();

	}

	/**
	 * delete file
	 * 
	 * @param PATH file path
	 */
	public void deleteFile(String PATH) {
		try {
			File file = new File(PATH);
			if (file.exists())
				Files.delete(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * read data from a file
	 * 
	 * @param PATH file path
	 * @return data from the file
	 */
	public ArrayList<String> readData(String PATH) {
		// create an array of data
		ArrayList<String> infos = new ArrayList<String>();

		try {
			// create the file with the path
			File file = new File(PATH);
			// if the file exist
			if (file.exists()) {
				// read the data inside and put them in the array
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

	/**
	 * save data in a file
	 * 
	 * @param info data to save
	 * @param Path file path
	 */
	public void saveData(String info, String Path) {
		try {
			// create a file
			File file = new File(Path);
			// if the file doesn't exist
			if (!file.exists())
				// create the file
				Files.createFile(file.toPath());
			// write the data in it
			Files.write(file.toPath(), (info + '\n').getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * create the message to send
	 * 
	 * @param id
	 * @return message
	 */
	public String createMessage(int id) {
		StringBuffer strBuff = new StringBuffer();

		// ad the informations to create the message to send
		strBuff.append(time.getCurrent_Date());
		strBuff.append("/");
		strBuff.append(time.getRounded_Time());
		strBuff.append("/");
		strBuff.append(id);

		return strBuff.toString();
	}

	public static void main(String args[]) {
		@SuppressWarnings("unused")
		MainController control = new MainController();
	}

}
