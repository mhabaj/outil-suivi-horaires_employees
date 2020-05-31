package central;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataTransferServer implements Runnable {

	private final int DEFAULT_SERVER_PORT = 7778;

	private ServerSocket mainDeviceServerSocket;
	private Socket ClientToServerSocket;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;
	private ManagerController mc;
	private int serverPort;
	private boolean status_Server; // true = up, false = down

	public DataTransferServer(ManagerController mc, int serverPort) {

		this.serverPort = serverPort;
		this.mc = mc;

	}

	public DataTransferServer(ManagerController mc) {
		this.serverPort = DEFAULT_SERVER_PORT;
		this.mc = mc;
	}

	public void init() throws IOException {

		try {
			this.mainDeviceServerSocket = new ServerSocket(this.serverPort);
			status_Server = true;
		} catch (java.net.BindException e) {
			System.out.println("Selected Server port unavailable.");
			status_Server = false;

		}

	}

	public void connectAndRetrieveData() throws IOException, ClassNotFoundException {

		if (status_Server == true) {
			this.ClientToServerSocket = mainDeviceServerSocket.accept(); // socket client
			System.out.println("New Connection from:  " + ClientToServerSocket + System.lineSeparator());

			this.inputStream = this.ClientToServerSocket.getInputStream();

			this.objectInputStream = new ObjectInputStream(inputStream);

			while (!ClientToServerSocket.isClosed()) {
				listenToData();
			}
		}

	}

	public void resetFlux() throws IOException, ClassNotFoundException {
		ClientToServerSocket.close();
		this.ClientToServerSocket = mainDeviceServerSocket.accept();
		this.inputStream = this.ClientToServerSocket.getInputStream();
		this.objectInputStream = new ObjectInputStream(inputStream);
	}

	public void listenToData() throws ClassNotFoundException, IOException {
		// read the list of messages from the socket
		try {
			mc.parseEmulatorInput((String) objectInputStream.readObject());

		} catch (EOFException e) {
			resetFlux();
		}
	}

	public void stopCurrentServer() {

		status_Server = false;

	}

	public void setPort(int newPort) {
		this.serverPort = newPort;
	}

	@Override
	public void run() {

		try {
			this.init();

			while (status_Server == true) {
				connectAndRetrieveData();
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Server exception: " + e.getMessage());
			e.printStackTrace();
		}

	}
}